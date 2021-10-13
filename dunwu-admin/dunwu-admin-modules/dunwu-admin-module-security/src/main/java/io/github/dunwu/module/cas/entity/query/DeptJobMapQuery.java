package io.github.dunwu.module.cas.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 部门职务关联表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeptJobMapQuery", description = "部门职务关联表")
public class DeptJobMapQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "部门ID")
    @QueryField(value = "`dept_id`")
    private Long deptId;

    @ApiModelProperty(value = "职务ID")
    @QueryField(value = "`job_id`")
    private Long jobId;

}
