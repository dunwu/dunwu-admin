package io.github.dunwu.module.security.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessVo {

    private String token;
    private UserVo user;

}
