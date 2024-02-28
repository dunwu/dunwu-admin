package io.github.dunwu.module.sys.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableConfigDto implements Serializable {

    /** Schema 名称 */
    @NotBlank
    private String schemaName;

    /** 表名称 */
    @NotBlank
    private String tableName;

    /** 列配置 */
    private List<TableColumnConfigDto> columns;

}
