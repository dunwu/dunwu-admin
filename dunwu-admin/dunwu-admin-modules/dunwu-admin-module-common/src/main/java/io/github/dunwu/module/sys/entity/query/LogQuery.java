package io.github.dunwu.module.sys.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.module.sys.entity.Log;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志记录 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogQuery", description = "系统日志记录")
public class LogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { Log.MESSAGE, Log.METHOD_NAME }, type = QueryField.QueryType.LIKE)
    @ApiModelProperty(value = "模糊查询字段")
    private String blurry;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "日志级别")
    @QueryField
    private String level;

    @ApiModelProperty(value = "业务类型")
    @QueryField
    private String bizType;

    @ApiModelProperty(value = "日志消息")
    @QueryField
    private String message;

    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    @QueryField
    private String exceptionMessage;

    @ApiModelProperty(value = "操作的类名")
    @QueryField
    private String className;

    @ApiModelProperty(value = "操作的方法名")
    @QueryField
    private String methodName;

    @ApiModelProperty(value = "被调用方法的参数")
    @QueryField
    private String params;

    @ApiModelProperty(value = "操作类型")
    @QueryField
    private String operateType;

    @ApiModelProperty(value = "操作者ID")
    @QueryField
    private Long operatorId;

    @ApiModelProperty(value = "操作者用户名")
    @QueryField
    private String operatorName;

    @ApiModelProperty(value = "服务端IP地址")
    @QueryField
    private String serverIp;

    @ApiModelProperty(value = "客户端IP地址")
    @QueryField
    private String clientIp;

    @ApiModelProperty(value = "客户端地理位置")
    @QueryField
    private String clientLocation;

    @ApiModelProperty(value = "客户端设备")
    @QueryField
    private String clientDevice;

    @ApiModelProperty(value = "HTTP请求的耗时")
    @QueryField
    private Long requestTime;

    @ApiModelProperty(value = "日志记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime createTime;

}
