/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.dunwu.module.security.config;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.DES;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @apiNote 配置文件转换Pojo类的 统一配置 类
 * @author: liaojinlong
 * @date: 2020/6/10 19:04
 */
@Configuration
public class ConfigBeanConfiguration {

    /**
     * 密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * RSA 加密算法工具实例
     *
     * @return /
     */
    @Bean
    public RSA rsa(DunwuWebSecurityProperties securityProperties) {
        return new RSA(securityProperties.getRsaPrivateKey(), securityProperties.getRsaPublicKey());
    }

    /**
     * DES 加密算法工具实例
     *
     * @return /
     */
    @Bean
    public DES des() {
        final String key = "芝麻开门";
        final String iv = "dunwu666";
        return new DES(Mode.CBC, Padding.PKCS5Padding, key.getBytes(), iv.getBytes());
    }

}
