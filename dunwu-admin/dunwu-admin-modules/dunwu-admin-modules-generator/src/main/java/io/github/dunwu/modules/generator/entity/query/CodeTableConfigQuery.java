package io.github.dunwu.modules.generator.entity.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.github.dunwu.data.core.annotation.QueryField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 代码生成-表级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfigQuery", description = "代码生成-表级别配置")
public class CodeTableConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库Schema")
    @QueryField
    private String tableSchema;

    @ApiModelProperty(value = "数据库Table")
    @QueryField
    private String tableName;

    @ApiModelProperty(value = "作者")
    @QueryField
    private String author;

    @ApiModelProperty(value = "是否覆盖")
    @QueryField
    private Boolean cover;

    @ApiModelProperty(value = "模块名称")
    @QueryField
    private String moduleName;

    @ApiModelProperty(value = "至于哪个包下")
    @QueryField
    private String pack;

    @ApiModelProperty(value = "前端代码生成的路径")
    @QueryField
    private String path;

    @ApiModelProperty(value = "前端Api文件路径")
    @QueryField
    private String apiPath;

    @ApiModelProperty(value = "表前缀")
    @QueryField
    private String prefix;

    @ApiModelProperty(value = "接口名称")
    @QueryField
    private String apiAlias;



}
