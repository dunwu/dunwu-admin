package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 岗位
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_job")
@ApiModel(value = "Job", description = "岗位")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "岗位名称")
    @NotNull
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "排序")
    @NotNull
    @Range(min = 0, max = 999)
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(value = "部门ID")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "岗位状态")
    @TableField("enabled")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    @TableField("note")
    private String note;

    @JsonIgnore
    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @JsonIgnore
    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SEQUENCE = "sequence";
    public static final String DEPT_ID = "dept_id";
    public static final String ENABLED = "enabled";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
