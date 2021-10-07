/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.dunwu.module.security.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import io.github.dunwu.module.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.tool.data.redis.RedisHelper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author /
 */
@Slf4j
@Component
public class JwtTokenUtil implements InitializingBean {

    public static final String AUTHORITIES_KEY = "user";
    private final DunwuWebSecurityProperties properties;
    private final RedisHelper redisHelper;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public JwtTokenUtil(DunwuWebSecurityProperties properties, RedisHelper redisHelper) {
        this.properties = properties;
        this.redisHelper = redisHelper;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getToken().getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();
        jwtBuilder = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     * 创建Token 设置永不过期， Token 的时间有效性转到Redis 维护
     *
     * @param authentication /
     * @return /
     */
    public String createToken(Authentication authentication) {
        return jwtBuilder
            // 加入ID确保生成的 Token 都不一致
            .setId(IdUtil.simpleUUID())
            .claim(AUTHORITIES_KEY, authentication.getName())
            .setSubject(authentication.getName())
            .compact();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public Claims getClaims(String token) {
        return jwtParser
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * @param token 需要检查的token
     */
    public void checkRenewal(String token) {
        // 判断是否续期token,计算token的过期时间
        long time = redisHelper.getExpire(properties.getToken().getOnlinePrefix() + token);
        Date expireDate = DateUtil.offset(new Date(), DateField.SECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期
        if (differ <= properties.getToken().getDetect()) {
            long renew = time + properties.getToken().getRenew();
            redisHelper.expire(properties.getToken().getOnlinePrefix() + token, renew, TimeUnit.SECONDS);
        }
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getToken().getTokenHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getToken().getTokenPrefix())) {
            return requestHeader.substring(7);
        }
        return null;
    }

}
