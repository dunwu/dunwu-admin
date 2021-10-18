package io.github.dunwu.module.cas.entity.vo;

import lombok.Data;

/**
 * 密码重置实体
 *
 * @author peng.zhang
 * @date 2021-10-18
 */
@Data
public class UserPasswordVo {

    private String oldPass;

    private String newPass;

}
