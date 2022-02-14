package io.github.dunwu.module.storage.entity.query;

import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Blob;

/**
 * 文件内容表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "FileContentQuery", description = "文件内容表")
public class FileContentQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField
    private String id;

    @ApiModelProperty(value = "实际文件名")
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

    @ApiModelProperty(value = "文件存储服务类型")
    @QueryField
    private String storeType;

    @ApiModelProperty(value = "文件存储路径")
    @QueryField
    private String storeUrl;

    @ApiModelProperty(value = "文件内容")
    @QueryField
    private Blob content;

}
