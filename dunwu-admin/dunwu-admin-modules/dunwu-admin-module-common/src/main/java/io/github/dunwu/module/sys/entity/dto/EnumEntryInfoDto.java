package io.github.dunwu.module.sys.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 枚举选项 Dto 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-08
 */
@Data
@ApiModel(value = "EnumEntryInfoDto", description = "枚举选项 Dto 实体")
public class EnumEntryInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "枚举选项名称")
    private String name;

    @ApiModelProperty(value = "枚举选项注释")
    private String comment;

    @ApiModelProperty(value = "枚举选项参数")
    private List<String> params;

}
