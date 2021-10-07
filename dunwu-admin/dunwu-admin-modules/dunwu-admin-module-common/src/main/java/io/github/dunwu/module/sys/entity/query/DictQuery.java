package io.github.dunwu.module.sys.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 数据字典 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictQuery", description = "数据字典")
public class DictQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "code", "name" }, type = QueryField.QueryType.LIKE)
    private String blurry;

    @ApiModelProperty(value = "字典编码")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String code;

    @ApiModelProperty(value = "字典名称")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField
    private Boolean isDisabled;

}
