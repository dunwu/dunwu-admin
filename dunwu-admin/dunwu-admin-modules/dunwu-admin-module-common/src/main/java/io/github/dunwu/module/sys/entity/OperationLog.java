package io.github.dunwu.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 操作日志表实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@Data
@Accessors(chain = true)
@TableName("sys_operation_log")
@ApiModel(value = "OperationLog", description = "操作日志表实体")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应用名")
    @TableField("`app_name`")
    private String appName;

    @ApiModelProperty(value = "业务编码")
    @TableField("`biz_no`")
    private String bizNo;

    @ApiModelProperty(value = "业务类型")
    @TableField("`biz_type`")
    private String bizType;

    @ApiModelProperty(value = "是否成功：0.失败；1.成功")
    @TableField("`is_success`")
    private Boolean success;

    @ApiModelProperty(value = "基本信息")
    @TableField("`message`")
    private String message;

    @ApiModelProperty(value = "详情信息")
    @TableField("`detail`")
    private String detail;

    @ApiModelProperty(value = "异常信息")
    @TableField("`exception`")
    private String exception;

    @ApiModelProperty(value = "类名")
    @TableField("`class_name`")
    private String className;

    @ApiModelProperty(value = "方法名")
    @TableField("`method_name`")
    private String methodName;

    @ApiModelProperty(value = "方法参数")
    @TableField("`params`")
    private String params;

    @ApiModelProperty(value = "操作类型")
    @TableField("`operation`")
    private String operation;

    @ApiModelProperty(value = "操作者ID")
    @TableField("`operator_id`")
    private Long operatorId;

    @ApiModelProperty(value = "操作者用户名")
    @TableField("`operator_name`")
    private String operatorName;

    @ApiModelProperty(value = "服务端IP地址")
    @TableField("`server_ip`")
    private String serverIp;

    @ApiModelProperty(value = "客户端IP地址")
    @TableField("`client_ip`")
    private String clientIp;

    @ApiModelProperty(value = "客户端地理位置")
    @TableField("`client_location`")
    private String clientLocation;

    @ApiModelProperty(value = "客户端设备")
    @TableField("`client_device`")
    private String clientDevice;

    @ApiModelProperty(value = "操作耗时")
    @TableField("`request_time`")
    private Long requestTime;

    @ApiModelProperty(value = "操作时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public static final String ID = "id";
    public static final String APP_NAME = "app_name";
    public static final String BIZ_NO = "biz_no";
    public static final String BIZ_TYPE = "biz_type";
    public static final String TABLE = "table";
    public static final String IS_SUCCESS = "is_success";
    public static final String MESSAGE = "message";
    public static final String DETAIL = "detail";
    public static final String EXCEPTION = "exception";
    public static final String CLASS_NAME = "class_name";
    public static final String METHOD_NAME = "method_name";
    public static final String PARAMS = "params";
    public static final String OPERATION = "operation";
    public static final String OPERATOR_ID = "operator_id";
    public static final String OPERATOR_NAME = "operator_name";
    public static final String SERVER_IP = "server_ip";
    public static final String CLIENT_IP = "client_ip";
    public static final String CLIENT_LOCATION = "client_location";
    public static final String CLIENT_DEVICE = "client_device";
    public static final String REQUEST_TIME = "request_time";
    public static final String CREATE_TIME = "create_time";

}
