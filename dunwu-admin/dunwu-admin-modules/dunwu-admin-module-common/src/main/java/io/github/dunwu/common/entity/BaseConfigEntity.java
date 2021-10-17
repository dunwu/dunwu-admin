package io.github.dunwu.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置型表基础实体
 *
 * @author peng.zhang
 * @date 2021-10-15
 */
@Data
@Accessors(chain = true)
public class BaseConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @TableField("`is_disabled`")
    protected Boolean disabled;

    @ApiModelProperty(value = "创建者ID")
    @TableField("`creator_id`")
    protected Long creatorId;

    @ApiModelProperty(value = "更新者ID")
    @TableField("`updater_id`")
    protected Long updaterId;

    @ApiModelProperty(value = "创建者名称")
    @TableField("`creator_name`")
    protected String creatorName;

    @ApiModelProperty(value = "更新者名称")
    @TableField("`updater_name`")
    protected String updaterName;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

    public static final String IS_DISABLED = "is_disabled";
    public static final String CREATOR_ID = "creator_id";
    public static final String UPDATER_ID = "updater_id";
    public static final String CREATOR_NAME = "creator_name";
    public static final String UPDATER_NAME = "updater_name";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
