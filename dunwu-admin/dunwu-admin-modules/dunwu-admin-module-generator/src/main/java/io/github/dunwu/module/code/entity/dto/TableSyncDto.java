package io.github.dunwu.module.code.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Data
public class TableSyncDto {

    private Long dbId;

    private String schemaName;

    private String createBy;

    /** 表名称 */
    private List<String> tables;

}
