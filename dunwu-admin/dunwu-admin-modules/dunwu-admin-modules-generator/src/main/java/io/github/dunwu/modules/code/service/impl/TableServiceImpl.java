package io.github.dunwu.modules.code.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.modules.code.entity.dto.TableInfoDto;
import io.github.dunwu.modules.code.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public Object getTables(String schemaName, String tableName, Integer page, Integer size) {
        // 计算起始偏移量
        int offset = page * size;
        Integer total = 0;
        List<TableInfoDto> list = null;
        if (StrUtil.isNotBlank(tableName)) {
            // 普通查询 SQL
            String countSql = "SELECT count(*)"
                + " FROM `information_schema`.`tables`"
                + " WHERE `table_schema` = ?"
                + " AND `table_name` LIKE ?";

            // 分页查询 SQL
            String pageSql = "SELECT `table_name`, `create_time`, `engine`, `table_collation`, `table_comment`"
                + " FROM `information_schema`.`tables`"
                + " WHERE `table_schema` = ?"
                + " AND `table_name` LIKE ?"
                + " ORDER BY `create_time` DESC"
                + " LIMIT ?, ?";

            // 分页查询符合要求的记录
            list = jdbcTemplate.query(pageSql,
                new BeanPropertyRowMapper<>(TableInfoDto.class), schemaName, tableName, offset, size);
            // 查询符合要求的记录数
            total = jdbcTemplate.queryForObject(countSql, Integer.class, schemaName, tableName);
        } else {

            // 普通查询 SQL
            String countSql = "SELECT count(*)"
                + " FROM `information_schema`.`tables`"
                + " WHERE `table_schema` = ?";

            // 分页查询 SQL
            String pageSql = "SELECT `table_name`, `create_time`, `engine`, `table_collation`, `table_comment`"
                + " FROM `information_schema`.`tables`"
                + " WHERE `table_schema` = ?"
                + " LIMIT ?, ?";

            // 分页查询符合要求的记录
            list = jdbcTemplate.query(pageSql, new Object[] { schemaName, offset, size },
                new BeanPropertyRowMapper<>(TableInfoDto.class));
            // 查询符合要求的记录数
            total = jdbcTemplate.queryForObject(countSql, new Object[] { schemaName }, Integer.class);
        }

        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(i -> i.setSchemaName(schemaName));
        }

        return PageUtil.toMap(list, total);
    }

}
