package io.github.dunwu.modules.generator.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.util.GenUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成-字段配置 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfigDto", description = "代码生成-字段配置")
public class CodeColumnConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "数据库Schema")
    private String tableSchema;

    @ApiModelProperty(value = "数据库Table")
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    private String columnName;

    @ApiModelProperty(value = "字段类型")
    private String columnType;

    @ApiModelProperty(value = "字段KEY类型")
    private String columnKey;

    @ApiModelProperty(value = "字段备注")
    private String columnComment;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "扩展属性")
    private String extra;

    @ApiModelProperty(value = "是否出现在表单")
    private Boolean formShow;

    @ApiModelProperty(value = "表单类型")
    private String formType;

    @ApiModelProperty(value = "是否出现在列表")
    private Boolean listShow;

    @ApiModelProperty(value = "不允许为空")
    private Boolean notNull;

    @ApiModelProperty(value = "查询类型")
    private String queryType;

    @ApiModelProperty(value = "日期表达式")
    private String dateExpression;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public CodeColumnConfigDto(String tableName, String columnName, Boolean notNull, String columnType, String remark,
        String columnKey, String extra) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnKey = columnKey;
        this.extra = extra;
        this.notNull = notNull;
        if (GenUtil.PK.equalsIgnoreCase(columnKey) && GenUtil.EXTRA.equalsIgnoreCase(extra)) {
            this.notNull = false;
        }
        this.note = remark;
        this.listShow = true;
        this.formShow = true;
    }

}
