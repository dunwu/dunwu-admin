package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.generator.CodeGeneratorUtil;
import io.github.dunwu.generator.MybatisPlusGenProps;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.engine.TemplateContent;
import io.github.dunwu.modules.generator.dao.CodeColumnConfigDao;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.dto.ColumnInfoDto;
import io.github.dunwu.modules.generator.entity.dto.TableColumnInfoDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Slf4j
@Service
public class CodeColumnConfigServiceImpl extends ServiceImpl implements CodeColumnConfigService {

    private final CodeColumnConfigDao dao;
    private final TableService tableService;
    private final JdbcTemplate jdbcTemplate;

    public CodeColumnConfigServiceImpl(CodeColumnConfigDao dao,
        TableService tableService, JdbcTemplate jdbcTemplate) {
        this.dao = dao;
        this.tableService = tableService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean save(CodeColumnConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeColumnConfig> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(CodeColumnConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeColumnConfig> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean removeById(Serializable id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public Page<CodeColumnConfigDto> pojoPageByQuery(Object query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<CodeColumnConfigDto> pojoListByQuery(Object query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoByQuery(Object query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeColumnConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<CodeColumnConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeColumnConfigDto doToDto(CodeColumnConfig model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeColumnConfigDto.class);
    }

    @Override
    public CodeColumnConfig dtoToDo(CodeColumnConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeColumnConfig.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CodeColumnConfigDto> syncTables(CodeColumnConfigQuery query) {

        List<CodeColumnConfigDto> list = new ArrayList<>();

        // 如果没有指定 schema，默认为当前数据源连接的 schema
        String tableSchema;
        if (StrUtil.isNotBlank(query.getTableSchema())) {
            tableSchema = query.getTableSchema();
        } else {
            tableSchema = getCurrentSchema();
        }

        if (CollectionUtil.isNotEmpty(query.getTables())) {
            for (String tableName : query.getTables()) {
                list.addAll(syncOneTable(tableSchema, tableName, query));
            }
        } else {
            list.addAll(syncOneTable(tableSchema, query.getTableName(), query));
        }

        return list;
    }

    private List<CodeColumnConfigDto> syncOneTable(String tableSchema, String tableName, CodeColumnConfigQuery query) {
        if (StrUtil.isBlank(tableName)) {
            throw new IllegalArgumentException("table_name must not be null");
        }

        StringBuilder sql = new StringBuilder(
            "SELECT table_schema, table_name, column_name, is_nullable, data_type, column_key, column_default, column_comment, extra");
        sql.append(" FROM information_schema.columns")
           .append(" WHERE table_schema = ?")
           .append(" AND table_name = ?")
           .append(" ORDER BY ordinal_position").append(";");

        Object[] params = new Object[] { tableSchema, tableName };
        List<ColumnInfoDto> columns = jdbcTemplate.query(sql.toString(), params,
            new BeanPropertyRowMapper<>(ColumnInfoDto.class));
        if (CollectionUtil.isEmpty(columns)) {
            dao.remove(Wrappers.query(new CodeColumnConfig().setTableName(query.getTableName())));
            return new ArrayList<>();
        }

        List<CodeColumnConfig> entities = new ArrayList<>();
        List<CodeColumnConfig> codeColumnConfigs = dao.listByQuery(query);
        for (ColumnInfoDto c : columns) {
            CodeColumnConfig entity = null;
            for (CodeColumnConfig e : codeColumnConfigs) {
                if (e.getColumnName().equalsIgnoreCase(c.getColumnName())) {
                    toEntity(e, c);
                    entities.add(e);
                    entity = e;
                }
            }

            if (entity != null) {
                continue;
            }

            entity = new CodeColumnConfig();
            toEntity(entity, c);
            entities.add(entity);
        }

        dao.remove(Wrappers.query(new CodeColumnConfig().setTableName(tableName)));
        dao.saveOrUpdateBatch(entities);
        return entities.stream()
                       .map(this::doToDto)
                       .collect(Collectors.toList());
    }

    @Override
    public void generate(CodeTableConfigDto tableConfig, List<CodeColumnConfigDto> columnConfigs,
        HttpServletRequest request, HttpServletResponse response) {
        String tmpPath = System.getProperty("java.io.tmpdir") + File.separator + "dunwu";
        Properties properties = getConfigs(tmpPath, tableConfig, columnConfigs);
        new CodeGeneratorUtil(properties).generate();
        String codePath = tmpPath + File.separator + "codes";
        String zipFilePath = tmpPath + File.separator + "codes.zip";
        FileUtil.mkdir(codePath);
        ZipUtil.zip(codePath, zipFilePath);
        log.info("代码已生成到：{}", zipFilePath);
        ServletUtil.downloadFile(request, response, new File(zipFilePath), true);
    }

    @Override
    public List<TemplateContent> getPreviewList(CodeTableConfigDto tableConfig,
        List<CodeColumnConfigDto> columnConfigs) {
        String tmpPath = System.getProperty("java.io.tmpdir") + "/dunwu";
        Properties properties = getConfigs(tmpPath, tableConfig, columnConfigs);
        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder(properties);
        return new CodeGeneratorUtil(properties).getPreviewList();
    }

    private String getCurrentSchema() {
        return jdbcTemplate.queryForObject("SELECT database()", String.class);
    }

    @Override
    public Properties getConfigs(String outputDir, CodeTableConfigDto tableConfig,
        List<CodeColumnConfigDto> columnConfigs) {
        Properties properties = new Properties();

        // 全局性配置
        properties.put(MybatisPlusGenProps.OUTPUT_DIR.getKey(), outputDir);
        properties.put(MybatisPlusGenProps.GC_AUTHOR_NAME.getKey(), tableConfig.getAuthor());
        properties.put(MybatisPlusGenProps.GC_ENABLE_SWAGGER.getKey(), "true");
        properties.put("mybatis-plus.configuration.default-enum-type-handler",
            "org.apache.ibatis.type.EnumOrdinalTypeHandler");

        // 数据源相关配置
        properties.put(MybatisPlusGenProps.SPRING_DATASOURCE_URL.getKey(),
            "jdbc:mysql://localhost:3306/dunwu_admin?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        properties.put(MybatisPlusGenProps.SPRING_DATASOURCE_DRIVER.getKey(), "com.mysql.cj.jdbc.Driver");
        properties.put(MybatisPlusGenProps.SPRING_DATASOURCE_USERNAME.getKey(), "root");
        properties.put(MybatisPlusGenProps.SPRING_DATASOURCE_PASSWORD.getKey(), "root");

        // 包、模块配置
        properties.put(MybatisPlusGenProps.PC_PACKAGE_NAME.getKey(), tableConfig.getPack());
        properties.put(MybatisPlusGenProps.PC_MODULE_NAME.getKey(), tableConfig.getModuleName());

        properties.put(MybatisPlusGenProps.SC_TABLE_NAME.getKey(), tableConfig.getTableName());
        return properties;
    }

    public void toEntity(CodeColumnConfig entity, ColumnInfoDto item) {
        entity.setTableSchema(item.getTableSchema())
              .setTableName(item.getTableName())
              .setColumnName(item.getColumnName())
              .setColumnType(item.getDataType())
              .setColumnKey(item.getColumnKey())
              .setColumnComment(item.getColumnComment())
              .setNotNull(item.getIsNullable().equalsIgnoreCase("NO"))
              .setExtra(item.getExtra());
    }

    @Override
    public void addOrSaveColumns(TableColumnInfoDto entity) {
        CodeColumnConfigQuery query = new CodeColumnConfigQuery();
        query.setTableSchema(entity.getTableSchema());
        query.setTableName(entity.getTableName());
        List<CodeColumnConfigDto> oldColumns = pojoListByQuery(query);
        Set<Serializable> ids = oldColumns.stream()
                                          .map(i -> (Serializable) i.getId())
                                          .collect(Collectors.toSet());
        removeByIds(ids);
        saveBatch(entity.getColumns());
    }

}
