package io.github.dunwu.module.sys.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 表字段锁定记录表实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
@Data
@ExcelIgnoreUnannotated
@TableName("sys_table_column_lock_log")
@ApiModel(value = "TableColumnLockLog", description = "表字段锁定记录表实体")
public class TableColumnLockLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @ExcelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "库名")
    @ExcelProperty(value = "库名")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`schema_name`")
    private String schemaName;

    @ApiModelProperty(value = "表名")
    @ExcelProperty(value = "表名")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`table_name`")
    private String tableName;

    @ApiModelProperty(value = "列名")
    @ExcelProperty(value = "列名")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`column_name`")
    private String columnName;

    @ApiModelProperty(value = "行ID")
    @ExcelProperty(value = "行ID")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`row_id`")
    private String rowId;

    @ApiModelProperty(value = "单元值")
    @ExcelProperty(value = "单元值")
    @TableField("`cell_value`")
    private String cellValue;

    @ApiModelProperty(value = "操作类型")
    @ExcelProperty(value = "操作类型")
    @TableField("`operation`")
    private String operation;

    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @ExcelProperty(value = "更新者")
    @TableField("`update_by`")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
