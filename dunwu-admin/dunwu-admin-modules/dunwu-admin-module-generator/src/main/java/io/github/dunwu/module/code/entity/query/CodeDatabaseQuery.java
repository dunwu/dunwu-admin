package io.github.dunwu.module.code.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据库管理 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "CodeDatabaseQuery", description = "数据库管理")
public class CodeDatabaseQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "数据库名称")
    @QueryField
    private String name;

    @ApiModelProperty(value = "Host")
    @QueryField
    private String host;

    @ApiModelProperty(value = "端口号")
    @QueryField
    private Integer port;

    @ApiModelProperty(value = "jdbc地址")
    @QueryField
    private String jdbcUrl;

    @ApiModelProperty(value = "账号")
    @QueryField
    private String username;

    @ApiModelProperty(value = "密码")
    @QueryField
    private String password;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime updateTime;

}
