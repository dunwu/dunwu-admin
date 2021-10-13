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
 * 部门职务关联表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@TableName("cas_dept_job_map")
@ApiModel(value = "DeptJobMap", description = "部门职务关联表")
public class DeptJobMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门ID")
    @TableField("`dept_id`")
    private Long deptId;

    @ApiModelProperty(value = "职务ID")
    @TableField("`job_id`")
    private Long jobId;

    public DeptJobMap() { }

    public DeptJobMap(Long deptId, Long jobId) {
        this.deptId = deptId;
        this.jobId = jobId;
    }

    public static final String ID = "id";
    public static final String DEPT_ID = "dept_id";
    public static final String JOB_ID = "job_id";

}
