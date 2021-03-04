package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.generator.CodeGenerator;
import io.github.dunwu.generator.CodeGeneratorUtil;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.config.po.TableField;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.generator.config.rules.JavaColumnType;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.dao.CodeColumnConfigDao;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.dto.ColumnInfoDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
 * 代码生成-字段级别配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
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
    public boolean saveOrUpdateBatch(Collection<CodeColumnConfig> list) {
        if (CollectionUtil.isEmpty(list)) {
            return true;
        }

        CodeColumnConfig first = CollectionUtil.getFirst(list.iterator());
        CodeColumnConfig query = new CodeColumnConfig();
        query.setSchemaName(first.getSchemaName())
             .setTableName(first.getTableName());
        List<CodeColumnConfig> oldRecords = dao.listByQuery(query);
        Set<Long> ids = oldRecords.stream().map(CodeColumnConfig::getId).collect(Collectors.toSet());
        if (CollectionUtil.isNotEmpty(ids)) {
            dao.removeByIds(ids);
            return dao.saveBatch(list);
        }
        return dao.saveBatch(list);
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
    public Page<CodeColumnConfigDto> pojoPageByQuery(CodeColumnConfigQuery query, Pageable pageable) {
        Page<CodeColumnConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        if (page == null || CollectionUtil.isEmpty(page.getContent())) {
            List<TableInfo> tableInfos = queryTableInfo(query.getSchemaName(), query.getTableName());
            TableInfo tableInfo = tableInfos.get(0);
            CodeTableConfigDto codeTableConfigDto = BeanUtil.toBean(tableInfo, CodeTableConfigDto.class);
            List<CodeColumnConfigDto> columns = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(tableInfo.getFields())) {
                List<CodeColumnConfigDto> fields = tableInfo.getFields().stream().map(i -> {
                    return BeanUtil.toBean(i, CodeColumnConfigDto.class);
                }).collect(Collectors.toList());
                columns.addAll(fields);
            }
            page = new PageImpl<>(columns, pageable, columns.size());
        }
        return page;
    }

    @Override
    public List<CodeColumnConfigDto> pojoListByQuery(CodeColumnConfigQuery query) {
        List<CodeColumnConfigDto> dtos = dao.pojoListByQuery(query, this::doToDto);
        if (CollectionUtil.isEmpty(dtos)) {
            List<TableInfo> tableInfos = queryTableInfo(query.getSchemaName(), query.getTableName());
            TableInfo tableInfo = tableInfos.get(0);
            List<CodeColumnConfigDto> columns = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(tableInfo.getFields())) {
                List<CodeColumnConfigDto> fields = tableInfo.getFields().stream()
                                                            .map(i -> BeanUtil.toBean(i, CodeColumnConfigDto.class))
                                                            .collect(Collectors.toList());
                columns.addAll(fields);
            }
            dtos.addAll(columns);
        }
        return dtos;
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
        String schemaName;
        if (StrUtil.isNotBlank(query.getSchemaName())) {
            schemaName = query.getSchemaName();
        } else {
            schemaName = tableService.getCurrentSchema();
        }

        if (CollectionUtil.isNotEmpty(query.getTables())) {
            for (String tableName : query.getTables()) {
                list.addAll(syncOneTable(schemaName, tableName, query));
            }
        } else {
            list.addAll(syncOneTable(schemaName, query.getTableName(), query));
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
                if (e.getFieldName().equalsIgnoreCase(c.getColumnName())) {
                    // toEntity(e, c);
                    entities.add(e);
                    entity = e;
                }
            }

            if (entity != null) {
                continue;
            }

            entity = new CodeColumnConfig();
            // toEntity(entity, c);
            entities.add(entity);
        }

        dao.remove(Wrappers.query(new CodeColumnConfig().setTableName(tableName)));
        dao.saveOrUpdateBatch(entities);
        return entities.stream()
                       .map(this::doToDto)
                       .collect(Collectors.toList());
    }

    @Override
    public void generate(CodeTableConfigDto tableConfigDto, List<CodeColumnConfigDto> columnConfigs,
        HttpServletRequest request, HttpServletResponse response) {
        // String tmpPath = System.getProperty("java.io.tmpdir") + File.separator + "dunwu";
        // Properties properties = getConfigs(tmpPath, tableConfigDto, columnConfigs);
        ConfigBuilder builder = transToConfigBuilder(tableConfigDto);

        String codePath = builder.getGlobalConfig().getOutputDir() + File.separator + "codes";
        String zipFilePath = builder.getGlobalConfig().getOutputDir() + File.separator + "codes.zip";

        FileUtil.mkdir(codePath);
        ZipUtil.zip(codePath, zipFilePath);
        log.info("代码已生成到：{}", zipFilePath);
        ServletUtil.downloadFile(request, response, new File(zipFilePath), true);
    }

    @Override
    public List<CodeGenerateContentDto> getPreviewList(CodeTableConfigDto tableConfig,
        List<CodeColumnConfigDto> columnConfigs) {
        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder();
        CodeGenerator generator = new CodeGenerator(builder);
        return generator.preview();
    }

    public ConfigBuilder transToConfigBuilder(CodeTableConfigDto tableConfig) {
        TableInfo tableInfo = transToTableInfo(tableConfig);
        ConfigBuilder builder = CodeGeneratorUtil.initConfigBuilder();
        builder.getGlobalConfig()
               .setAuthor(tableConfig.getAuthor())
               .setBackendDir(tableConfig.getBackendPath())
               .setFrontendDir(tableConfig.getFrontendPath())
               .setEnableOverride(tableConfig.getEnableOverride());
        builder.setTableInfoList(Collections.singletonList(tableInfo));
        return builder;
    }

    public TableInfo transToTableInfo(CodeTableConfigDto tableConfigDto) {
        List<TableField> fields = new ArrayList<>();
        for (CodeColumnConfigDto column : tableConfigDto.getColumns()) {
            TableField field = transToTableField(column);
            fields.add(field);
        }
        TableInfo tableInfo = BeanUtil.toBean(tableConfigDto, TableInfo.class);
        tableInfo.setFields(fields);
        return tableInfo;
    }

    public TableField transToTableField(CodeColumnConfigDto columnConfigDto) {
        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
        TableField tableField = BeanUtil.toBean(columnConfigDto, TableField.class, copyOptions);
        tableField.setJavaType(JavaColumnType.getJavaColumnTypeByType(columnConfigDto.getJavaType()));
        return tableField;
    }

    public List<TableInfo> queryTableInfo(String schemaName, String tableName) {

        if (StrUtil.isBlank(schemaName)) {
            schemaName = tableService.getCurrentSchema();
        }

        String url = StrUtil.format(
            "jdbc:mysql://localhost:3306/{}?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8",
            schemaName);
        DataSourceConfig dataSourceConfig = new DataSourceConfig(url, "com.mysql.cj.jdbc.Driver", "root", "root",
            schemaName);
        PackageConfig packageConfig = new PackageConfig("io.github.dunwu.modules", "generator");
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("dunwu").setOutputDir("E:\\Temp\\codes");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude(tableName);
        TemplateConfig templateConfig = new TemplateConfig();

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);

        List<TableInfo> tableInfoList = builder.queryTableInfoList();
        // for (TableInfo table : tableInfoList) {
        //     for (TableField field : table.getFields()) {
        //         if (field.getName().equals("rating")) {
        //             field.setQueryType("Between");
        //             field.setFormType("Date");
        //         } else {
        //             field.setQueryType("Equals");
        //             field.setFormType("Input");
        //         }
        //     }
        // }
        //
        // builder.setTableInfoList(tableInfoList);
        // CodeGenerator codeGenerator = new CodeGenerator(builder);
        // codeGenerator.generate();
        // AnsiColorUtil.YELLOW.println(JSONUtil.toJsonStr(builder.getTableInfoList()));
        return tableInfoList;
    }

}
