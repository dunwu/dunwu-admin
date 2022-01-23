package io.github.dunwu.module.sys.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典选项 Dto 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-22
 */
@Data
@Accessors(chain = true)
@ExcelIgnoreUnannotated
@ApiModel(value = "DictOptionDto", description = "数据字典选项 Dto 实体")
public class DictOptionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelProperty("ID")
    @ApiModelProperty(value = "ID")
    private Long id;

    @ExcelProperty("字典ID")
    @ApiModelProperty(value = "字典ID")
    private Long dictId;

    @ExcelProperty("字典选项编码")
    @ApiModelProperty(value = "字典选项编码")
    private String code;

    @ExcelProperty("字典选项名称")
    @ApiModelProperty(value = "字典选项名称")
    private String name;

    @ExcelProperty("备注")
    @ApiModelProperty(value = "备注")
    private String note;

    @ExcelProperty("是否禁用")
    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    private Boolean disabled;

    @ExcelProperty("创建者")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ExcelProperty("更新者")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ExcelProperty("创建时间")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ExcelProperty("更新时间")
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
