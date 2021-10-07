package io.github.dunwu.module.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.module.storage.constant.StorageTypeEnum;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 文件信息表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tool_file_info")
@ApiModel(value = "FileInfo", description = "文件信息表")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件名")
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

    @ApiModelProperty(value = "文件大小")
    @TableField("`size`")
    private Long size;

    @ApiModelProperty(value = "文件扩展名")
    @TableField("`extension`")
    private String extension;

    @ApiModelProperty(value = "文件实际类型")
    @TableField("`content_type`")
    private String contentType;

    @ApiModelProperty(value = "文件存储服务类型")
    @TableField("`store_type`")
    private StorageTypeEnum storeType;

    @ApiModelProperty(value = "文件存储路径")
    @TableField("`store_url`")
    private String storeUrl;

    @ApiModelProperty(value = "文件访问路径")
    @TableField("`access_url`")
    private String accessUrl;

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
    public static final String FILE_NAME = "file_name";
    public static final String NAMESPACE = "namespace";
    public static final String TAG = "tag";
    public static final String ORIGIN_NAME = "origin_name";
    public static final String SIZE = "size";
    public static final String EXTENSION = "extension";
    public static final String CONTENT_TYPE = "content_type";
    public static final String STORE_TYPE = "store_type";
    public static final String STORE_URL = "store_url";
    public static final String ACCESS_URL = "access_url";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
