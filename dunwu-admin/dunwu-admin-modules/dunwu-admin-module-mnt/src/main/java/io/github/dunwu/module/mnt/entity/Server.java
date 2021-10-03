package io.github.dunwu.module.mnt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 服务器配置表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("mnt_server")
@ApiModel(value = "Server", description = "服务器配置表")
public class Server implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "IP地址")
    @TableField("`ip`")
    private String ip;

    @ApiModelProperty(value = "端口")
    @TableField("`port`")
    private Integer port;

    @ApiModelProperty(value = "账号")
    @TableField("`account`")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("`password`")
    private String password;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    @ApiModelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @TableField("`update_by`")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String IP = "ip";
    public static final String PORT = "port";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
