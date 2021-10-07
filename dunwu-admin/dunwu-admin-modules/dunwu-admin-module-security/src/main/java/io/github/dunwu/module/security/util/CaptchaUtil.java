package io.github.dunwu.module.security.util;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.module.security.config.CaptchaProperties;
import io.github.dunwu.tool.core.exception.AuthException;

import java.awt.*;

/**
 * @author peng.zhang
 * @date 2021-09-24
 */
public class CaptchaUtil {

    public static Captcha getCaptcha(CaptchaProperties captchaDto) {
        Captcha captcha;
        switch (captchaDto.getCaptchaType()) {
            case ARITHMETIC:
                // 算术类型 https://gitee.com/whvse/EasyCaptcha
                captcha = new ArithmeticCaptcha(captchaDto.getWidth(), captchaDto.getHeight());
                // 几位数运算，默认是两位
                captcha.setLen(captchaDto.getLength());
                break;
            case CHINESE:
                captcha = new ChineseCaptcha(captchaDto.getWidth(), captchaDto.getHeight());
                captcha.setLen(captchaDto.getLength());
                break;
            case CHINESE_GIF:
                captcha = new ChineseGifCaptcha(captchaDto.getWidth(), captchaDto.getHeight());
                captcha.setLen(captchaDto.getLength());
                break;
            case GIF:
                captcha = new GifCaptcha(captchaDto.getWidth(), captchaDto.getHeight());
                captcha.setLen(captchaDto.getLength());
                break;
            case SPEC:
                captcha = new SpecCaptcha(captchaDto.getWidth(), captchaDto.getHeight());
                captcha.setLen(captchaDto.getLength());
                break;
            default:
                throw new AuthException("验证码配置信息错误！");
        }
        if (StrUtil.isNotBlank(captchaDto.getFontName())) {
            captcha.setFont(new Font(captchaDto.getFontName(), Font.PLAIN, captchaDto.getFontSize()));
        }
        return captcha;
    }

}
