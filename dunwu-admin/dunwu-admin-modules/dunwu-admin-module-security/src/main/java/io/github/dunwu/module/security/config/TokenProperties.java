package io.github.dunwu.module.security.config;

import lombok.Data;

/**
 * 令牌配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@Data
public class TokenProperties {

    /** Request Headers: DunwuToken */
    private String tokenHeader = "DunwuToken";
    /** 令牌前缀，最后留个空格 */
    private String tokenPrefix = "dunwu";
    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;
    /** 令牌过期时间 此处单位/秒 ，默认4小时 */
    private Long tokenValidityInSeconds = 4 * 60 * 60L;
    /** 令牌过期时间 此处单位/秒 */
    private Long expireTime;
    /** 在线用户 key 前缀 */
    private String onlinePrefix = "dunwu::online::";
    /** 验证码 key 前缀 */
    private String captchaPrefix = "dunwu::captcha::";
    /** token 续期检查时间，单位秒，默认30分钟 */
    private Long detect = 1800L;
    /** 续期时间，单位秒，默认1小时 */
    private Long renew = 3600L;

    public String getTokenPrefix() {
        return tokenPrefix + "@";
    }

}
