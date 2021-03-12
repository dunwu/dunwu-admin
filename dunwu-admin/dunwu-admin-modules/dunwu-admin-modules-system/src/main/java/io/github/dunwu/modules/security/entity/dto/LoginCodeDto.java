package io.github.dunwu.modules.security.entity.dto;

import io.github.dunwu.modules.security.entity.constant.LoginCodeEnum;
import lombok.Data;

/**
 * 登录验证码配置信息
 *
 * @author liaojinlong
 * @date 2020/6/10 18:53
 */
@Data
public class LoginCodeDto {

    /** 验证码配置 */
    private LoginCodeEnum codeType = LoginCodeEnum.arithmetic;
    /** 验证码有效期(分钟) */
    private Long expiration = 2L;
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

    public LoginCodeEnum getCodeType() {
        return codeType;
    }

}
