package io.github.dunwu.module.sys.entity.query;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 数据字典选项 Query 实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DictOptionQuery", description = "数据字典选项 Query 实体")
public class DictOptionQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`", type = QueryField.QueryType.EQUALS)
    private Long id;

    @ApiModelProperty(value = "字典id")
    @QueryField(value = "`dict_id`", type = QueryField.QueryType.EQUALS)
    private Long dictId;

    @ApiModelProperty(value = "字典选项编码")
    @QueryField(value = "`code`", type = QueryField.QueryType.LIKE)
    private String code;

    @ApiModelProperty(value = "字典选项值")
    @QueryField(value = "`value`", type = QueryField.QueryType.LIKE)
    private String value;

    @ApiModelProperty(value = "字典选项名称")
    @QueryField(value = "`name`", type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`", type = QueryField.QueryType.EQUALS)
    private Boolean disabled;

    public String toJsonStr() {
        return JSONUtil.toJsonStr(this);
    }

}
