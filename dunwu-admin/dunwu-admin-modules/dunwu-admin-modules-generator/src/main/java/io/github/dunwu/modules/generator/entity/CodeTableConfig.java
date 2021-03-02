package io.github.dunwu.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 代码生成-表级别配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfig", description = "代码生成-表级别配置")
public class CodeTableConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Schema名称")
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    private String name;

    @ApiModelProperty(value = "Table注释")
    private String comment;

    @ApiModelProperty(value = "开启权限校验")
    private Boolean enablePermission;

    @ApiModelProperty(value = "开启搜索")
    private Boolean enableQuery;

    @ApiModelProperty(value = "开启列表")
    private Boolean enableList;

    @ApiModelProperty(value = "开启表单")
    private Boolean enableForm;

    @ApiModelProperty(value = "开启校验")
    private Boolean enableValidate;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "允许覆盖")
    private Boolean enableCover;

    @ApiModelProperty(value = "开启Swagger")
    private Boolean enableSwagger;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "包路径")
    private String packagePath;

    @ApiModelProperty(value = "输出路径")
    private String outputPath;

    @ApiModelProperty(value = "后端代码路径")
    private String backendPath;

    @ApiModelProperty(value = "前端代码路径")
    private String frontendPath;

    @ApiModelProperty(value = "表前缀")
    private String prefix;

    @ApiModelProperty(value = "REST接口URL")
    private String apiUrl;

}
