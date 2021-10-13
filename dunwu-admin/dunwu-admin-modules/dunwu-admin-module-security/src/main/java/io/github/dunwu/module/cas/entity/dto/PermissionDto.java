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
 * 权限表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PermissionDto", description = "权限表")
public class PermissionDto implements Serializable, Comparable<PermissionDto> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    private Long pid;

    @ApiModelProperty(value = "权限编码")
    private String code;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限类型")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    private String expression;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    private Boolean disabled;

    @ApiModelProperty(value = "备注")
    private String note;

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

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否有子菜单")
    private boolean hasChildren;

    @ApiModelProperty(value = "是否为叶子节点")
    private boolean leaf;

    @ApiModelProperty(value = "子权限")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<PermissionDto> children;

    @Override
    public int compareTo(PermissionDto o) {
        if (o.getUpdateTime().equals(this.getUpdateTime())) {
            return o.id.compareTo(this.id);
        } else {
            return o.getUpdateTime().compareTo(this.getUpdateTime());
        }
    }

}
