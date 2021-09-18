package io.github.dunwu.module.code.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import io.github.dunwu.tool.data.util.PageUtil;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;
import io.github.dunwu.module.code.entity.dto.TableInfoDto;
import io.github.dunwu.module.code.service.CodeDatabaseService;
import io.github.dunwu.module.code.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final JdbcTemplate jdbcTemplate;
    private final CodeDatabaseService databaseService;

    @Override
    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public Map<String, Object> getTables(Long dbId, String tableName, Integer page, Integer size) {

        // 查询数据库记录
        CodeDatabaseDto codeDatabaseDto = databaseService.pojoById(dbId);
        if (codeDatabaseDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, StrUtil.format("找不到 dbId = {} 的数据库记录", dbId));
        }

        // 计算分页偏移量
        int offset = (page - 1) * size;

        int total = 0;
        List<TableInfoDto> list = new ArrayList<>();

        // 获取 JDBC 链接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(codeDatabaseDto.getJdbcUrl(),
                codeDatabaseDto.getUsername(),
                codeDatabaseDto.getPassword());

            DSLContext dslContext = DSL.using(connection);

            // 查询条件
            List<Condition> conditions = new ArrayList<>();
            conditions.add(DSL.field("TABLE_SCHEMA").eq(codeDatabaseDto.getSchemaName()));
            if (StrUtil.isNotBlank(tableName)) {
                conditions.add(DSL.field("TABLE_NAME").likeIgnoreCase(StrUtil.format("%{}%", tableName)));
            }

            // 查询所有匹配记录数
            total = dslContext.selectCount()
                              .from("information_schema.tables")
                              .where(conditions)
                              .fetchOne(0, int.class);

            // 分页查询匹配记录
            list.addAll(dslContext.select()
                                  .from("information_schema.tables")
                                  .where(conditions)
                                  .limit(offset, size.intValue())
                                  .fetchInto(TableInfoDto.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                DbUtil.close(connection);
            }
        }

        // 组装分页展示数据
        return PageUtil.toMap(list, total);
    }

}
