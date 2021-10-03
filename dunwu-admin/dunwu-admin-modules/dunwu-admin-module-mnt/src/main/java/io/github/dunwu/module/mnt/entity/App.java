package io.github.dunwu.module.mnt.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * 应用配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("mnt_app")
@ApiModel(value = "App", description = "应用配置")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应用名称")
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "上传路径")
    @TableField("`upload_path`")
    private String uploadPath;

    @ApiModelProperty(value = "部署路径")
    @TableField("`deploy_path`")
    private String deployPath;

    @ApiModelProperty(value = "备份路径")
    @TableField("`backup_path`")
    private String backupPath;

    @ApiModelProperty(value = "应用端口")
    @TableField("`port`")
    private Integer port;

    @ApiModelProperty(value = "启动脚本")
    @TableField("`start_script`")
    private String startScript;

    @ApiModelProperty(value = "部署脚本")
    @TableField("`deploy_script`")
    private String deployScript;

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
    public static final String UPLOAD_PATH = "upload_path";
    public static final String DEPLOY_PATH = "deploy_path";
    public static final String BACKUP_PATH = "backup_path";
    public static final String PORT = "port";
    public static final String START_SCRIPT = "start_script";
    public static final String DEPLOY_SCRIPT = "deploy_script";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
