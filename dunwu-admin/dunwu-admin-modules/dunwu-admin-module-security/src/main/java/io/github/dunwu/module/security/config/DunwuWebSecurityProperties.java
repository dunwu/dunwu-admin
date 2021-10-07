package io.github.dunwu.module.security.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * web 安全配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.web.security")
public class DunwuWebSecurityProperties {

    /** 令牌 */
    private final TokenProperties token = new TokenProperties();
    /** 验证码 */
    private final CaptchaProperties captcha = new CaptchaProperties();
    /** 安全开关 */
    private boolean enabled = false;
    /** 是否允许跨域 */
    private boolean corsEnabled = false;
    /** 是否需要认证 */
    private boolean authEnabled = false;
    /** 用户登录信息缓存 */
    private boolean cacheEnable;
    /** 是否开启单点登录 */
    private boolean singleLogin = true;
    /** 登录有效期，默认 1 小时 */
    private Long expiration = 3600L;
    /** RSA 私钥 */
    private String rsaPrivateKey;
    /** RSA 公钥 */
    private String rsaPublicKey;
    /** 跨域的路径 */
    private String corsPath = "/**";
    /** 是否开启 XSS 开关 */
    private boolean xssEnabled = true;
    /** 跳过 XSS 防御的路径 */
    private String xssExcludePath = "/favicon.ico,/img/*,/js/*,/css/*";
    /** 认证令牌 */
    private String authTokenKey = "token";
    /** 所有请求白名单 */
    private List<String> allRequestWhiteList;
    /** Get 请求白名单 */
    private List<String> getRequestWhiteList;

}
