package io.github.dunwu.module.code.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成-表级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfigQuery", description = "代码生成-表级别配置")
public class CodeTableConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库ID")
    @QueryField
    private Long dbId;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String tableName;

    @ApiModelProperty(value = "输出路径")
    @QueryField
    private String outputDir;

    @ApiModelProperty(value = "后端代码路径")
    @QueryField
    private String backendPath;

    @ApiModelProperty(value = "前端代码路径")
    @QueryField
    private String frontendPath;

    @ApiModelProperty(value = "包路径")
    @QueryField
    private String packagePath;

    @ApiModelProperty(value = "模块名称")
    @QueryField
    private String moduleName;

    @ApiModelProperty(value = "REST接口根路径")
    @QueryField
    private String apiBaseUrl;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

}
