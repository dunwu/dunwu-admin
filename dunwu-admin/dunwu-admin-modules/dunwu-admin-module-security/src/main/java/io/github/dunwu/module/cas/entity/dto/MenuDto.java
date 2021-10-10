package io.github.dunwu.module.cas.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 菜单 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MenuDto", description = "菜单")
public class MenuDto implements Serializable, Comparable<MenuDto> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    private Long pid;

    @ApiModelProperty(value = "子菜单数目")
    private Integer subCount;

    @ApiModelProperty(value = "菜单类型")
    private Integer type;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "组件名称")
    private String name;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "排序")
    private Integer sequence;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "链接地址")
    private String path;

    @ApiModelProperty(value = "是否外链")
    private Boolean frame;

    @ApiModelProperty(value = "缓存")
    private Boolean cached;

    @ApiModelProperty(value = "隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // ========================================================================
    // 以下字段在构建树形列表时会自动填充

    @ApiModelProperty(value = "组件名称")
    private String componentName;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否有子菜单")
    private boolean hasChildren;

    @ApiModelProperty(value = "是否为叶子节点")
    private boolean leaf;

    @ApiModelProperty(value = "子菜单")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<MenuDto> children;

    // ========================================================================

    @Override
    public int compareTo(MenuDto o) {
        if (o.sequence.equals(this.sequence)) {
            return o.id.compareTo(this.id);
        } else {
            return o.sequence.compareTo(this.sequence);
        }
    }

}
