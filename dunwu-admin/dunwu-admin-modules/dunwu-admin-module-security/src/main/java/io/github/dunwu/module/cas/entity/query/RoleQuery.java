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
 * 角色表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "RoleQuery", description = "角色表")
public class RoleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`", type = QueryField.QueryType.IN)
    private Collection<? extends Serializable> ids;

    @ApiModelProperty(value = "角色编码")
    @QueryField(value = "`code`")
    private String code;

    @ApiModelProperty(value = "角色名称")
    @QueryField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "角色级别")
    @QueryField(value = "`level`")
    private Integer level;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Collection<LocalDateTime> updateTimeRange;

    @ApiModelProperty(value = "角色编码、角色名称混合模糊查询")
    @QueryField(blurry = { "code", "name" }, type = QueryField.QueryType.LIKE)
    private String blurry;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

}
