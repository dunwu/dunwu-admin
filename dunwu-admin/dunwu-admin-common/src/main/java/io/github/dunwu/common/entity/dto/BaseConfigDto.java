package io.github.dunwu.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
public class BaseConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    protected Boolean disabled;

    @ApiModelProperty(value = "创建者ID")
    protected Long creatorId;

    @ApiModelProperty(value = "更新者ID")
    protected Long updaterId;

    @ApiModelProperty(value = "创建者名称")
    protected String creatorName;

    @ApiModelProperty(value = "更新者名称")
    protected String updaterName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

}
