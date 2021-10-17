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
 * 菜单表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "MenuQuery", description = "菜单表")
public class MenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    @QueryField(value = "`pid`")
    private Long pid;

    @ApiModelProperty(value = "编码")
    @QueryField(value = "`code`")
    private String code;

    @ApiModelProperty(value = "名称")
    @QueryField(value = "`name`")
    private String name;

    @ApiModelProperty(value = "菜单类型")
    @QueryField(value = "`menu_type`")
    private Integer menuType;

    @ApiModelProperty(value = "是否外链")
    @QueryField(value = "`is_frame`")
    private Boolean frame;

    @ApiModelProperty(value = "缓存")
    @QueryField(value = "`is_cached`")
    private Boolean cached;

    @ApiModelProperty(value = "隐藏")
    @QueryField(value = "`is_hidden`")
    private Boolean hidden;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private Collection<LocalDateTime> updateTimeRange;

}
