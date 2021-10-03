package io.github.dunwu.module.mnt.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用配置 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "AppDto", description = "应用配置")
public class AppDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "应用名称")
    private String name;

    @ApiModelProperty(value = "上传路径")
    private String uploadPath;

    @ApiModelProperty(value = "部署路径")
    private String deployPath;

    @ApiModelProperty(value = "备份路径")
    private String backupPath;

    @ApiModelProperty(value = "应用端口")
    private Integer port;

    @ApiModelProperty(value = "启动脚本")
    private String startScript;

    @ApiModelProperty(value = "部署脚本")
    private String deployScript;

    @ApiModelProperty(value = "备注")
    private String note;

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
