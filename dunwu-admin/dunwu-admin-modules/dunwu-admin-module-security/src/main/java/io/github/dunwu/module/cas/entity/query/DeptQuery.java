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
 * 部门表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeptQuery", description = "部门表")
public class DeptQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "上级部门ID")
    @QueryField(value = "`pid`")
    private Long pid;

    @ApiModelProperty(value = "部门名称")
    @QueryField(value = "`name`", type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "部门等级")
    @QueryField(value = "`level`")
    private Integer level;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private Collection<LocalDateTime> updateTimeRange;

}
