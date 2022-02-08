package io.github.dunwu.module.sys.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 枚举信息 Dto 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-08
 */
@Data
@ApiModel(value = "EnumInfoDto", description = "枚举信息 Dto 实体")
public class EnumInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "枚举名称")
    private String name;

    @ApiModelProperty(value = "枚举注释")
    private String comment;

    @ApiModelProperty(value = "字典选项")
    private List<EnumEntryInfoDto> entries;

}
