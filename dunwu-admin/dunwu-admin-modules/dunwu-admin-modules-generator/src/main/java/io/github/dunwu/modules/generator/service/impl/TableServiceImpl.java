package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.generator.CodeGeneratorUtil;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.TableInfoDto;
import io.github.dunwu.modules.generator.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    @PersistenceContext
    private EntityManager em;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public Object getTables(String schemaName) {

        if (cn.hutool.core.util.StrUtil.isBlank(schemaName)) {
            log.error("schemaName 为空");
            return null;
        }

        // 使用预编译防止sql注入
        String sql = new StringBuilder("select table_name, create_time, engine, table_collation, table_comment")
            .append(" from information_schema.tables")
            .append(" where table_schema = ?")
            .append(" order by create_time desc")
            .toString();

        Query query = em.createNativeQuery(sql);
        query.setParameter(1, schemaName);
        return query.getResultList();
    }

    @Override
    public Object getTables(String schemaName, String tableName, int[] startEnd) {
        // 使用预编译防止sql注入
        String sql = new StringBuilder("select table_name, create_time, engine, table_collation, table_comment")
            .append(" from information_schema.tables")
            .append(" where table_schema = ?")
            .append(" and table_name like ? order by create_time desc")
            .toString();

        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);
        query.setParameter(1, schemaName);
        query.setParameter(2, StrUtil.isNotBlank(tableName) ? ("%" + tableName + "%") : "%%");
        List<?> result = query.getResultList();
        List<TableInfoDto> tableInfoDtos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            tableInfoDtos.add(
                new TableInfoDto(schemaName, arr[0], arr[1], arr[2], arr[3],
                    ObjectUtil.isNotEmpty(arr[4]) ? arr[4] : "-"));
        }
        Query query1 = em.createNativeQuery(
            "SELECT COUNT(*) FROM `information_schema`.`tables` WHERE `table_schema` = (SELECT database())");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toMap(tableInfoDtos, totalElements);
    }

    @Override
    public List<CodeColumnConfigDto> getColumns(String tableName) {

        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder();
        builder.getStrategyConfig().setInclude(tableName);
        List<TableInfo> tableInfos = builder.queryTableInfoList();

        return null;
    }

    // public ConfigBuilder initConfigBuilder() {
    //     String url =
    //         "jdbc:mysql://localhost:3306/eladmin?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
    //     DataSourceConfig dataSourceConfig = new DataSourceConfig(url, "com.mysql.cj.jdbc.Driver", "root", "root");
    //     PackageConfig packageConfig = new PackageConfig("io.github.dunwu.modules", "generator");
    //     GlobalConfig globalConfig = new GlobalConfig();
    //     globalConfig.setAuthor("dunwu").setOutputDir("E:\\Temp\\codes");
    //     StrategyConfig strategyConfig = new StrategyConfig();
    //     strategyConfig.setInclude("code_column_config");
    //     TemplateConfig templateConfig = new TemplateConfig();
    //
    //     ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
    //         templateConfig);
    //
    //     Collection<TableInfo> tableInfoList = builder.queryTableInfoList();
    //     for (TableInfo table : tableInfoList) {
    //         for (TableField field : table.getFields()) {
    //             if (field.getName().equals("rating")) {
    //                 field.setQueryType("Between");
    //                 field.setFormType("Date");
    //             } else {
    //                 field.setQueryType("Equals");
    //                 field.setFormType("Input");
    //             }
    //         }
    //     }
    //
    //     builder.setTableInfoList(tableInfoList);
    // }
}
