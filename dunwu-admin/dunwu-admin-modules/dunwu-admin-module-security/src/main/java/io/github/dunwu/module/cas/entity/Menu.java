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

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 菜单
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_menu")
@ApiModel(value = "Menu", description = "菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty(value = "子菜单数目")
    @TableField("sub_count")
    private Integer subCount;

    @ApiModelProperty(value = "菜单类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "菜单标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "组件名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "组件")
    @TableField("component")
    private String component;

    @ApiModelProperty(value = "排序")
    @TableField("sequence")
    private Integer sequence;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "是否外链")
    @TableField("i_frame")
    private Boolean iFrame;

    @ApiModelProperty(value = "缓存")
    @TableField("`cache`")
    private Boolean cache;

    @ApiModelProperty(value = "隐藏")
    @TableField("hidden")
    private Boolean hidden;

    @ApiModelProperty(value = "权限")
    @TableField("permission")
    private String permission;

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
    public static final String PID = "pid";
    public static final String SUB_COUNT = "sub_count";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String COMPONENT = "component";
    public static final String SEQUENCE = "sequence";
    public static final String ICON = "icon";
    public static final String PATH = "path";
    public static final String I_FRAME = "i_frame";
    public static final String CACHE = "cache";
    public static final String HIDDEN = "hidden";
    public static final String PERMISSION = "permission";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
