package io.github.dunwu.module.storage.entity;

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
 * 文件内容表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_file_content")
@ApiModel(value = "FileContent", description = "文件内容表")
public class FileContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "实际文件名")
    @TableField("`file_name`")
    private String fileName;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    @TableField("`namespace`")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    @TableField("`tag`")
    private String tag;

    @ApiModelProperty(value = "源文件名")
    @TableField("`origin_name`")
    private String originName;

    @ApiModelProperty(value = "文件存储服务类型")
    @TableField("`store_type`")
    private String storeType;

    @ApiModelProperty(value = "文件存储路径")
    @TableField("`store_url`")
    private String storeUrl;

    @ApiModelProperty(value = "文件内容")
    @TableField("`content`")
    private byte[] content;

    public static final String ID = "id";
    public static final String FILE_NAME = "file_name";
    public static final String NAMESPACE = "namespace";
    public static final String TAG = "tag";
    public static final String ORIGIN_NAME = "origin_name";
    public static final String STORE_TYPE = "store_type";
    public static final String STORE_URL = "store_url";
    public static final String CONTENT = "content";

}
