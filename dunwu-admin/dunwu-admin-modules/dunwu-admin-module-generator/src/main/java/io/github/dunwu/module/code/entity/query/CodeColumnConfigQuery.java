package io.github.dunwu.module.code.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成-字段级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CodeColumnConfigQuery", description = "代码生成-字段级别配置")
public class CodeColumnConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "数据库ID")
    @QueryField
    private Long dbId;

    @ApiModelProperty(value = "所属表的ID")
    @QueryField
    private Long tableId;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @QueryField
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    @QueryField
    private String columnName;

    @ApiModelProperty(value = "字段注释")
    @QueryField
    private String comment;

    @ApiModelProperty(value = "字段数据类型")
    @QueryField
    private String type;

    @ApiModelProperty(value = "字段Java类型")
    @QueryField
    private String javaType;

    @ApiModelProperty(value = "键类型")
    @QueryField
    private String keyType;

    @ApiModelProperty(value = "不允许为空")
    @QueryField
    private Boolean notNull;

    @ApiModelProperty(value = "字段别名")
    @QueryField
    private String propertyName;

    @ApiModelProperty(value = "字段Label")
    @QueryField
    private String labelName;

    @ApiModelProperty(value = "允许表单")
    @QueryField
    private Boolean enableForm;

    @ApiModelProperty(value = "允许列表")
    @QueryField
    private Boolean enableList;

    @ApiModelProperty(value = "允许查询")
    @QueryField
    private Boolean enableQuery;

    @ApiModelProperty(value = "允许排序")
    @QueryField
    private Boolean enableSort;

    @ApiModelProperty(value = "允许校验")
    @QueryField
    private Boolean enableValidate;

    @ApiModelProperty(value = "表单类型")
    @QueryField
    private String formType;

    @ApiModelProperty(value = "列表类型")
    @QueryField
    private String listType;

    @ApiModelProperty(value = "查询类型")
    @QueryField
    private String queryType;

    @ApiModelProperty(value = "排序类型")
    @QueryField
    private String sortType;

    @ApiModelProperty(value = "校验类型")
    @QueryField
    private String validateType;

    @ApiModelProperty(value = "时间类型")
    @QueryField
    private String dateType;

    @ApiModelProperty(value = "时间格式")
    @QueryField
    private String datePattern;

    @ApiModelProperty(value = "字典编码")
    @QueryField
    private String dictCode;

    @ApiModelProperty(value = "@TableField填充属性")
    @QueryField
    private String fill;

    @ApiModelProperty(value = "扩展属性")
    @QueryField
    private String extra;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

    @ApiModelProperty(value = "更新时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> updateTime;

    @ApiModelProperty(value = "数据库Table")
    @QueryField(value = "tableName", type = QueryField.QueryType.IN)
    private List<String> tables;

}
