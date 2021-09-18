package io.github.dunwu.module.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
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
 * 系统岗位信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysJob", description = "系统岗位信息")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "岗位名称")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    private String name;

    @ApiModelProperty(value = "权重")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @Range(min = 0, max = 999, groups = { AddCheck.class, EditCheck.class })
    private Integer weight;

    @ApiModelProperty(value = "状态")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    private String note;

    @JsonIgnore
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonIgnore
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}
