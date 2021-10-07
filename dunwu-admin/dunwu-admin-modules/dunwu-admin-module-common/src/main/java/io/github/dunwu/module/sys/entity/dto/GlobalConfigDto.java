package io.github.dunwu.module.sys.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统全局配置表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GlobalConfigDto", description = "系统全局配置表")
public class GlobalConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "应用编码")
    private String appCode;

    @ApiModelProperty(value = "模块编码")
    private String moduleCode;

    @ApiModelProperty(value = "命名空间")
    private String namespace;

    @ApiModelProperty(value = "配置项编码")
    private String code;

    @ApiModelProperty(value = "配置项配置名称")
    private String name;

    @ApiModelProperty(value = "配置项值")
    private String value;

    @ApiModelProperty(value = "配置项默认值")
    private String defaultValue;

    @ApiModelProperty(value = "配置项值类型")
    private String valueType;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    private Boolean isDisabled;

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

}
