package io.github.dunwu.modules.log.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-07
 */
@Data
@Accessors(chain = true)
public abstract class BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态")
    protected Boolean enabled;

    @ApiModelProperty(value = "备注")
    protected String note;

    @ApiModelProperty(value = "创建者")
    protected String createBy;

    @ApiModelProperty(value = "更新者")
    protected String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

}
