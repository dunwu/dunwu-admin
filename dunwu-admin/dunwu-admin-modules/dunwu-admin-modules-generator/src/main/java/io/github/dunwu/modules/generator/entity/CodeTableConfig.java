package io.github.dunwu.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 代码生成-表级别配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfig", description = "代码生成-表级别配置")
public class CodeTableConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "数据库Schema")
    private String tableSchema;

    @ApiModelProperty(value = "数据库Table")
    private String tableName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "是否覆盖")
    private Boolean cover;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "至于哪个包下")
    private String pack;

    @ApiModelProperty(value = "前端代码生成的路径")
    private String path;

    @ApiModelProperty(value = "前端Api文件路径")
    private String apiPath;

    @ApiModelProperty(value = "表前缀")
    private String prefix;

    @ApiModelProperty(value = "接口名称")
    private String apiAlias;


}
