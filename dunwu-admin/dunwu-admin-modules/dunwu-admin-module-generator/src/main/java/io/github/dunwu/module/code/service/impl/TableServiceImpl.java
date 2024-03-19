package io.github.dunwu.module.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.dunwu.module.code.dao.CodeColumnConfigDao;
import io.github.dunwu.module.code.dao.CodeGlobalConfigDao;
import io.github.dunwu.module.code.dao.CodeTableConfigDao;
import io.github.dunwu.module.code.entity.CodeColumnConfig;
import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.CodeTableConfig;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;
import io.github.dunwu.module.code.entity.dto.TableInfoDto;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.module.code.service.CodeDatabaseService;
import io.github.dunwu.module.code.service.TableService;
import io.github.dunwu.module.security.util.SecurityUtil;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.data.entity.TableInfo;
import io.github.dunwu.tool.data.response.PageImpl;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.util.DatabaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final CodeGlobalConfigDao globalConfigDao;
    private final CodeTableConfigDao tableConfigDao;
    private final CodeColumnConfigDao columnConfigDao;

    @Override
    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public PageResult<TableInfoDto> getTables(Long dbId, String tableName, Integer page, Integer size) {

        // 查询数据库记录
        CodeDatabaseDto codeDatabaseDto = databaseService.pojoById(dbId);
        if (codeDatabaseDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("找不到 dbId = {} 的数据库记录", dbId));
        }

        // 获取 JDBC 链接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(codeDatabaseDto.getJdbcUrl(),
                codeDatabaseDto.getUsername(),
                codeDatabaseDto.getPassword());

            PageImpl<TableInfo> pageImpl =
                DatabaseUtil.getTables(connection, codeDatabaseDto.getSchemaName(), tableName, page, size);

            // 查找是否存在匹配的全局级配置
            String username = SecurityUtil.getCurrentUsername();
            LambdaQueryWrapper<CodeGlobalConfig> globalQueryWrapper = new LambdaQueryWrapper<>();
            globalQueryWrapper.eq(CodeGlobalConfig::getCreateBy, username);
            int globalCount = globalConfigDao.count(globalQueryWrapper);
            boolean isGlobalConfigured = globalCount > 0;

            long total = 0L;
            List<TableInfoDto> list = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(pageImpl.getContent())) {
                total = pageImpl.getTotal();
                list = pageImpl.getContent().stream().map(baseTableInfoDto -> {
                    TableInfoDto tableInfoDto = BeanUtil.toBean(baseTableInfoDto, TableInfoDto.class);
                    tableInfoDto.setIsGlobalConfigured(isGlobalConfigured);

                    LambdaQueryWrapper<CodeTableConfig> tableQueryWrapper = new LambdaQueryWrapper<>();
                    tableQueryWrapper.eq(CodeTableConfig::getDbId, dbId)
                                     .eq(CodeTableConfig::getSchemaName, tableInfoDto.getTableSchema())
                                     .eq(CodeTableConfig::getTableName, tableInfoDto.getTableName());
                    int tableCount = tableConfigDao.count(tableQueryWrapper);
                    tableInfoDto.setIsTableConfigured(tableCount > 0);

                    LambdaQueryWrapper<CodeColumnConfig> columnQueryWrapper = new LambdaQueryWrapper<>();
                    columnQueryWrapper.eq(CodeColumnConfig::getDbId, dbId)
                                      .eq(CodeColumnConfig::getSchemaName, tableInfoDto.getTableSchema())
                                      .eq(CodeColumnConfig::getTableName, tableInfoDto.getTableName());
                    int columnCount = columnConfigDao.count(columnQueryWrapper);
                    tableInfoDto.setIsColumnConfigured(columnCount > 0);
                    return tableInfoDto;
                }).collect(Collectors.toList());
            }
            return PageResult.ok(new PageImpl<>(list, page, size, total));
        } catch (SQLException e) {
            e.printStackTrace();
            return PageResult.build(ResultCode.DATA_ERROR);
        } finally {
            if (connection != null) {
                DbUtil.close(connection);
            }
        }
    }

    @Override
    public TableInfoDto getCodeConfigInfo(CodeTableConfigQuery query) {

        String username = getCurrentUsername();

        // 查找是否存在匹配的全局级配置
        LambdaQueryWrapper<CodeGlobalConfig> globalQueryWrapper = new LambdaQueryWrapper<>();
        globalQueryWrapper.eq(CodeGlobalConfig::getCreateBy, username);
        int globalCount = globalConfigDao.count(globalQueryWrapper);
        boolean isGlobalConfigured = globalCount > 0;

        // 查找是否存在匹配的表级配置
        LambdaQueryWrapper<CodeTableConfig> tableQueryWrapper = new LambdaQueryWrapper<>();
        tableQueryWrapper.eq(CodeTableConfig::getDbId, query.getDbId())
                         .eq(CodeTableConfig::getSchemaName, query.getSchemaName())
                         .eq(CodeTableConfig::getTableName, query.getTableName())
                         .eq(CodeTableConfig::getCreateBy, username);
        int tableCount = tableConfigDao.count(tableQueryWrapper);
        boolean isTableConfigured = tableCount > 0;

        // 查找是否存在匹配的字段级配置
        LambdaQueryWrapper<CodeColumnConfig> columnQueryWrapper = new LambdaQueryWrapper<>();
        columnQueryWrapper.eq(CodeColumnConfig::getDbId, query.getDbId())
                          .eq(CodeColumnConfig::getSchemaName, query.getSchemaName())
                          .eq(CodeColumnConfig::getTableName, query.getTableName())
                          .eq(CodeColumnConfig::getCreateBy, username);
        int columnCount = columnConfigDao.count(columnQueryWrapper);
        boolean isColumnConfigured = columnCount > 0;

        TableInfoDto tableInfoDto = TableInfoDto.builder()
                                                .dbId(query.getDbId())
                                                .isGlobalConfigured(isGlobalConfigured)
                                                .isTableConfigured(isTableConfigured)
                                                .isColumnConfigured(isColumnConfigured)
                                                .build();
        tableInfoDto.setTableSchema(query.getSchemaName());
        tableInfoDto.setTableName(query.getTableName());
        return tableInfoDto;
    }

    private String getCurrentUsername() {
        String username = SecurityUtil.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            return "admin";
        }
        return username;
    }

}
