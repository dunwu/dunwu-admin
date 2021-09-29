package io.github.dunwu.module.security.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.annotation.rest.AnonymousGetMapping;
import io.github.dunwu.annotation.rest.AnonymousPostMapping;
import io.github.dunwu.domain.vo.EmailVo;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.entity.constant.LoginCodeEnum;
import io.github.dunwu.module.security.entity.dto.AuthUserDto;
import io.github.dunwu.module.security.entity.dto.UserVo;
import io.github.dunwu.module.security.security.TokenProvider;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.module.security.service.VerifyService;
import io.github.dunwu.module.cas.entity.SysUser;
import io.github.dunwu.module.cas.entity.vo.UserPassVo;
import io.github.dunwu.service.EmailService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.util.enums.CodeBiEnum;
import io.github.dunwu.util.enums.CodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2018-11-23 授权、根据token获取用户详细信息
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
@Api(tags = "系统：系统授权接口")
public class AuthController {

    private final RSA rsa;
    private final DunwuWebSecurityProperties properties;
    private final RedisHelper redisHelper;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final VerifyService verificationCodeService;
    private final EmailService emailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public DataResult login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) {
        // 密码解密
        String password = rsa.decryptStr(authUser.getPassword(), KeyType.PrivateKey);
        // 查询验证码
        String code = (String) redisHelper.get(authUser.getUuid());
        // 清除验证码
        redisHelper.del(authUser.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "验证码不存在或已过期");
        }
        if (StrUtil.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final UserVo userVo = (UserVo) authentication.getPrincipal();
        // 保存在线信息
        authService.save(userVo, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getJwt().getTokenStartWith() + token);
            put("user", userVo);
        }};
        if (properties.isSingleLogin()) {
            //踢掉之前已经登录的token
            authService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return DataResult.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public DataResult getUserInfo() {
        return DataResult.ok(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public DataResult getCode() {
        // 获取运算的结果
        Captcha captcha = authService.getCaptcha();
        String uuid = properties.getJwt().getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisHelper.set(uuid, captchaValue, properties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return DataResult.ok(imgResult);
    }

    @ApiOperation("退出登录")
    @AnonymousPostMapping(value = "/logout")
    public DataResult logout(HttpServletRequest request) {
        authService.logout(tokenProvider.getToken(request));
        return DataResult.ok();
    }

    @AppLog("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PostMapping("edit/center")
    public DataResult center(@Validated(EditCheck.class) @RequestBody SysUser entity) {
        if (!entity.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "不能修改他人资料");
        }
        authService.updateCenter(entity);
        return DataResult.ok();
    }

    @ApiOperation("修改用户密码")
    @PostMapping("edit/password")
    public DataResult updatePass(@RequestBody UserPassVo entity) throws Exception {
        authService.updatePass(entity);
        return DataResult.ok();
    }

    @AppLog("修改用户邮箱")
    @ApiOperation("修改用户邮箱")
    @PostMapping(value = "edit/email/{code}")
    public DataResult updateEmail(@PathVariable String code,
        @Validated(EditCheck.class) @RequestBody SysUser entity) throws Exception {
        authService.updateEmail(code, entity);
        return DataResult.ok();
    }

    @PostMapping(value = "/code/resetEmail")
    @ApiOperation("重置邮箱，发送验证码")
    public DataResult resetEmail(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return DataResult.ok();
    }

    @PostMapping(value = "/code/email/resetPass")
    @ApiOperation("重置密码，发送验证码")
    public DataResult resetPass(@RequestParam String email) {
        EmailVo emailVo = verificationCodeService.sendEmail(email, CodeEnum.EMAIL_RESET_PWD_CODE.getKey());
        emailService.send(emailVo, emailService.find());
        return DataResult.ok();
    }

    @GetMapping(value = "/code/validated")
    @ApiOperation("验证码验证")
    public DataResult validated(@RequestParam String email, @RequestParam String code,
        @RequestParam Integer codeBi) {
        CodeBiEnum biEnum = CodeBiEnum.find(codeBi);
        switch (Objects.requireNonNull(biEnum)) {
            case ONE:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + email, code);
                break;
            case TWO:
                verificationCodeService.validated(CodeEnum.EMAIL_RESET_PWD_CODE.getKey() + email, code);
                break;
            default:
                break;
        }
        return DataResult.ok();
    }

}
