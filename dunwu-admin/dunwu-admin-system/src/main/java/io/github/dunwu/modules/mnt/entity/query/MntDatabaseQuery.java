package io.github.dunwu.modules.mnt.entity.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MntDatabaseQuery", description = "")
public class MntDatabaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "jdbc连接")
    @QueryField
    private String jdbcUrl;

    @ApiModelProperty(value = "创建时间")
    @QueryField
    private LocalDateTime createTime;

}
