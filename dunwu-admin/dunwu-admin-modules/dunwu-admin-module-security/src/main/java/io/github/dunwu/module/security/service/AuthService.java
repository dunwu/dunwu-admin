package io.github.dunwu.module.security.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.vo.UserPasswordVo;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.constant.enums.CaptchaTypeEnum;
import io.github.dunwu.module.security.constant.enums.CodeEnum;
import io.github.dunwu.module.security.entity.dto.CaptchaImageDto;
import io.github.dunwu.module.security.entity.dto.LoginDto;
import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.entity.vo.LoginSuccessVo;
import io.github.dunwu.module.security.entity.vo.UserVo;
import io.github.dunwu.module.security.util.CaptchaUtil;
import io.github.dunwu.module.security.util.EncryptUtil;
import io.github.dunwu.module.security.util.JwtTokenUtil;
import io.github.dunwu.module.security.util.SecurityUtil;
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.github.dunwu.tool.data.util.PageUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.SpringUtil;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2019年10月26日21:56:27
 */
@Slf4j
@CacheConfig(cacheNames = "dunwu::user")
@RequiredArgsConstructor
@Service("userDetailsService")
public class AuthService implements UserDetailsService {

    private final RSA rsa;
    private final RedisHelper redisHelper;
    private final UserService userService;
    private final DeptService deptService;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final DunwuWebSecurityProperties securityProperties;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    @Cacheable(key = "'name:' + #username")
    public UserVo loadUserByUsername(String username) {
        log.info("查询用户 {} 的信息", username);
        UserDto user = userService.pojoByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        if (user.getDisabled()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "账号未激活！");
        }

        Set<Long> deptIds = deptService.getOwnAndChildrenDeptIds(user.getDeptId());
        return new UserVo(user, deptIds, roleService.mapToGrantedAuthorities(user));
    }

    /**
     * 获取当前用户身份信息
     */
    public UserVo getCurrentUser() throws AuthException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException("当前登录状态过期");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        return (UserVo) userDetailsService.loadUserByUsername(userDetails.getUsername());
    }

    /**
     * 处理登出请求
     */
    public boolean logout(String token) {
        String username = SecurityUtil.getCurrentUsername();
        String key = securityProperties.getToken().getOnlinePrefix() + token;
        redisHelper.del(key);
        cleanUserCache(username);
        return true;
    }

    /**
     * 处理登录请求
     */
    public LoginSuccessVo login(LoginDto loginDto, HttpServletRequest request) {
        checkCaptcha(loginDto);

        // 密码解密
        String password = rsa.decryptStr(loginDto.getPassword(), KeyType.PrivateKey);
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成令牌
        String token = jwtTokenUtil.createToken(authentication);
        final UserVo userVo = (UserVo) authentication.getPrincipal();
        // 保存在线信息
        saveLoginUserInfo(userVo, token, request);

        if (securityProperties.isSingleLogin()) {
            //踢掉之前已经登录的token
            checkLoginOnUser(loginDto.getUsername(), token);
        }

        // 返回 token 与 用户信息
        return new LoginSuccessVo(securityProperties.getToken().getTokenPrefix() + token, userVo);
    }

    /**
     * 校验验证码
     */
    public void checkCaptcha(LoginDto loginDto) {
        // 查询验证码
        String code = (String) redisHelper.get(loginDto.getUuid());
        // 清除验证码
        redisHelper.del(loginDto.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new AuthException("验证码不存在或已过期");
        }
        if (StrUtil.isBlank(loginDto.getCode()) || !loginDto.getCode().equalsIgnoreCase(code)) {
            throw new AuthException("验证码错误");
        }
    }

    /**
     * 保存在线用户信息
     */
    public void saveLoginUserInfo(UserVo userVo, String token, HttpServletRequest request) {
        String dept = userVo.getUser().getDept().getName();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        String ip = requestIdentityInfo.getIp();
        String browser = requestIdentityInfo.getBrowser();
        String address = requestIdentityInfo.getLocation();
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(userVo.getUsername(), userVo.getUser().getNickname(), dept,
                browser, ip, address, EncryptUtil.desEncrypt(token), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        redisHelper.set(securityProperties.getToken().getOnlinePrefix() + token, onlineUserDto,
            securityProperties.getToken().getTokenValidityInSeconds());
    }

    /**
     * 强制用户下线
     */
    public void offline(String id) {
        id = securityProperties.getToken().getOnlinePrefix() + id;
        redisHelper.del(id);
    }

    /**
     * 根据用户名强退用户
     *
     * @param username /
     */
    @Async
    public void offlineForUsername(String username) throws Exception {
        List<OnlineUserDto> onlineUsers = getAllOnlineUsers(username);
        for (OnlineUserDto onlineUser : onlineUsers) {
            if (onlineUser.getUserName().equals(username)) {
                String token = EncryptUtil.desDecrypt(onlineUser.getKey());
                offline(token);
            }
        }
    }

    /**
     * 获取验证码图片信息
     */
    public CaptchaImageDto getCaptcha() {
        // 生成 uuid
        String uuid = securityProperties.getToken().getCaptchaPrefix() + IdUtil.fastSimpleUUID();
        // 生成验证码
        Captcha captcha = CaptchaUtil.getCaptcha(securityProperties.getCaptcha());
        // 当验证码类型为 arithmetic 时且长度 >= 2 时，captcha.text() 的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() == CaptchaTypeEnum.ARITHMETIC.getCode() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 刷新验证码缓存
        redisHelper.set(uuid, captchaValue, securityProperties.getCaptcha().getExpiration(), TimeUnit.SECONDS);
        // 返回验证码信息
        return new CaptchaImageDto(captcha.toBase64(), uuid);
    }

    /**
     * 查询全部数据
     *
     * @param filter   /
     * @param pageable /
     * @return /
     */
    public Map<String, Object> getAllOnlineUsers(String filter, Pageable pageable) {
        List<OnlineUserDto> list = getAllOnlineUsers(filter);
        int pageNumber = 0;
        if (pageable.getPageNumber() > 0) {
            pageNumber = pageable.getPageNumber() - 1;
        }
        List<OnlineUserDto> content = PageUtil.toList(pageNumber, pageable.getPageSize(), list);
        return PageUtil.toMap(content, list.size());
    }

    /**
     * 查询用户
     *
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisHelper.get(key);
    }

    /**
     * 更新用户中心的个人信息
     *
     * @param entity 用户实体
     * @return true / false
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCenter(User entity) {
        if (!entity.getId().equals(securityService.getCurrentUserId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "不能修改他人资料");
        }

        UserDto userDto = userService.pojoById(entity.getId());
        User record = BeanUtil.toBean(userDto, User.class);
        record.setNickname(entity.getNickname());
        record.setPhone(entity.getPhone());
        record.setGender(entity.getGender());
        boolean isOk = userService.updateById(record);
        if (!isOk) {
            return false;
        }

        // 清理缓存
        cleanUserCache(userDto.getId(), userDto.getUsername());
        return true;
    }

    /**
     * 更新用户密码
     *
     * @param passVo 用户密码重置实体
     * @return true / false
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(UserPasswordVo passVo) {

        String oldPass = rsa.decryptStr(passVo.getOldPass(), KeyType.PrivateKey);
        String newPass = rsa.decryptStr(passVo.getNewPass(), KeyType.PrivateKey);

        UserDto userDto = userService.pojoByUsername(securityService.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "新密码不能与旧密码相同");
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(passwordEncoder.encode(newPass));
        boolean isOk = userService.updateById(user);
        if (!isOk) {
            return false;
        }

        cleanUserCache(userDto.getUsername());
        return true;
    }

    /**
     * 更新用户邮箱
     *
     * @param code   校验码
     * @param entity 用户实体
     * @return true / false
     */
    public boolean updateEmail(String code, User entity) {
        String password = rsa.decryptStr(entity.getPassword(), KeyType.PrivateKey);
        UserDto userDto = userService.pojoByUsername(securityService.getCurrentUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "密码错误");
        }
        validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + entity.getEmail(), code);
        User user = new User();
        user.setId(entity.getId());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        userService.updateById(user);
        return true;
    }

    /**
     * 清理用户缓存
     *
     * @param id       用户ID
     * @param username 用户名
     */
    @Async
    public void cleanUserCache(Long id, String username) {
        redisHelper.del("user::id:" + id);
        cleanUserCache(username);
    }

    /**
     * 清理特定用户缓存信息<br> 用户信息变更时
     *
     * @param username 用户名
     */
    @Async
    public void cleanUserCache(String username) {
        log.info("清理用户 {} 的缓存", username);
        redisHelper.del("dunwu::user::name:" + username);
    }

    public boolean validated(String key, String code) {
        Object value = redisHelper.get(key);
        if (value == null || !value.toString().equals(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "无效验证码");
        } else {
            redisHelper.del(key);
        }
        return true;
    }

    /**
     * 查询全部在线用户数据，不分页
     */
    private List<OnlineUserDto> getAllOnlineUsers(String filter) {
        List<String> keys = redisHelper.scan(securityProperties.getToken().getOnlinePrefix() + "*");
        Collections.reverse(keys);
        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDto onlineUserDto = (OnlineUserDto) redisHelper.get(key);
            if (StrUtil.isNotBlank(filter)) {
                if (onlineUserDto.toString().contains(filter)) {
                    onlineUserDtos.add(onlineUserDto);
                }
            } else {
                onlineUserDtos.add(onlineUserDto);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     */
    private void checkLoginOnUser(String userName, String ignoreToken) {
        List<OnlineUserDto> onlineUsers = getAllOnlineUsers(userName);
        if (CollectionUtil.isEmpty(onlineUsers)) {
            return;
        }
        for (OnlineUserDto onlineUserDto : onlineUsers) {
            if (onlineUserDto.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtil.desDecrypt(onlineUserDto.getKey());
                    if (StrUtil.isNotBlank(ignoreToken) && !ignoreToken.equals(token)) {
                        this.offline(token);
                    } else if (StrUtil.isBlank(ignoreToken)) {
                        this.offline(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

}
