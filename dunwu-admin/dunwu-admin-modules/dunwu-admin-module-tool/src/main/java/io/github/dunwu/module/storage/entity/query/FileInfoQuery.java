package io.github.dunwu.module.storage.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.module.storage.constant.StorageTypeEnum;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件信息表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FileInfoQuery", description = "文件信息表")
public class FileInfoQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "文件名")
    @QueryField
    private String fileName;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    @QueryField
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    @QueryField
    private String tag;

    @ApiModelProperty(value = "源文件名")
    @QueryField
    private String originName;

    @ApiModelProperty(value = "文件扩展名")
    @QueryField
    private String extension;

    @ApiModelProperty(value = "文件实际类型")
    @QueryField
    private String contentType;

    @ApiModelProperty(value = "文件存储服务类型")
    @QueryField
    private StorageTypeEnum storeType;

    @ApiModelProperty(value = "文件存储路径")
    @QueryField
    private String storeUrl;

    @ApiModelProperty(value = "文件访问路径")
    @QueryField
    private String accessUrl;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime updateTime;

}
