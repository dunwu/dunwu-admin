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

/**
 * 部署历史管理 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DeployHistoryQuery", description = "部署历史管理")
public class DeployHistoryQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "部署编号")
    @QueryField
    private Long deployId;

    @ApiModelProperty(value = "应用名称")
    @QueryField
    private String appName;

    @ApiModelProperty(value = "部署日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime deployDate;

    @ApiModelProperty(value = "部署用户")
    @QueryField
    private String deployUser;

    @ApiModelProperty(value = "服务器IP")
    @QueryField
    private String ip;

}
