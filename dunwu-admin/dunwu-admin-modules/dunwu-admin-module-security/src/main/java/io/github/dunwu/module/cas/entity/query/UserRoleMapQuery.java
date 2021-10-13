package io.github.dunwu.module.cas.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关联表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserRoleMapQuery", description = "用户角色关联表")
public class UserRoleMapQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @QueryField(value = "`user_id`")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    @QueryField(value = "`role_id`")
    private Long roleId;

}
