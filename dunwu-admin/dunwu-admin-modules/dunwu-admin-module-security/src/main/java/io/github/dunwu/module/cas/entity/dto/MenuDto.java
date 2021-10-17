package io.github.dunwu.module.cas.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.dunwu.common.entity.dto.BaseConfigDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Objects;

/**
 * 菜单表 Dto 类（基于 children 字段构成树形结构）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-16
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuDto", description = "菜单表")
public class MenuDto extends BaseConfigDto implements Comparable<MenuDto> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    private Long pid;

    @ApiModelProperty(value = "子菜单数目")
    private Integer childrenNum;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单类型")
    private Integer menuType;

    @ApiModelProperty(value = "权限表达式")
    private String expression;

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

    @ApiModelProperty(value = "备注")
    private String note;

    // ========================================================================
    // 以下字段在构建树形列表时会自动填充
    // ========================================================================

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否有子菜单")
    private boolean hasChildren;

    @ApiModelProperty(value = "是否为叶子节点")
    private boolean leaf;

    @ApiModelProperty(value = "子菜单")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Collection<MenuDto> children;

    // ========================================================================
    // 为了前端组件 【vue-treeselect】 展示，而需要设置的字段
    // ========================================================================

    @ApiModelProperty(value = "用于为新节点赋予不同的颜色")
    private Boolean isNew;

    @ApiModelProperty(value = "默认情况下是否应扩展此文件夹选项")
    private Boolean isDefaultExpanded;

    /** 用于禁用项目选择 */
    public Boolean getIsDisabled() {
        return disabled;
    }

    // ========================================================================
    // 为了构建树形结构，所需要覆写的基础方法
    // ========================================================================

    @Override
    public int compareTo(MenuDto o) {
        if (o.sequence.equals(this.sequence)) {
            return o.id.compareTo(this.id);
        } else {
            return o.sequence.compareTo(this.sequence);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MenuDto menuDto = (MenuDto) o;
        return Objects.equals(id, menuDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

}
