package io.github.dunwu.module.security.filter;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.module.security.util.JwtTokenUtil;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义认证处理器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Slf4j
public class AuthenticationFilter extends GenericFilterBean {

    private final JwtTokenUtil jwtTokenUtil;
    private final DunwuWebSecurityProperties securityProperties;
    private final AuthService authService;
    private final RedisHelper redisHelper;

    /**
     * @param jwtTokenUtil Token
     * @param securityProperties   JWT
     * @param authService  用户在线
     */
    public AuthenticationFilter(JwtTokenUtil jwtTokenUtil,
        DunwuWebSecurityProperties securityProperties, AuthService authService, RedisHelper redisHelper) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.securityProperties = securityProperties;
        this.authService = authService;
        this.redisHelper = redisHelper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = resolveToken(httpServletRequest);
        // 对于 Token 为空的不需要去查 Redis
        if (StrUtil.isNotBlank(token)) {
            OnlineUserDto onlineUserDto = null;
            boolean cleanUserCache = false;
            try {
                onlineUserDto = authService.getOne(securityProperties.getToken().getOnlinePrefix() + token);
                if (onlineUserDto != null && StringUtils.hasText(token)) {
                    Authentication authentication = jwtTokenUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // Token 续期
                    jwtTokenUtil.checkRenewal(token);
                }
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
                cleanUserCache = true;
            } finally {
                if (cleanUserCache || Objects.isNull(onlineUserDto)) {
                    redisHelper.del(securityProperties.getToken().getOnlinePrefix() + token);
                }
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getToken().getTokenHeader());
        if (StringUtils.hasText(token) && token.startsWith(securityProperties.getToken().getTokenPrefix())) {
            // 去掉令牌前缀
            return token.replace(securityProperties.getToken().getTokenPrefix(), "");
        } else {
            log.debug("非法Token：{}", token);
        }
        return null;
    }

}
