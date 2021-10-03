package io.github.dunwu.module.mnt.entity.query;

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
 * 应用配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppQuery", description = "应用配置")
public class AppQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "应用名称")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "应用端口")
    @QueryField
    private Integer port;

    @ApiModelProperty(value = "创建时间")
    @QueryField(value = "createTime", type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTimeRange;

}
