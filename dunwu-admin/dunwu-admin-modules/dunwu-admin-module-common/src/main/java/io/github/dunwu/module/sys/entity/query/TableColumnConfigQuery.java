package io.github.dunwu.module.sys.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表字段配置表 Query 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TableColumnConfigQuery", description = "表字段配置表 Query 实体")
public class TableColumnConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库名")
    @QueryField(value ="`schema_name`", type = QueryField.QueryType.EQUALS)
    private String schemaName;

    @ApiModelProperty(value = "表名")
    @QueryField(value = "`table_name`", type = QueryField.QueryType.EQUALS)
    private String tableName;

}
