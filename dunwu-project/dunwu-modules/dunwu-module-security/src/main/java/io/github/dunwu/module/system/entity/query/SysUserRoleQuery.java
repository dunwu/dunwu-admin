package io.github.dunwu.module.system.entity.query;

import io.github.dunwu.tool.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统用户角色关联信息 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserRoleQuery", description = "系统用户角色关联信息")
public class SysUserRoleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @QueryField
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    @QueryField
    private Long roleId;



}
