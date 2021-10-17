package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.common.entity.BaseConfigEntity;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 职务表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_job")
@ApiModel(value = "Job", description = "职务表")
public class Job extends BaseConfigEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "职务名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "职务类型")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty(value = "职级")
    @Range(min = 1, max = 99)
    @TableField("`level`")
    private Integer level;

    @ApiModelProperty(value = "职务顺序")
    @Range(min = 0, max = 999)
    @TableField("`sequence`")
    private Integer sequence;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String LEVEL = "level";
    public static final String SEQUENCE = "sequence";
    public static final String NOTE = "note";

}
