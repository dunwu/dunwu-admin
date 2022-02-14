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
 * 用户角色关联表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@TableName("cas_user_role_map")
@ApiModel(value = "UserRoleMap", description = "用户角色关联表")
public class UserRoleMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    @TableField("`role_id`")
    private Long roleId;

    public UserRoleMap() { }

    public UserRoleMap(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String ROLE_ID = "role_id";

}
