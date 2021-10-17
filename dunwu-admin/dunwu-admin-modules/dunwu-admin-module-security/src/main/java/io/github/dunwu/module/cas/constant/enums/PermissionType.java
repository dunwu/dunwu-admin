package io.github.dunwu.module.cas.constant.enums;

import io.github.dunwu.tool.core.constant.GetDesc;
import io.github.dunwu.tool.core.constant.GetIntCode;

/**
 * 权限资源类型
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
public enum PermissionType implements GetIntCode, GetDesc {
    MENU(1, "菜单");

    private final int code;
    private final String desc;

    PermissionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
