package io.github.dunwu.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 系统日志记录
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log")
@ApiModel(value = "Log", description = "系统日志记录")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "日志级别")
    @TableField("level")
    private String level;

    @ApiModelProperty(value = "业务类型")
    @TableField("biz_type")
    private String bizType;

    @ApiModelProperty(value = "日志消息")
    @TableField("message")
    private String message;

    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    @TableField("exception_message")
    private String exceptionMessage;

    @ApiModelProperty(value = "操作的类名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "操作的方法名")
    @TableField("method_name")
    private String methodName;

    @ApiModelProperty(value = "被调用方法的参数")
    @TableField("params")
    private String params;

    @ApiModelProperty(value = "操作类型")
    @TableField("operate_type")
    private String operateType;

    @ApiModelProperty(value = "操作者ID")
    @TableField("operator_id")
    private Long operatorId;

    @ApiModelProperty(value = "操作者用户名")
    @TableField("operator_name")
    private String operatorName;

    @ApiModelProperty(value = "服务端IP地址")
    @TableField("server_ip")
    private String serverIp;

    @ApiModelProperty(value = "客户端IP地址")
    @TableField("client_ip")
    private String clientIp;

    @ApiModelProperty(value = "客户端地理位置")
    @TableField("client_location")
    private String clientLocation;

    @ApiModelProperty(value = "客户端设备")
    @TableField("client_device")
    private String clientDevice;

    @ApiModelProperty(value = "HTTP请求的耗时")
    @TableField("request_time")
    private Long requestTime;

    @ApiModelProperty(value = "日志记录时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    public static final String ID = "id";
    public static final String LEVEL = "level";
    public static final String BIZ_TYPE = "biz_type";
    public static final String MESSAGE = "message";
    public static final String EXCEPTION_MESSAGE = "exception_message";
    public static final String CLASS_NAME = "class_name";
    public static final String METHOD_NAME = "method_name";
    public static final String PARAMS = "params";
    public static final String OPERATE_TYPE = "operate_type";
    public static final String OPERATOR_ID = "operator_id";
    public static final String OPERATOR_NAME = "operator_name";
    public static final String SERVER_IP = "server_ip";
    public static final String CLIENT_IP = "client_ip";
    public static final String CLIENT_LOCATION = "client_location";
    public static final String CLIENT_DEVICE = "client_device";
    public static final String REQUEST_TIME = "request_time";
    public static final String CREATE_TIME = "create_time";

}
