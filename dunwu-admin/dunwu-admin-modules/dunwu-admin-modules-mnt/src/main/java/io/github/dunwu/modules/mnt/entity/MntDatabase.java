package io.github.dunwu.modules.mnt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 数据库管理实体类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MntDatabase", description = "数据库管理")
public class MntDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "数据库名称")
    private String name;

    @NotNull
    @ApiModelProperty(value = "Host")
    private String host;

    @NotNull
    @ApiModelProperty(value = "端口号")
    private Integer port;

    @NotNull
    @ApiModelProperty(value = "jdbc地址")
    private String jdbcUrl;

    @NotNull
    @ApiModelProperty(value = "账号")
    private String username;

    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "Schema 名")
    private String schemaName;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
