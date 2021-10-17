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
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 权限表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
@Data
@Accessors(chain = true)
@TableName("cas_permission")
@ApiModel(value = "Permission", description = "权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "资源ID")
    @TableField("`resource_id`")
    private Long resourceId;

    @ApiModelProperty(value = "权限编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "权限名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "权限类型")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    @TableField("`expression`")
    private String expression;

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

    @ApiModelProperty(value = "更新者名称")
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
    public static final String RESOURCE_ID = "resource_id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String EXPRESSION = "expression";
    public static final String IS_DISABLED = "is_disabled";
    public static final String NOTE = "note";
    public static final String CREATOR_ID = "creator_id";
    public static final String UPDATER_ID = "updater_id";
    public static final String CREATOR_NAME = "creator_name";
    public static final String UPDATER_NAME = "updater_name";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
