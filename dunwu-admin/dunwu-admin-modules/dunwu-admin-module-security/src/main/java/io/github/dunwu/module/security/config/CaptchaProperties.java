package io.github.dunwu.module.security.config;

import io.github.dunwu.module.security.constant.enums.CaptchaTypeEnum;
import lombok.Data;

/**
 * 登录验证码配置
 *
 * @author peng.zhang
 * @date 2021-09-24
 */
@Data
public class CaptchaProperties {

    /** 验证码配置 */
    private CaptchaTypeEnum captchaType = CaptchaTypeEnum.ARITHMETIC;
    /** 验证码有效期(分钟)，默认 3 分钟 */
    private Long expiration = 3 * 60L;
    /** 验证码内容长度 */
    private int length = 2;
    /** 验证码宽度 */
    private int width = 111;
    /** 验证码高度 */
    private int height = 36;
    /** 验证码字体 */
    private String fontName;
    /** 字体大小 */
    private int fontSize = 25;

    public CaptchaTypeEnum getCaptchaType() {
        return captchaType;
    }

}
