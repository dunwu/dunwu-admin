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
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.entity.vo.UserPassVo;
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
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.github.dunwu.tool.data.util.PageUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.SpringUtil;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2019年10月26日21:56:27
 */
@Slf4j
@RequiredArgsConstructor
@Service("userDetailsService")
public class AuthService implements UserDetailsService {

    static final Map<String, UserVo> userDtoCache = new ConcurrentHashMap<>();

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
    public UserVo loadUserByUsername(String username) {
        boolean searchDb = true;
        UserVo userVo = null;
        if (securityProperties.isCacheEnable() && userDtoCache.containsKey(username)) {
            userVo = userDtoCache.get(username);
            searchDb = false;
        }
        if (searchDb) {
            UserDto user;
            user = userService.pojoByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (user.getDisabled()) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "账号未激活！");
                }

                Set<Long> deptIds = deptService.getOwnAndChildrenDeptIds(user.getDeptId());
                userVo = new UserVo(user, deptIds, roleService.mapToGrantedAuthorities(user));
                userDtoCache.put(username, userVo);
            }
        }
        return userVo;
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
        String key = securityProperties.getToken().getOnlinePrefix() + token;
        redisHelper.del(key);
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
        List<OnlineUserDto> onlineUserDtos = getAllOnlineUsers(filter);
        return PageUtil.toMap(
            PageUtil.toList(pageable.getPageNumber(), pageable.getPageSize(), onlineUserDtos),
            onlineUserDtos.size()
        );
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

    public void setEnableCache(boolean enableCache) {
        securityProperties.setCacheEnable(enableCache);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(User entity) {
        if (!entity.getId().equals(securityService.getCurrentUserId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "不能修改他人资料");
        }
        UserDto user = userService.pojoById(entity.getId());
        UserQuery query = new UserQuery();
        query.setPhone(entity.getPhone());
        UserDto user1 = userService.pojoByQuery(query);
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new DataException(StrUtil.format("未找到 phone = {} 的用户", entity.getPhone()));
        }
        User sysUser = BeanUtil.toBean(user, User.class);
        sysUser.setNickname(entity.getNickname());
        sysUser.setPhone(entity.getPhone());
        sysUser.setGender(entity.getGender());
        userService.updateById(sysUser);
        // 清理缓存
        delCaches(user.getId(), user.getUsername());
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisHelper.del("user::id:" + id);
        flushCache(username);
    }

    /**
     * 清理特定用户缓存信息<br> 用户信息变更时
     *
     * @param userName /
     */
    public void cleanUserCache(String userName) {
        if (StrUtil.isNotEmpty(userName)) {
            userDtoCache.remove(userName);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePass(UserPassVo passVo) {
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
        userService.updateById(user);
        flushCache(userDto.getUsername());
    }

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
     * 清理所有用户的缓存信息<br> 如发生角色授权信息变化，可以简便的全部失效缓存
     */
    public void cleanAll() {
        userDtoCache.clear();
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

    /**
     * 清理 登陆时 用户缓存信息
     *
     * @param username /
     */
    private void flushCache(String username) {
        cleanUserCache(username);
    }

}
