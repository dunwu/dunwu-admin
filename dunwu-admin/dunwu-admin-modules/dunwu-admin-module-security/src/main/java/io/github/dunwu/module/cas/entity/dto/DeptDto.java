package io.github.dunwu.module.cas.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 部门 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-05
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DeptDto", description = "部门")
public class DeptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "上级部门ID")
    private Long pid;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "部门等级")
    private Integer level;

    @ApiModelProperty(value = "部门顺序")
    private Integer sequence;

    @ApiModelProperty(value = "子部门数量")
    private Integer childrenNum;

    @ApiModelProperty(value = "状态")
    private Boolean disabled;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否有子部门")
    private boolean hasChildren;

    @ApiModelProperty(value = "子部门")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<DeptDto> children;

}
