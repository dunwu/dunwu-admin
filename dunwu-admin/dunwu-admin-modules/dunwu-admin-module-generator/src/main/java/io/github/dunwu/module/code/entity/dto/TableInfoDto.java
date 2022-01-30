package io.github.dunwu.module.code.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 表的数据信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfoDto {

    /** 数据库ID */
    private Long dbId;

    /** Schema 名称 */
    @JsonProperty("schemaName")
    private String tableSchema;

    /** 表名称 */
    private String tableName;

    /** 数据库引擎 */
    private String engine;

    /** 编码集 */
    @JsonProperty("coding")
    private String tableCollation;

    /** 注释 */
    @JsonProperty("comment")
    private String tableComment;

    /** 全局配置是否已配置 */
    private Boolean isGlobalConfigured;

    /** 表级配置是否已配置 */
    private Boolean isTableConfigured;

    /** 字段级配置是否已配置 */
    private Boolean isColumnConfigured;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
