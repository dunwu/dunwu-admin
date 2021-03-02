package io.github.dunwu.modules.generator.entity.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 代码生成-表级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfigQuery", description = "代码生成-表级别配置")
public class CodeTableConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "Table注释")
    @QueryField
    private String comment;

    @ApiModelProperty(value = "开启权限校验")
    @QueryField
    private Boolean enablePermission;

    @ApiModelProperty(value = "开启搜索")
    @QueryField
    private Boolean enableQuery;

    @ApiModelProperty(value = "开启列表")
    @QueryField
    private Boolean enableList;

    @ApiModelProperty(value = "开启表单")
    @QueryField
    private Boolean enableForm;

    @ApiModelProperty(value = "开启校验")
    @QueryField
    private Boolean enableValidate;

    @ApiModelProperty(value = "作者")
    @QueryField
    private String author;

    @ApiModelProperty(value = "允许覆盖")
    @QueryField
    private Boolean enableCover;

    @ApiModelProperty(value = "开启Swagger")
    @QueryField
    private Boolean enableSwagger;

    @ApiModelProperty(value = "模块名称")
    @QueryField
    private String moduleName;

    @ApiModelProperty(value = "包路径")
    @QueryField
    private String packagePath;

    @ApiModelProperty(value = "输出路径")
    @QueryField
    private String outputPath;

    @ApiModelProperty(value = "后端代码路径")
    @QueryField
    private String backendPath;

    @ApiModelProperty(value = "前端代码路径")
    @QueryField
    private String frontendPath;

    @ApiModelProperty(value = "表前缀")
    @QueryField
    private String prefix;

    @ApiModelProperty(value = "REST接口名称")
    @QueryField
    private String apiUrl;

}
