package io.github.dunwu.modules.generator.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成-字段配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfigQuery", description = "代码生成-字段配置")
public class CodeColumnConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库Schema")
    @QueryField
    private String tableSchema;

    @ApiModelProperty(value = "数据库Table")
    @QueryField
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    @QueryField
    private String columnName;

    @ApiModelProperty(value = "字段类型")
    @QueryField
    private String columnType;

    @ApiModelProperty(value = "字段KEY类型")
    @QueryField
    private String columnKey;

    @ApiModelProperty(value = "字段备注")
    @QueryField
    private String columnComment;

    @ApiModelProperty(value = "字典名称")
    @QueryField
    private String dictName;

    @ApiModelProperty(value = "扩展属性")
    @QueryField
    private String extra;

    @ApiModelProperty(value = "是否出现在表单")
    @QueryField
    private Boolean formShow;

    @ApiModelProperty(value = "表单类型")
    @QueryField
    private String formType;

    @ApiModelProperty(value = "是否出现在列表")
    @QueryField
    private Boolean listShow;

    @ApiModelProperty(value = "不允许为空")
    @QueryField
    private Boolean notNull;

    @ApiModelProperty(value = "查询类型")
    @QueryField
    private String queryType;

    @ApiModelProperty(value = "日期表达式")
    @QueryField
    private String dateExpression;

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
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

    @ApiModelProperty(value = "更新时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> updateTime;

    @ApiModelProperty(value = "数据库Table")
    @QueryField(name = "tableName", type = QueryField.QueryType.IN)
    private List<String> tables;

}
