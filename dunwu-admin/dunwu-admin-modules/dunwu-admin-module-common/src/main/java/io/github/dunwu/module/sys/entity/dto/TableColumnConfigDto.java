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
 * 表字段配置表 Dto 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Data
@ExcelIgnoreUnannotated
@ApiModel(value = "TableColumnConfigDto", description = "表字段配置表 Dto 实体")
public class TableColumnConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @ExcelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "库名")
    @ExcelProperty(value = "库名")
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @ExcelProperty(value = "Table名称")
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    @ExcelProperty(value = "字段名称")
    private String columnName;

    @ApiModelProperty(value = "字段备注")
    @ExcelProperty(value = "字段备注")
    private String columnComment;

    @ApiModelProperty(value = "字段数据类型")
    @ExcelProperty(value = "字段数据类型")
    private String dataType;

    @ApiModelProperty(value = "是否锁定")
    @ExcelProperty(value = "是否锁定")
    private Boolean locked;

    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @ExcelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "操作时间")
    @ExcelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作时间")
    @ExcelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
