package io.github.dunwu.module.sys.entity.query;

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
 * 数据字典详情 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictOptionQuery", description = "数据字典详情")
public class DictOptionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "字典id")
    @QueryField
    private Long dictId;

    @ApiModelProperty(value = "字典选项编码")
    @QueryField
    private String code;

    @ApiModelProperty(value = "字典选项名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTimeRange;

}
