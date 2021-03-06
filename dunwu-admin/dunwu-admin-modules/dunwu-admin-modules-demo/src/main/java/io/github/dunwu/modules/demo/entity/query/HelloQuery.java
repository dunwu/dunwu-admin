package io.github.dunwu.modules.demo.entity.query;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 测试 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "HelloQuery", description = "测试")
public class HelloQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年龄")
    @QueryField
    private Integer age;

    @QueryField(value = "age", type = QueryField.QueryType.BETWEEN)
    private List<Integer> ageRange;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "createTime", type = QueryField.QueryType.BETWEEN)
    private List<LocalDateTime> createTimeRange;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "名字")
    @QueryField
    private String name;
}
