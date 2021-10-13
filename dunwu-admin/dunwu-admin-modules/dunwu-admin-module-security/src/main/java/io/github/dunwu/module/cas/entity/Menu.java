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
 * 菜单表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@TableName("cas_menu")
@ApiModel(value = "Menu", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "子菜单数目")
    @TableField("`sub_count`")
    private Integer subCount;

    @ApiModelProperty(value = "菜单类型")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty(value = "菜单标题")
    @TableField("`title`")
    private String title;

    @ApiModelProperty(value = "组件名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "组件")
    @TableField("`component`")
    private String component;

    @ApiModelProperty(value = "排序")
    @TableField("`sequence`")
    private Integer sequence;

    @ApiModelProperty(value = "图标")
    @TableField("`icon`")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    @TableField("`path`")
    private String path;

    @ApiModelProperty(value = "是否外链")
    @TableField("`is_frame`")
    private Boolean frame;

    @ApiModelProperty(value = "缓存")
    @TableField("`is_cached`")
    private Boolean cached;

    @ApiModelProperty(value = "隐藏")
    @TableField("`is_hidden`")
    private Boolean hidden;

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
    public static final String SUB_COUNT = "sub_count";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String COMPONENT = "component";
    public static final String SEQUENCE = "sequence";
    public static final String ICON = "icon";
    public static final String PATH = "path";
    public static final String IS_FRAME = "is_frame";
    public static final String IS_CACHED = "is_cached";
    public static final String IS_HIDDEN = "is_hidden";
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
