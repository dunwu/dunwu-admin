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

import javax.validation.constraints.NotNull;

/**
 * 菜单表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_menu")
@ApiModel(value = "Menu", description = "菜单表")
public class Menu extends BaseConfigEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "菜单类型")
    @TableField("`menu_type`")
    private Integer menuType;

    @ApiModelProperty(value = "权限表达式")
    @TableField("`expression`")
    private String expression;

    @ApiModelProperty(value = "子菜单数目")
    @TableField("`children_num`")
    private Integer childrenNum;

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

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String CHILDREN_NUM = "children_num";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String EXPRESSION = "expression";
    public static final String COMPONENT = "component";
    public static final String SEQUENCE = "sequence";
    public static final String ICON = "icon";
    public static final String PATH = "path";
    public static final String IS_FRAME = "is_frame";
    public static final String IS_CACHED = "is_cached";
    public static final String IS_HIDDEN = "is_hidden";
    public static final String NOTE = "note";

}
