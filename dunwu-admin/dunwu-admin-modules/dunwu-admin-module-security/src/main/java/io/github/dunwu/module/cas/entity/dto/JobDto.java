package io.github.dunwu.module.cas.entity.dto;

import io.github.dunwu.common.entity.dto.BaseConfigDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 职务表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "JobDto", description = "职务表")
public class JobDto extends BaseConfigDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "职务名称")
    private String name;

    @ApiModelProperty(value = "职务类型")
    private Integer type;

    @ApiModelProperty(value = "职级")
    private Integer level;

    @ApiModelProperty(value = "职务顺序")
    private Integer sequence;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "部门", hidden = true)
    private DeptDto dept;

    @ApiModelProperty(value = "角色列表", hidden = true)
    private List<RoleDto> roles;

}
