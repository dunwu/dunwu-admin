package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 角色权限关联表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
@Data
@Accessors(chain = true)
@TableName("cas_role_permission_map")
@ApiModel(value = "RolePermissionMap", description = "角色权限关联表")
public class RolePermissionMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    @TableField("`role_id`")
    private Long roleId;

    @ApiModelProperty(value = "权限ID")
    @TableField("`permission_id`")
    private Long permissionId;

    public RolePermissionMap() { }

    public RolePermissionMap(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public static final String ID = "id";
    public static final String ROLE_ID = "role_id";
    public static final String PERMISSION_ID = "permission_id";

}
