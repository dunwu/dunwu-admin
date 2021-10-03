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
 * 部署历史管理
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("mnt_deploy_history")
@ApiModel(value = "DeployHistory", description = "部署历史管理")
public class DeployHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部署编号")
    @TableField("`deploy_id`")
    private Long deployId;

    @ApiModelProperty(value = "应用名称")
    @TableField("`app_name`")
    private String appName;

    @ApiModelProperty(value = "部署日期")
    @TableField("`deploy_date`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime deployDate;

    @ApiModelProperty(value = "部署用户")
    @TableField("`deploy_user`")
    private String deployUser;

    @ApiModelProperty(value = "服务器IP")
    @TableField("`ip`")
    private String ip;

    public static final String ID = "id";
    public static final String DEPLOY_ID = "deploy_id";
    public static final String APP_NAME = "app_name";
    public static final String DEPLOY_DATE = "deploy_date";
    public static final String DEPLOY_USER = "deploy_user";
    public static final String IP = "ip";

}
