package io.github.dunwu.module.security.filter;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.module.security.util.JwtTokenUtil;
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
    private final DunwuWebSecurityProperties properties;
    private final AuthService authService;

    /**
     * @param jwtTokenUtil Token
     * @param properties   JWT
     * @param authService  用户在线
     */
    public AuthenticationFilter(JwtTokenUtil jwtTokenUtil,
        DunwuWebSecurityProperties properties, AuthService authService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.properties = properties;
        this.authService = authService;
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
                onlineUserDto = authService.getOne(properties.getToken().getOnlinePrefix() + token);
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
                cleanUserCache = true;
            } finally {
                if (cleanUserCache || Objects.isNull(onlineUserDto)) {
                    authService.cleanUserCache(String.valueOf(jwtTokenUtil.getClaims(token).get(JwtTokenUtil.AUTHORITIES_KEY)));
                }
            }
            if (onlineUserDto != null && StringUtils.hasText(token)) {
                Authentication authentication = jwtTokenUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // Token 续期
                jwtTokenUtil.checkRenewal(token);
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
        String token = request.getHeader(properties.getToken().getTokenHeader());
        if (StringUtils.hasText(token) && token.startsWith(properties.getToken().getTokenPrefix())) {
            // 去掉令牌前缀
            return token.replace(properties.getToken().getTokenPrefix(), "");
        } else {
            log.debug("非法Token：{}", token);
        }
        return null;
    }

}
