package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 岗位角色关联
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_job_role_map")
@ApiModel(value = "JobRoleMap", description = "岗位角色关联")
public class JobRoleMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "岗位ID")
    @TableField("job_id")
    private Long jobId;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Long roleId;

    public JobRoleMap() {}

    public JobRoleMap(Long jobId, Long roleId) {
        this.jobId = jobId;
        this.roleId = roleId;
    }

    public static final String ID = "id";
    public static final String JOB_ID = "job_id";
    public static final String ROLE_ID = "role_id";

}
