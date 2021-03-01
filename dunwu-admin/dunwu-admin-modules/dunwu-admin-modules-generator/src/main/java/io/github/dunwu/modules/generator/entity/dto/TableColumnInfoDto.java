package io.github.dunwu.modules.generator.entity.dto;

import io.github.dunwu.generator.config.po.CodeColumnConfigDto;
import lombok.Data;

import java.util.Collection;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Data
public class TableColumnInfoDto {

    /** Schema名称 */
    private String tableSchema;
    /** 表名称 */
    private String tableName;
    /** 表字段列表 */
    private Collection<CodeColumnConfigDto> columns;

}
