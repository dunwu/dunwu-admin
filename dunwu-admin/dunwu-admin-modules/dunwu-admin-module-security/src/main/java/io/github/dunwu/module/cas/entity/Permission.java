package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.common.entity.BaseConfigEntity;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 权限表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_permission")
@ApiModel(value = "Permission", description = "权限表")
public class Permission extends BaseConfigEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "资源ID")
    @TableField("`resource_id`")
    private Long resourceId;

    @ApiModelProperty(value = "权限编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "权限名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "权限类型")
    @TableField("`type`")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    @TableField("`expression`")
    private String expression;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String RESOURCE_ID = "resource_id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String EXPRESSION = "expression";
    public static final String NOTE = "note";

}
