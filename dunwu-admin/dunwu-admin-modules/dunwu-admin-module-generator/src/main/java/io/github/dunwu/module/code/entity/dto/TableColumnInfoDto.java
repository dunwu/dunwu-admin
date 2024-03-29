package io.github.dunwu.module.code.entity.dto;

import io.github.dunwu.module.code.entity.CodeColumnConfig;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
@Accessors(chain = true)
public class TableColumnInfoDto {

    /** 数据库ID */
    @NotNull
    private Long dbId;
    /** Schema名称 */
    @NotNull
    private String schemaName;
    /** 表名称 */
    @NotNull
    private String tableName;
    /** 创建者 */
    private String createBy;
    /** 表字段列表 */
    @NotEmpty
    private Collection<CodeColumnConfig> columns;

}
