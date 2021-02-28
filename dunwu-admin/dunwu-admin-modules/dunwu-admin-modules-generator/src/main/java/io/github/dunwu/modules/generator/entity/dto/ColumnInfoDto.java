package io.github.dunwu.modules.generator.entity.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 数据表列信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-28
 */
@Data
@ToString
public class ColumnInfoDto {

    /** Schema名称 */
    private String tableSchema;
    /** 表名称 */
    private String tableName;
    /** 列名称 */
    private String columnName;
    /** 是否允许为空 */
    private String isNullable;
    /** 数据类型 */
    private String dataType;
    /** 列的Key类型 */
    private String columnKey;
    /** 列默认值 */
    private String columnDefault;
    /** 列备注 */
    private String columnComment;
    /** 扩展信息 */
    private String extra;

}
