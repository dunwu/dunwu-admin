package io.github.dunwu.module.sys.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统全局配置表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GlobalConfigQuery", description = "系统全局配置表")
public class GlobalConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "应用编码")
    @QueryField
    private String appCode;

    @ApiModelProperty(value = "模块编码")
    @QueryField
    private String moduleCode;

    @ApiModelProperty(value = "命名空间")
    @QueryField
    private String namespace;

    @ApiModelProperty(value = "配置项编码")
    @QueryField
    private String code;

    @ApiModelProperty(value = "配置项配置名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "配置项值")
    @QueryField
    private String value;

    @ApiModelProperty(value = "配置项默认值")
    @QueryField
    private String defaultValue;

    @ApiModelProperty(value = "配置项值类型")
    @QueryField
    private String valueType;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField
    private Boolean isDisabled;

    @ApiModelProperty(value = "备注")
    @QueryField
    private String note;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime updateTime;

}
