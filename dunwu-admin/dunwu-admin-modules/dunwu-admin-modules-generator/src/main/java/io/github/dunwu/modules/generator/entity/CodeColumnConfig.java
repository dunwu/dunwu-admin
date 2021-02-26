package io.github.dunwu.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 代码生成-字段配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfig", description = "代码生成-字段配置")
public class CodeColumnConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
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

}
