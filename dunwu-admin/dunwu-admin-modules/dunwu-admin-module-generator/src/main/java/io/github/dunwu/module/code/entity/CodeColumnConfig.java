package io.github.dunwu.module.code.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成-字段级别配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfig", description = "代码生成-字段级别配置")
public class CodeColumnConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "数据库ID")
    private Long dbId;

    @ApiModelProperty(value = "所属表的ID")
    private Long tableId;

    @ApiModelProperty(value = "Schema名称")
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    private String columnName;

    @ApiModelProperty(value = "字段注释")
    private String comment;

    @ApiModelProperty(value = "字段数据类型")
    private String type;

    @ApiModelProperty(value = "字段Java类型")
    private String javaType;

    @ApiModelProperty(value = "键类型")
    private String keyType;

    @ApiModelProperty(value = "不允许为空")
    private Boolean notNull;

    @ApiModelProperty(value = "字段别名")
    private String propertyName;

    @ApiModelProperty(value = "字段Label")
    private String labelName;

    @ApiModelProperty(value = "允许表单")
    private Boolean enableForm;

    @ApiModelProperty(value = "允许列表")
    private Boolean enableList;

    @ApiModelProperty(value = "允许查询")
    private Boolean enableQuery;

    @ApiModelProperty(value = "允许排序")
    private Boolean enableSort;

    @ApiModelProperty(value = "允许校验")
    private Boolean enableValidate;

    @ApiModelProperty(value = "表单类型")
    private String formType;

    @ApiModelProperty(value = "列表类型")
    private String listType;

    @ApiModelProperty(value = "查询类型")
    private String queryType;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "校验类型")
    private String validateType;

    @ApiModelProperty(value = "时间类型")
    private String dateType;

    @ApiModelProperty(value = "时间格式")
    private String datePattern;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "@TableField填充属性")
    private String fill;

    @ApiModelProperty(value = "扩展属性")
    private String extra;

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
