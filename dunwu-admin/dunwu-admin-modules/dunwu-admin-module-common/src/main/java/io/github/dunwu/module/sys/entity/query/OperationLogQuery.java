package io.github.dunwu.module.sys.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作日志表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "OperationLogQuery", description = "操作日志表 Query 类")
public class OperationLogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "应用名")
    @QueryField(value = "`app_name`")
    private String appName;

    @ApiModelProperty(value = "业务编码")
    @QueryField(value = "`biz_no`")
    private String bizNo;

    @ApiModelProperty(value = "业务类型")
    @QueryField(value = "`biz_type`")
    private String bizType;

    @ApiModelProperty(value = "是否成功：0.失败；1.成功")
    @QueryField(value = "`is_success`")
    private Boolean success;

    @ApiModelProperty(value = "基本信息")
    @QueryField(value = "`message`")
    private String message;

    @ApiModelProperty(value = "详情信息")
    @QueryField(value = "`detail`")
    private String detail;

    @ApiModelProperty(value = "异常信息")
    @QueryField(value = "`exception`")
    private String exception;

    @ApiModelProperty(value = "类名")
    @QueryField(value = "`class_name`")
    private String className;

    @ApiModelProperty(value = "方法名")
    @QueryField(value = "`method_name`")
    private String methodName;

    @ApiModelProperty(value = "操作类型")
    @QueryField(value = "`operation`")
    private String operation;

    @ApiModelProperty(value = "操作者ID")
    @QueryField(value = "`operator_id`")
    private Long operatorId;

    @ApiModelProperty(value = "操作者用户名")
    @QueryField(value = "`operator_name`")
    private String operatorName;

    @ApiModelProperty(value = "服务端IP地址")
    @QueryField(value = "`server_ip`")
    private String serverIp;

    @ApiModelProperty(value = "客户端IP地址")
    @QueryField(value = "`client_ip`")
    private String clientIp;

    @ApiModelProperty(value = "客户端地理位置")
    @QueryField(value = "`client_location`")
    private String clientLocation;

    @ApiModelProperty(value = "客户端设备")
    @QueryField(value = "`client_device`")
    private String clientDevice;

    @ApiModelProperty(value = "创建时间")
    @QueryField(value = "createTime", type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTimeRange;

}
