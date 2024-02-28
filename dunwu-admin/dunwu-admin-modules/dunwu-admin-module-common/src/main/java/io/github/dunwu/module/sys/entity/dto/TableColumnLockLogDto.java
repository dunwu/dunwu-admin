package io.github.dunwu.module.sys.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表字段锁定记录表 Dto 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
@Data
@ExcelIgnoreUnannotated
@ApiModel(value = "TableColumnLockLogDto", description = "表字段锁定记录表 Dto 实体")
public class TableColumnLockLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @ExcelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "库名")
    @ExcelProperty(value = "库名")
    private String schemaName;

    @ApiModelProperty(value = "表名")
    @ExcelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "列名")
    @ExcelProperty(value = "列名")
    private String columnName;

    @ApiModelProperty(value = "行ID")
    @ExcelProperty(value = "行ID")
    private String rowId;

    @ApiModelProperty(value = "单元值")
    @ExcelProperty(value = "单元值")
    private String cellValue;

    @ApiModelProperty(value = "操作类型")
    @ExcelProperty(value = "操作类型")
    private String operation;

    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @ExcelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @ExcelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
