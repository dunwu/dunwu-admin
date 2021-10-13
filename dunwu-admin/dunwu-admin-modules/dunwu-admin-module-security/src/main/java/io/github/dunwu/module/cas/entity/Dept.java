package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 部门
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_dept")
@ApiModel(value = "Dept", description = "部门")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @TableField("`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    @ApiModelProperty(value = "创建者ID")
    @TableField("`creator_id`")
    private Long creatorId;

    @ApiModelProperty(value = "更新者ID")
    @TableField("`updater_id`")
    private Long updaterId;

    @ApiModelProperty(value = "创建者名称")
    @TableField("`creator_name`")
    private String creatorName;

    @ApiModelProperty(value = "更新者用户名")
    @TableField("`updater_name`")
    private String updaterName;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String SEQUENCE = "sequence";
    public static final String CHILDREN_NUM = "children_num";
    public static final String IS_DISABLED = "is_disabled";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
