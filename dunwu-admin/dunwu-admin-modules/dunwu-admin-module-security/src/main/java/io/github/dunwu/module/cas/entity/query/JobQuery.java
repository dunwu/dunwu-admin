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
 * 岗位 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "JobQuery", description = "岗位")
public class JobQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "id", type = QueryField.QueryType.IN)
    private Set<Long> ids;

    @ApiModelProperty(value = "岗位名称")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "部门ID")
    @QueryField
    private Long deptId;

    @ApiModelProperty(value = "岗位状态")
    @QueryField
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

}
