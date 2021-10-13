package io.github.dunwu.module.cas.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 菜单表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MenuQuery", description = "菜单表")
public class MenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @QueryField(value = "`pid`")
    private Long pid;

    @ApiModelProperty(value = "子菜单数目")
    @QueryField(value = "`sub_count`")
    private Integer subCount;

    @ApiModelProperty(value = "菜单类型")
    @QueryField(value = "`type`")
    private Integer type;

    @ApiModelProperty(value = "菜单标题")
    @QueryField(value = "`title`")
    private String title;

    @ApiModelProperty(value = "组件名称")
    @QueryField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "组件")
    @QueryField(value = "`component`")
    private String component;

    @ApiModelProperty(value = "排序")
    @QueryField(value = "`sequence`")
    private Integer sequence;

    @ApiModelProperty(value = "图标")
    @QueryField(value = "`icon`")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    @QueryField(value = "`path`")
    private String path;

    @ApiModelProperty(value = "是否外链")
    @QueryField(value = "`is_frame`")
    private Boolean frame;

    @ApiModelProperty(value = "缓存")
    @QueryField(value = "`is_cached`")
    private Boolean cached;

    @ApiModelProperty(value = "隐藏")
    @QueryField(value = "`is_hidden`")
    private Boolean hidden;

    @ApiModelProperty(value = "权限表达式")
    @QueryField(value = "`expression`")
    private String expression;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "备注")
    @QueryField(value = "`note`")
    private String note;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private Collection<LocalDateTime> updateTimeRange;

}
