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
 * 表字段配置表实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Data
@ExcelIgnoreUnannotated
@TableName("sys_table_column_config")
@ApiModel(value = "TableColumnConfig", description = "表字段配置表实体")
public class TableColumnConfig implements Serializable {

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

    @ApiModelProperty(value = "字段名称")
    @ExcelProperty(value = "字段名称")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`column_name`")
    private String columnName;

    @ApiModelProperty(value = "是否锁定")
    @ExcelProperty(value = "是否锁定")
    @TableField("`is_locked`")
    private Boolean locked;

    @ApiModelProperty(value = "创建者")
    @ExcelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @ExcelProperty(value = "更新者")
    @TableField("`update_by`")
    private String updateBy;

    @ApiModelProperty(value = "操作时间")
    @ExcelProperty(value = "操作时间")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作时间")
    @ExcelProperty(value = "操作时间")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;


}
