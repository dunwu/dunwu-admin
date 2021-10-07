package io.github.dunwu.module.security.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单信息
 *
 * @author peng.zhang
 * @date 2021-09-24
 */
@Getter
@Setter
public class LoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Boolean rememberMe;

    private String code;

    private String uuid = "";

}
