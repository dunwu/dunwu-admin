package io.github.dunwu.module.mnt.entity;

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
import javax.validation.constraints.NotNull;

/**
 * 应用和服务关联表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("mnt_deploy_server_map")
@ApiModel(value = "DeployServerMap", description = "应用和服务关联表")
public class DeployServerMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部署ID")
    @TableField("`deploy_id`")
    private Long deployId;

    @ApiModelProperty(value = "服务ID")
    @TableField("`server_id`")
    private Long serverId;

    public DeployServerMap() { }

    public DeployServerMap(Long deployId, Long serverId) {
        this.deployId = deployId;
        this.serverId = serverId;
    }

    public static final String ID = "id";
    public static final String DEPLOY_ID = "deploy_id";
    public static final String SERVER_ID = "server_id";

}
