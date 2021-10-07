package io.github.dunwu.module.cas.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MenuQuery", description = "菜单")
public class MenuQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "name", "title", "path", "component" }, type = QueryField.QueryType.LIKE)
    private String blurry;

    @ApiModelProperty(value = "上级菜单ID")
    @QueryField
    private Long pid;

    @ApiModelProperty(value = "创建时间")
    @QueryField(value = "createTime", type = QueryField.QueryType.BETWEEN)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTimeRange;

}
