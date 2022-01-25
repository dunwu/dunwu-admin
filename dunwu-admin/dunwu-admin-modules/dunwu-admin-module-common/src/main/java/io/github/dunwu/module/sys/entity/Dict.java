package io.github.dunwu.module.sys.entity;

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
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-03
 */
@Data
@EqualsAndHashCode
@TableName("sys_dict")
@ApiModel(value = "Dict", description = "数据字典实体")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty("ID")
    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ExcelProperty("字典编码")
    @ApiModelProperty(value = "字典编码")
    @NotBlank(groups = { AddCheck.class, EditCheck.class })
    @TableField("`code`")
    private String code;

    @ExcelProperty("字典名称")
    @ApiModelProperty(value = "字典名称")
    @NotBlank(groups = { AddCheck.class, EditCheck.class })
    @TableField("`name`")
    private String name;

    @ExcelProperty("备注")
    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    @ExcelProperty("是否禁用")
    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @NotNull(groups = { AddCheck.class, EditCheck.class })
    @TableField("`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @TableField("`update_by`")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String NOTE = "note";
    public static final String IS_DISABLED = "is_disabled";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
