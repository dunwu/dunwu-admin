package io.github.dunwu.module.cas.entity.dto;

import io.github.dunwu.common.entity.dto.BaseConfigDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleDto", description = "角色表")
public class RoleDto extends BaseConfigDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色级别")
    private Integer level;

    @ApiModelProperty(value = "数据权限")
    private String dataScope;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "权限表达式")
    private List<MenuDto> menus;

    @ApiModelProperty(value = "权限表达式")
    private List<DeptDto> depts;

}
