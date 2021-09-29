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
 * 系统数据字典项 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictOptionQuery", description = "系统数据字典项")
public class DictOptionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属字典ID")
    @QueryField
    private Long dictId;

    @ApiModelProperty(value = "字典项编码")
    @QueryField
    private String code;

    @ApiModelProperty(value = "字典项名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "排序")
    @QueryField
    private Integer sequence;

    @ApiModelProperty(value = "状态")
    @QueryField
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

}
