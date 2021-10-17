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

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 角色表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_role")
@ApiModel(value = "Role", description = "角色表")
public class Role extends BaseConfigEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色编码")
    @TableField("`code`")
    private String code;

    @ApiModelProperty(value = "角色名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "角色级别")
    @TableField("`level`")
    private Integer level;

    @ApiModelProperty(value = "数据权限")
    @TableField("`data_scope`")
    private String dataScope;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String LEVEL = "level";
    public static final String DATA_SCOPE = "data_scope";
    public static final String NOTE = "note";

}
