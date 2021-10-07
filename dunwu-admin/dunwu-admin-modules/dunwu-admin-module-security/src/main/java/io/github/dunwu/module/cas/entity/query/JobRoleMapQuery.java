package io.github.dunwu.module.cas.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 岗位角色关联 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "JobRoleMapQuery", description = "岗位角色关联")
public class JobRoleMapQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    @QueryField
    private Long jobId;

    @ApiModelProperty(value = "角色ID")
    @QueryField
    private Long roleId;

}
