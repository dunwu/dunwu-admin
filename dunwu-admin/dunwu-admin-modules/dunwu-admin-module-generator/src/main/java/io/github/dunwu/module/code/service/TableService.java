package io.github.dunwu.module.code.service;

import io.github.dunwu.module.code.entity.dto.TableInfoDto;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.tool.data.response.PageResult;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
public interface TableService {

    String getCurrentSchema();

    /**
     * 查询数据库元数据
     *
     * @param dbId      数据库ID
     * @param tableName table 名称
     * @param page      分页页号
     * @param size      每页记录数
     * @return /
     */
    PageResult<TableInfoDto> getTables(Long dbId, String tableName, Integer page, Integer size);

    /**
     * 查询代码生成配置信息
     *
     * @param query {@link CodeTableConfigQuery} 请求参数
     * @return /
     */
    TableInfoDto getCodeConfigInfo(CodeTableConfigQuery query);

}
