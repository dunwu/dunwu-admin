package io.github.dunwu.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 系统全局配置表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_global_config")
@ApiModel(value = "GlobalConfig", description = "系统全局配置表")
public class GlobalConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应用编码")
    @TableField("`app_code`")
    private String appCode;

    @ApiModelProperty(value = "模块编码")
    @TableField("`module_code`")
    private String moduleCode;

    @ApiModelProperty(value = "命名空间")
    @TableField("`namespace`")
    private String namespace;

    @ApiModelProperty(value = "配置项编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "配置项配置名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "配置项值")
    @TableField("`value`")
    private String value;

    @ApiModelProperty(value = "配置项默认值")
    @TableField("`default_value`")
    private String defaultValue;

    @ApiModelProperty(value = "配置项值类型")
    @TableField("`value_type`")
    private String valueType;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @TableField("`is_disabled`")
    private Boolean isDisabled;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

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
    public static final String APP_CODE = "app_code";
    public static final String MODULE_CODE = "module_code";
    public static final String NAMESPACE = "namespace";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String DEFAULT_VALUE = "default_value";
    public static final String VALUE_TYPE = "value_type";
    public static final String IS_DISABLED = "is_disabled";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
