package io.github.dunwu.module.sys.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表字段锁定记录表 Query 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "TableColumnLockLogQuery", description = "表字段锁定记录表 Query 实体")
public class TableColumnLockLogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`", type = QueryField.QueryType.EQUALS)
    private Long id;

    @ApiModelProperty(value = "库名")
    @QueryField(value = "`schema_name`", type = QueryField.QueryType.EQUALS)
    private String schemaName;

    @ApiModelProperty(value = "表名")
    @QueryField(value = "`table_name`", type = QueryField.QueryType.EQUALS)
    private String tableName;

    @ApiModelProperty(value = "列名")
    @QueryField(value = "`column_name`", type = QueryField.QueryType.EQUALS)
    private String columnName;

    @ApiModelProperty(value = "行ID")
    @QueryField(value = "`row_id`", type = QueryField.QueryType.EQUALS)
    private String rowId;

    @ApiModelProperty(value = "单元值")
    @QueryField(value = "`cell_value`", type = QueryField.QueryType.EQUALS)
    private String cellValue;

    @ApiModelProperty(value = "操作类型")
    @QueryField(value = "`operation`", type = QueryField.QueryType.EQUALS)
    private String operation;

    @ApiModelProperty(value = "创建者")
    @QueryField(value = "`create_by`", type = QueryField.QueryType.EQUALS)
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField(value = "`update_by`", type = QueryField.QueryType.EQUALS)
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`create_time`", type = QueryField.QueryType.EQUALS)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.EQUALS)
    private LocalDateTime updateTime;

}
