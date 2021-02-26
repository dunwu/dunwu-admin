package io.github.dunwu.modules.generator.service;

import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;

import java.util.List;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
public interface TableService {

    /**
     * 获取所有table
     *
     * @return /
     */
    Object getTables();

    /**
     * 查询数据库元数据
     *
     * @param name     表名
     * @param startEnd 分页参数
     * @return /
     */
    Object getTables(String name, int[] startEnd);

    List<CodeColumnConfigDto> getColumns(String tableName);

}
