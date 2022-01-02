package io.github.dunwu.module.sys.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OperationLogDto", description = "操作日志表")
public class OperationLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "应用名")
    private String appName;

    @ApiModelProperty(value = "业务编码")
    private String bizNo;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "是否成功：0.失败；1.成功")
    private Boolean success;

    @ApiModelProperty(value = "基本信息")
    private String message;

    @ApiModelProperty(value = "详情信息")
    private String detail;

    @ApiModelProperty(value = "异常信息")
    private String exception;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "方法参数")
    private String params;

    @ApiModelProperty(value = "操作类型")
    private String operation;

    @ApiModelProperty(value = "操作者ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作者用户名")
    private String operatorName;

    @ApiModelProperty(value = "服务端IP地址")
    private String serverIp;

    @ApiModelProperty(value = "客户端IP地址")
    private String clientIp;

    @ApiModelProperty(value = "客户端地理位置")
    private String clientLocation;

    @ApiModelProperty(value = "客户端设备")
    private String clientDevice;

    @ApiModelProperty(value = "操作耗时")
    private Long requestTime;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
