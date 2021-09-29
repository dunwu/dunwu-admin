package io.github.dunwu.module.sys.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志记录 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogDto", description = "系统日志记录")
public class LogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "日志级别")
    private String level;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "日志消息")
    private String message;

    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    private String exceptionMessage;

    @ApiModelProperty(value = "操作的类名")
    private String className;

    @ApiModelProperty(value = "操作的方法名")
    private String methodName;

    @ApiModelProperty(value = "被调用方法的参数")
    private String params;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

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

    @ApiModelProperty(value = "HTTP请求的耗时")
    private Long requestTime;

    @ApiModelProperty(value = "日志记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
