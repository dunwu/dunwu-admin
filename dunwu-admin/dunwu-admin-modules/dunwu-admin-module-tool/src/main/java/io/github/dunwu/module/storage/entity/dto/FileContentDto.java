package io.github.dunwu.module.storage.entity.dto;

import io.github.dunwu.module.storage.constant.StorageTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文件内容表 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FileContentDto", description = "文件内容表")
public class FileContentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "实际文件名")
    private String fileName;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    private String tag;

    @ApiModelProperty(value = "源文件名")
    private String originName;

    @ApiModelProperty(value = "文件存储服务类型")
    private StorageTypeEnum storeType;

    @ApiModelProperty(value = "文件存储路径")
    private String storeUrl;

    @ApiModelProperty(value = "文件内容")
    private byte[] content;

}
