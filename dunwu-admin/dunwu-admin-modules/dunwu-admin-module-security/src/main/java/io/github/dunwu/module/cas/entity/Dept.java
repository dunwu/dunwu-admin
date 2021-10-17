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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_dept")
@ApiModel(value = "Dept", description = "部门表")
public class Dept extends BaseConfigEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    @ApiModelProperty(value = "上级部门ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "部门名称")
    @NotBlank
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "部门等级")
    @Range(min = 1, max = 99)
    @TableField("`level`")
    private Integer level;

    @ApiModelProperty(value = "部门顺序")
    @Range(min = 1, max = 999)
    @TableField("`sequence`")
    private Integer sequence;

    @ApiModelProperty(value = "子部门数量")
    @TableField("`children_num`")
    private Integer childrenNum;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String SEQUENCE = "sequence";
    public static final String CHILDREN_NUM = "children_num";
    public static final String NOTE = "note";

}
