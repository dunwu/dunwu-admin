package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.dunwu.data.core.DataException;
import io.github.dunwu.data.core.GlobalException;
import io.github.dunwu.generator.CodeGenerator;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.config.po.TableField;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.generator.config.rules.JavaColumnType;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.*;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.*;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.modules.mnt.service.MntDatabaseService;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成服务接口实现类
 *
 * @author peng.zhang
 * @date 2021/3/3
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final CodeGlobalConfigService globalConfigService;
    private final CodeTableConfigService tableConfigService;
    private final CodeColumnConfigService columnConfigService;
    private final MntDatabaseService databaseService;
    private final TableService tableService;

    public GeneratorServiceImpl(CodeGlobalConfigService globalConfigService,
        CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService,
        MntDatabaseService databaseService, TableService tableService) {
        this.globalConfigService = globalConfigService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
        this.databaseService = databaseService;
        this.tableService = tableService;
    }

    @Override
    public void syncTables(TableSyncDto tableSyncDto) {
        for (String tableName : tableSyncDto.getTables()) {
            syncOneTable(tableSyncDto.getDbId(), tableSyncDto.getSchemaName(), tableName, tableSyncDto.getCreateBy());
        }
    }

    private void syncOneTable(Long dbId, String schemaName, String tableName, String createBy) {

        CodeColumnConfigQuery columnQuery = new CodeColumnConfigQuery();
        columnQuery.setSchemaName(schemaName)
                   .setTableName(tableName)
                   .setCreateBy(createBy);
        List<CodeColumnConfigDto> codeColumnConfigDtos = columnConfigService.pojoListByQuery(columnQuery);
        if (CollectionUtil.isEmpty(codeColumnConfigDtos)) {
            return;
        }
        List<CodeColumnConfig> oldColumns = codeColumnConfigDtos.stream()
                                                                .map(columnConfigService::dtoToDo)
                                                                .collect(Collectors.toList());
        Map<String, CodeColumnConfig> columnMap = oldColumns.stream()
                                                            .collect(Collectors.toMap(CodeColumnConfig::getFieldName,
                                                                Function.identity()));

        TableInfo tableInfo = queryTableInfo(dbId, schemaName, tableName, columnQuery.getCreateBy());
        if (tableInfo == null) {
            throw new GlobalException(StrUtil.format("未找到 {} 表信息", tableName));
        }
        List<CodeColumnConfig> newColumns = tableInfo.getFields().stream()
                                                     .map(i -> transToCodeColumnConfig(i, schemaName, tableName))
                                                     .collect(Collectors.toList());

        List<CodeColumnConfig> mergeColumns = new ArrayList<>();
        for (CodeColumnConfig n : newColumns) {
            CodeColumnConfig entity = columnMap.getOrDefault(n.getFieldName(), n);
            mergeColumns.add(entity);
        }

        Set<Serializable> ids = oldColumns.stream().map(CodeColumnConfig::getId).collect(Collectors.toSet());
        columnConfigService.deleteBatchByIds(ids);
        columnConfigService.insertBatch(mergeColumns);
    }

    @Override
    public CodeGlobalConfigDto queryOrCreateGlobalConfig(CodeGlobalConfigQuery query) {
        CodeGlobalConfigDto dto = globalConfigService.pojoByQuery(query);
        if (dto != null) {
            return dto;
        }

        GlobalConfig globalConfig = new GlobalConfig();
        return BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
    }

    @Override
    public boolean saveGlobalConfig(CodeGlobalConfig entity) {
        if (entity.getId() == null) {
            return globalConfigService.insert(entity);
        } else {
            return globalConfigService.updateById(entity);
        }
    }

    @Override
    public CodeTableConfigDto queryTableConfig(CodeTableConfigQuery query) {
        return tableConfigService.pojoByQuery(query);
    }

    @Override
    public boolean saveTableConfig(CodeTableConfig entity) {
        return tableConfigService.save(entity);
    }

    @Override
    public List<CodeColumnConfigDto> queryColumnConfigs(CodeColumnConfigQuery query) {

        // 如果已经存在指定的数据表的列属性配置，则直接返回
        List<CodeColumnConfigDto> columnConfigRecords = columnConfigService.pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(columnConfigRecords)) {
            return columnConfigRecords;
        }

        CodeTableConfigQuery tableQuery = new CodeTableConfigQuery();
        tableQuery.setSchemaName(query.getSchemaName())
                  .setTableName(query.getTableName())
                  .setCreateBy(query.getCreateBy());
        CodeTableConfigDto codeTableConfigDto = queryTableConfig(tableQuery);
        if (codeTableConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("请先配置 schema = {}, table = {} 的表配置", query.getSchemaName(), query.getTableName()));
        }

        // 如果不存在指定的数据表的列属性配置，则查询指定表的属性，并组装默认的配置后返回结果
        TableInfo tableInfo = transToTableInfo(codeTableConfigDto);
        if (tableInfo == null || CollectionUtil.isEmpty(tableInfo.getFields())) {
            throw new GlobalException(StrUtil.format("查询 schema = {}, table = {} 的列数据信息失败",
                query.getSchemaName(), query.getTableName()));
        }

        return tableInfo.getFields().stream()
                        .filter(Objects::nonNull)
                        .map(i -> transToCodeColumnConfigDto(i, query.getSchemaName(), query.getTableName()))
                        .collect(Collectors.toList());
    }

    @Override
    public boolean saveColumnsConfigByCurrentUser(TableColumnInfoDto entity) {
        Collection<CodeColumnConfig> list = entity.getColumns();
        if (CollectionUtil.isEmpty(list)) {
            return true;
        }

        String username = SecurityUtils.getCurrentUsername();
        CodeColumnConfigQuery query = new CodeColumnConfigQuery();
        query.setSchemaName(entity.getSchemaName())
             .setTableName(entity.getTableName())
             .setCreateBy(username);

        List<CodeColumnConfigDto> oldRecords = columnConfigService.pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(oldRecords)) {
            Set<Serializable> ids = oldRecords.stream()
                                              .map(CodeColumnConfigDto::getId)
                                              .collect(Collectors.toSet());

            if (CollectionUtil.isNotEmpty(ids)) {
                columnConfigService.deleteBatchByIds(ids);
            }
        }
        return columnConfigService.insertBatch(list);
    }

    @Override
    public ConfigBuilder generateCode(CodeTableConfigQuery query) {
        CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
        globalQuery.setCreateBy(query.getCreateBy());
        CodeGlobalConfigDto globalConfigDto = queryOrCreateGlobalConfig(globalQuery);
        if (globalConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfig(query);
        if (tableConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(query.getCreateBy());
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        return builder;
    }

    @Override
    public void downloadCode(CodeTableConfigQuery query, HttpServletRequest request,
        HttpServletResponse response) {
        CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
        globalQuery.setCreateBy(query.getCreateBy());
        CodeGlobalConfigDto globalConfigDto = queryOrCreateGlobalConfig(globalQuery);
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfig(query);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(query.getCreateBy());
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        String tmpSchemaPath = System.getProperty("java.io.tmpdir")
            + "dunwu"
            + File.separator
            + query.getSchemaName();
        String tmpTablePath = tmpSchemaPath + File.separator + query.getTableName();
        globalConfigDto.setOutputDir(tmpTablePath)
                       .setBackendPath(tmpTablePath + "/backend")
                       .setFrontendPath(tmpTablePath + "/frontend");
        tableConfigDto.setOutputDir(tmpTablePath)
                      .setBackendPath(tmpTablePath + "/backend")
                      .setFrontendPath(tmpTablePath + "/frontend");

        log.info("临时代码生成路径：{}", tmpTablePath);

        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        String zipFilePath = tmpSchemaPath + File.separator + "codes.zip";
        File zip = ZipUtil.zip(tmpTablePath, zipFilePath);
        ServletUtil.downloadFile(request, response, zip, true);
    }

    @Override
    public List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery query) {
        CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
        globalQuery.setCreateBy(query.getCreateBy());
        CodeGlobalConfigDto globalConfigDto = queryOrCreateGlobalConfig(globalQuery);
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfig(query);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(query.getCreateBy());
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        return codeGenerator.preview();
    }

    /**
     * 将 {@link CodeTableConfigDto} 转为 {@link ConfigBuilder}
     *
     * @param tableConfigDto CodeTableConfigDto 对象
     * @return /
     */
    public ConfigBuilder createConfigBuilder(CodeTableConfigDto tableConfigDto) {

        if (tableConfigDto.getDbId() == null) {
            throw new DataException("dbId 为空");
        }

        MntDatabaseDto databaseDto = databaseService.pojoById(tableConfigDto.getDbId());
        if (databaseDto == null) {
            throw new DataException(StrUtil.format("未找到数据库 dbId = {}", tableConfigDto.getDbId()));
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig(databaseDto.getJdbcUrl(), "com.mysql.cj.jdbc.Driver",
            databaseDto.getUsername(), databaseDto.getPassword(), tableConfigDto.getSchemaName());

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(tableConfigDto.getAuthor())
                    .setOutputDir(tableConfigDto.getOutputDir())
                    .setBackendDir(tableConfigDto.getBackendPath())
                    .setFrontendDir(tableConfigDto.getFrontendPath());

        PackageConfig packageConfig = new PackageConfig(tableConfigDto.getPackagePath(),
            tableConfigDto.getModuleName());

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude(tableConfigDto.getTableName());

        TemplateConfig templateConfig = new TemplateConfig();

        TableInfo tableInfo = transToTableInfo(tableConfigDto);

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);
        tableInfo = builder.processTable(tableInfo);
        builder.setTableInfoList(Collections.singletonList(tableInfo));
        return builder;
    }

    @Override
    public CodeTableConfigDto queryOrCreateCodeTableConfig(CodeTableConfigQuery query) {

        // 如果已经存在指定的数据表的表属性配置，则直接返回
        CodeTableConfigDto tableConfigDto = queryTableConfig(query);
        if (tableConfigDto != null) {
            return tableConfigDto;
        }

        // 查询全局配置
        CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
        globalQuery.setCreateBy(query.getCreateBy());
        CodeGlobalConfigDto globalConfigDto = queryOrCreateGlobalConfig(globalQuery);
        if (globalConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "请先配置全局配置");
        }

        // 初始化 CodeTableConfigDto
        tableConfigDto = new CodeTableConfigDto();
        tableConfigDto.setDbId(query.getDbId())
                      .setSchemaName(query.getSchemaName())
                      .setTableName(query.getTableName())
                      .setCreateBy(query.getCreateBy());

        // 将全局配置属性填充到 CodeTableConfigDto
        fillPropertyToCodeTableConfigDto(tableConfigDto, globalConfigDto);

        ConfigBuilder configBuilder = createConfigBuilder(tableConfigDto);
        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tableInfos)) {
            return null;
        }
        TableInfo tableInfo = tableInfos.get(0);
        fillPropertyToCodeTableConfigDto(tableConfigDto, tableInfo);
        return tableConfigDto;
    }

    public List<CodeColumnConfigDto> queryOrCreateCodeColumnConfig(CodeTableConfigQuery query) {
        return null;
    }

    /**
     * 根据 schemaName、tableName 查询表的实际数据信息
     *
     * @param dbId       数据库 ID
     * @param schemaName schema 名
     * @param tableName  table 名
     * @param createBy   配置者
     * @return /
     */
    private TableInfo queryTableInfo(Long dbId, String schemaName, String tableName, String createBy) {
        if (StrUtil.isBlank(schemaName)) {
            schemaName = tableService.getCurrentSchema();
        }

        CodeTableConfigQuery tableQuery = new CodeTableConfigQuery();
        tableQuery.setSchemaName(schemaName).setTableName(tableName).setCreateBy(createBy);
        CodeTableConfigDto tableConfigDto = queryTableConfig(tableQuery);

        CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
        globalQuery.setCreateBy(createBy);
        CodeGlobalConfigDto globalConfig = queryOrCreateGlobalConfig(globalQuery);
        ConfigBuilder configBuilder;
        if (tableConfigDto != null) {
            configBuilder = createConfigBuilder(tableConfigDto);
        } else {
            CodeTableConfigDto newTableConfigDto = BeanUtil.toBean(globalConfig, CodeTableConfigDto.class);
            newTableConfigDto.setDbId(dbId)
                             .setSchemaName(schemaName)
                             .setTableName(tableName)
                             .setCreateBy(createBy);
            configBuilder = createConfigBuilder(newTableConfigDto);
        }

        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        return tableInfos.get(0);
    }

    /**
     * 将 {@link CodeTableConfigDto} 转为 {@link TableInfo}
     *
     * @param tableConfigDto CodeTableConfigDto 对象
     * @return /
     */
    private TableInfo transToTableInfo(CodeTableConfigDto tableConfigDto) {
        TableInfo tableInfo = BeanUtil.toBean(tableConfigDto, TableInfo.class);

        if (CollectionUtil.isNotEmpty(tableConfigDto.getColumns())) {
            List<TableField> fields = new ArrayList<>();
            Map<String, TableField> fieldNameMap = new HashMap<>(fields.size());
            for (CodeColumnConfigDto column : tableConfigDto.getColumns()) {
                TableField field = transToTableField(column);
                fields.add(field);
                fieldNameMap.put(field.getFieldName(), field);
            }
            tableInfo.setFields(fields);
            tableInfo.setFieldMap(fieldNameMap);
        }

        return tableInfo;
    }

    /**
     * 将 {@link CodeColumnConfigDto} 转为 {@link TableField}
     *
     * @param columnConfigDto CodeColumnConfigDto 对象
     * @return /
     */
    private TableField transToTableField(CodeColumnConfigDto columnConfigDto) {
        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
        TableField tableField = BeanUtil.toBean(columnConfigDto, TableField.class, copyOptions);
        tableField.setJavaType(JavaColumnType.getJavaColumnTypeByType(columnConfigDto.getJavaType()));
        return tableField;
    }

    private CodeColumnConfigDto transToCodeColumnConfigDto(TableField field, String schemaName, String tableName) {
        CodeColumnConfigDto column = BeanUtil.toBean(field, CodeColumnConfigDto.class);
        column.setSchemaName(schemaName).setTableName(tableName);
        if (field.getJavaType() != null) {
            column.setJavaType(field.getJavaType().getType());
        }
        return column;
    }

    private CodeColumnConfig transToCodeColumnConfig(TableField field, String schemaName, String tableName) {
        CodeColumnConfig column = BeanUtil.toBean(field, CodeColumnConfig.class);
        column.setSchemaName(schemaName).setTableName(tableName);
        if (field.getJavaType() != null) {
            column.setJavaType(field.getJavaType().getType());
        }
        return column;
    }

    private void fillPropertyToCodeTableConfigDto(CodeTableConfigDto tableConfigDto,
        CodeGlobalConfigDto codeGlobalConfigDto) {
        if (codeGlobalConfigDto == null) {
            return;
        }

        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("id");
        BeanUtil.copyProperties(codeGlobalConfigDto, tableConfigDto, copyOptions);
    }

    private void fillPropertyToCodeTableConfigDto(CodeTableConfigDto tableConfigDto, TableInfo tableInfo) {
        if (tableInfo == null) {
            return;
        }

        CopyOptions copyOptions = CopyOptions.create();
        BeanUtil.copyProperties(tableInfo, tableConfigDto, copyOptions);
    }

}
