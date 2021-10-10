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
import java.util.Set;

/**
 * 职务表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "JobQuery", description = "职务表")
public class JobQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`", type = QueryField.QueryType.IN)
    private Set<? extends Serializable> ids;

    @QueryField(blurry = { "id", "name" })
    private String blurry;

    @ApiModelProperty(value = "职务名称")
    @QueryField(value = "`name`", type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "职务类型")
    @QueryField(value = "`type`")
    private Integer type;

    @ApiModelProperty(value = "职级")
    @QueryField(value = "`level`")
    private Integer level;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private List<LocalDateTime> updateTimeRange;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

}
