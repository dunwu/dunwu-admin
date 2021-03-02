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
 * 代码生成-字段级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfigQuery", description = "代码生成-字段级别配置")
public class CodeColumnConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @QueryField
    private String tableName;

    @ApiModelProperty(value = "Table ID")
    @QueryField
    private Long tableId;

    @ApiModelProperty(value = "字段名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "字段展示名称（实体字段）")
    @QueryField
    private String propertyName;

    @ApiModelProperty(value = "字段注释")
    @QueryField
    private String comment;

    @ApiModelProperty(value = "字段数据类型")
    @QueryField
    private String type;

    @ApiModelProperty(value = "字段 Java 类型")
    @QueryField
    private String javaType;

    @ApiModelProperty(value = "字段KEY类型")
    @QueryField
    private String keyType;

    @ApiModelProperty(value = "不允许为空")
    @QueryField
    private Boolean notNull;

    @ApiModelProperty(value = "出现在表单")
    @QueryField
    private Boolean enableForm;

    @ApiModelProperty(value = "出现在列表")
    @QueryField
    private Boolean enableList;

    @ApiModelProperty(value = "出现在搜索")
    @QueryField
    private Boolean enableQuery;

    @ApiModelProperty(value = "前端表单类型")
    @QueryField
    private String formType;

    @ApiModelProperty(value = "前端列表类型")
    @QueryField
    private Boolean listShow;

    @ApiModelProperty(value = "前端搜索类型")
    @QueryField
    private String queryType;

    @ApiModelProperty(value = "是否为排序字段")
    @QueryField
    private Boolean enableSort;

    @ApiModelProperty(value = "排序类型")
    @QueryField
    private String sortType;

    @ApiModelProperty(value = "日期表达式")
    @QueryField
    private String dateExpression;

    @ApiModelProperty(value = "字典名称")
    @QueryField
    private String dictName;

    @ApiModelProperty(value = "@TableField 填充属性")
    @QueryField
    private String fill;

    @ApiModelProperty(value = "扩展属性")
    @QueryField
    private String extra;

    @ApiModelProperty(value = "状态")
    @QueryField
    private Boolean enabled;

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
