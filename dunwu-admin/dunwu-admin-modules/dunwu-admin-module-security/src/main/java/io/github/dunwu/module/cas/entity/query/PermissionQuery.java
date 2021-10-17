package io.github.dunwu.module.cas.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 权限表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "PermissionQuery", description = "权限表")
public class PermissionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    @QueryField(value = "`pid`")
    private Long pid;

    @ApiModelProperty(value = "资源ID")
    @QueryField(value = "`resource_id`")
    private Long resourceId;

    @ApiModelProperty(value = "权限编码")
    @QueryField(value = "`code`")
    private String code;

    @ApiModelProperty(value = "权限名称")
    @QueryField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "权限类型")
    @QueryField(value = "`type`")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    @QueryField(value = "`expression`")
    private String expression;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private Collection<LocalDateTime> updateTimeRange;

}
