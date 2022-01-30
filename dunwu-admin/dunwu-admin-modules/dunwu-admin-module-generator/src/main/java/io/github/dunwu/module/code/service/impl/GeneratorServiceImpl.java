package io.github.dunwu.module.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.dunwu.module.code.dao.CodeColumnConfigDao;
import io.github.dunwu.module.code.dao.CodeGlobalConfigDao;
import io.github.dunwu.module.code.entity.CodeColumnConfig;
import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.CodeTableConfig;
import io.github.dunwu.module.code.entity.dto.*;
import io.github.dunwu.module.code.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.module.code.service.*;
import io.github.dunwu.module.security.util.SecurityUtil;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.generator.CodeGenerator;
import io.github.dunwu.tool.generator.config.*;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.config.po.TableField;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.config.rules.FormType;
import io.github.dunwu.tool.generator.config.rules.JavaColumnType;
import io.github.dunwu.tool.generator.config.rules.ListType;
import io.github.dunwu.tool.generator.config.rules.ValidateType;
import io.github.dunwu.tool.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
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
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final CodeGlobalConfigService globalConfigService;
    private final CodeTableConfigService tableConfigService;
    private final CodeColumnConfigService columnConfigService;
    private final CodeGlobalConfigDao globalConfigDao;
    private final CodeColumnConfigDao columnConfigDao;
    private final CodeDatabaseService databaseService;

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

        // 查询表级配置
        CodeTableConfigQuery tableQuery = CodeTableConfigQuery.builder()
                                                              .schemaName(schemaName)
                                                              .tableName(tableName)
                                                              .build();
        CodeTableConfigDto tableConfigDto = queryTableConfigByQuery(tableQuery);

        // 查不到表计配置，则根据实际表信息和全局配置填充表信息
        if (tableConfigDto == null) {
            // 查询全局级配置
            CodeGlobalConfigQuery globalQuery = new CodeGlobalConfigQuery();
            globalQuery.setCreateBy(createBy);
            CodeGlobalConfigDto globalConfig = queryOrCreateGlobalConfig(globalQuery);

            tableConfigDto = BeanUtil.toBean(globalConfig, CodeTableConfigDto.class);
            tableConfigDto.setDbId(dbId)
                          .setSchemaName(schemaName)
                          .setTableName(tableName)
                          .setCreateBy(createBy);
        }

        ConfigBuilder configBuilder = createConfigBuilder(tableConfigDto);
        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tableInfos)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("找不到要查询的数据表 schema = {}, table = {}", schemaName, tableName));
        }
        return tableInfos.get(0);
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
    public CodeTableConfigDto queryOrCreateCodeTableConfig(CodeTableConfigQuery query) {

        String username = getCurrentUsername();

        // 检查全局级配置
        CodeGlobalConfigDto globalConfigDto = queryAndCheckGlobalConfig();

        // 如果已经存在指定的数据表的表属性配置，则直接返回
        CodeTableConfigDto tableConfigDto = tableConfigService.pojoByQuery(query);
        if (tableConfigDto != null) {
            return tableConfigDto;
        }

        // 初始化 CodeTableConfigDto
        tableConfigDto = new CodeTableConfigDto();
        tableConfigDto.setDbId(query.getDbId())
                      .setSchemaName(query.getSchemaName())
                      .setTableName(query.getTableName())
                      .setCreateBy(username);

        // 查询该表的实际属性，并填充到 CodeTableConfigDto
        ConfigBuilder configBuilder = createConfigBuilder(tableConfigDto);
        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tableInfos)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("找不到要查询的数据表 schema = {}, table = {}", query.getSchemaName(), query.getTableName()));
        }
        TableInfo tableInfo = tableInfos.get(0);

        // 将 TableInfo 的属性填充到 CodeTableConfigDto
        CopyOptions tableCopyOptions = CopyOptions.create();
        BeanUtil.copyProperties(tableInfo, tableConfigDto, tableCopyOptions);

        // 将全局配置属性填充到 CodeTableConfigDto
        CopyOptions globalCopyOptions = CopyOptions.create().setIgnoreProperties("id");
        BeanUtil.copyProperties(globalConfigDto, tableConfigDto, globalCopyOptions);

        return tableConfigDto;
    }

    @Override
    public List<CodeColumnConfigDto> queryOrCreateColumnConfigList(CodeColumnConfigQuery query) {

        // 查询并检查表级配置
        CodeTableConfigQuery tableQuery = BeanUtil.toBean(query, CodeTableConfigQuery.class);
        CodeTableConfigDto tableConfigDto = queryAndCheckTableConfig(tableQuery);

        // 如果已经存在指定的数据表的列属性配置，则直接返回
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(columns)) {
            return columns;
        }

        ConfigBuilder configBuilder = createConfigBuilder(tableConfigDto);
        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tableInfos)) {
            return null;
        }
        TableInfo tableInfo = tableInfos.get(0);
        // 将 TableInfo 的属性填充到 CodeTableConfigDto
        CopyOptions copyOptions = CopyOptions.create();
        BeanUtil.copyProperties(tableInfo, tableConfigDto, copyOptions);
        return tableInfo.getFields().stream()
                        .filter(Objects::nonNull)
                        .map(i -> transToCodeColumnConfigDto(i, query.getSchemaName(), query.getTableName()))
                        .collect(Collectors.toList());
    }

    @Override
    public List<CodeColumnConfigDto> syncQueryColumnConfigList(CodeTableConfigQuery tableQuery) {

        // 查询表配置信息
        CodeTableConfigDto tableConfigDto = queryTableConfigByQuery(tableQuery);
        if (tableConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("请先配置 schema = {}, table = {} 的表配置", tableQuery.getSchemaName(),
                    tableQuery.getTableName()));
        }

        // 查询实际表字段
        ConfigBuilder configBuilder = createConfigBuilder(tableConfigDto);
        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tableInfos)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("查不到指定表 schema = {}, table = {}", tableQuery.getSchemaName(),
                    tableQuery.getTableName()));
        }
        TableInfo tableInfo = tableInfos.get(0);

        // 将 TableInfo 的属性填充到 CodeTableConfigDto
        CopyOptions copyOptions = CopyOptions.create();
        BeanUtil.copyProperties(tableInfo, tableConfigDto, copyOptions);

        List<TableField> fields = tableInfo.getFields();
        if (CollectionUtil.isEmpty(fields)) {
            throw new DataException(StrUtil.format("找不到 schema = {}, table = {} 表信息",
                tableQuery.getSchemaName(), tableQuery.getTableName()));
        }

        String username = getCurrentUsername();
        CodeColumnConfigQuery columnQuery = CodeColumnConfigQuery.builder()
                                                                 .schemaName(tableQuery.getSchemaName())
                                                                 .tableName(tableQuery.getTableName())
                                                                 .createBy(username)
                                                                 .build();
        List<CodeColumnConfigDto> oldColumns = columnConfigService.pojoListByQuery(columnQuery);

        Map<String, CodeColumnConfigDto> map;
        if (CollectionUtil.isNotEmpty(oldColumns)) {
            map = oldColumns.stream()
                            .collect(Collectors.toMap(CodeColumnConfigDto::getFieldName, Function.identity()));
        } else {
            map = new HashMap<>(fields.size());
        }

        List<CodeColumnConfigDto> newColumns = new ArrayList<>();
        for (TableField field : fields) {
            CodeColumnConfigDto columnConfigDto = syncCodeColumnConfigDto(map.get(field.getFieldName()), field);
            newColumns.add(columnConfigDto);
        }
        return newColumns;
    }

    private CodeGlobalConfigDto queryAndCheckGlobalConfig() {
        String username = getCurrentUsername();
        LambdaQueryWrapper<CodeGlobalConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CodeGlobalConfig::getCreateBy, username);
        CodeGlobalConfigDto globalConfigDto = globalConfigDao.pojoOne(wrapper, CodeGlobalConfigDto.class);
        if (globalConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "请先配置全局配置");
        }
        return globalConfigDto;
    }

    private CodeTableConfigDto queryAndCheckTableConfig(CodeTableConfigQuery tableQuery) {
        // 检查全局级配置
        queryAndCheckGlobalConfig();

        CodeTableConfigDto tableConfigDto = queryTableConfigByQuery(tableQuery);
        if (tableConfigDto == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "未配置表级别配置");
        }
        return tableConfigDto;
    }

    private CodeTableConfigDto queryAndCheckColumnConfig(CodeTableConfigQuery tableQuery) {

        String username = getCurrentUsername();

        // 检查全局级配置 + 检查表级配置
        tableQuery.setCreateBy(username);
        CodeTableConfigDto tableConfigDto = queryAndCheckTableConfig(tableQuery);

        // 检查列级配置

        CodeColumnConfigQuery columnQuery = CodeColumnConfigQuery.builder()
                                                                 .dbId(tableQuery.getDbId())
                                                                 .schemaName(tableQuery.getSchemaName())
                                                                 .tableName(tableQuery.getTableName())
                                                                 .createBy(username)
                                                                 .build();
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(columnQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "未配置列级别配置");
        }
        tableConfigDto.setColumns(columns);
        return tableConfigDto;
    }

    private CodeTableConfigDto queryTableConfigByQuery(CodeTableConfigQuery tableQuery) {
        return tableConfigService.pojoByQuery(tableQuery);
    }

    @Override
    public boolean saveGlobalConfig(CodeGlobalConfig entity) {
        return globalConfigService.save(entity);
    }

    @Override
    public boolean saveTableConfig(CodeTableConfig entity) {
        queryAndCheckGlobalConfig();
        return tableConfigService.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveColumnConfigList(TableColumnInfoDto entity) {
        if (entity == null || CollectionUtil.isEmpty(entity.getColumns())) {
            throw new IllegalArgumentException("列信息为空");
        }

        // 查询并检查表级配置
        String username = getCurrentUsername();
        CodeTableConfigQuery tableQuery = CodeTableConfigQuery.builder()
                                                              .dbId(entity.getDbId())
                                                              .schemaName(entity.getSchemaName())
                                                              .tableName(entity.getTableName())
                                                              .createBy(username)
                                                              .build();
        CodeTableConfigDto tableConfigDto = queryAndCheckTableConfig(tableQuery);

        // 先删除旧配置
        LambdaQueryWrapper<CodeColumnConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CodeColumnConfig::getDbId, tableConfigDto.getDbId())
               .eq(CodeColumnConfig::getTableId, tableConfigDto.getId())
               .eq(CodeColumnConfig::getSchemaName, tableConfigDto.getSchemaName())
               .eq(CodeColumnConfig::getTableName, tableConfigDto.getTableName());
        columnConfigDao.delete(wrapper);

        // 设置数据库、表信息
        entity.getColumns().forEach(i -> {
            i.setDbId(tableConfigDto.getDbId());
            i.setTableId(tableConfigDto.getId());
        });

        List<CodeColumnConfigDto> columns = entity.getColumns().stream()
                                                  .map(i -> {
                                                      CodeColumnConfigDto columnDto = columnConfigService.doToDto(i);
                                                      columnDto.setDbId(tableConfigDto.getDbId());
                                                      columnDto.setTableId(tableConfigDto.getId());
                                                      return columnDto;
                                                  })
                                                  .collect(Collectors.toList());
        tableConfigDto.setColumns(columns);

        // 创建构造器
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);

        // 根据表级配置创建构造器
        checkTableChanged(tableConfigDto, builder);

        return columnConfigService.saveBatch(entity.getColumns());
    }

    @Override
    public ConfigBuilder generateCode(CodeTableConfigQuery query) {

        // 查询并检查表级配置
        CodeTableConfigDto tableConfigDto = queryAndCheckColumnConfig(query);

        // 创建构造器
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);

        // 根据表级配置创建构造器
        checkTableChanged(tableConfigDto, builder);

        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        return builder;
    }

    @Override
    public ConfigBuilder downloadCode(CodeTableConfigQuery query, HttpServletRequest request,
        HttpServletResponse response) {

        // 查询并检查表级配置
        CodeTableConfigDto tableConfigDto = queryAndCheckColumnConfig(query);

        // 将代码输出路径输出到临时路径
        String tmpSchemaPath = System.getProperty("java.io.tmpdir")
            + "dunwu"
            + File.separator
            + query.getSchemaName();
        String tmpTablePath = tmpSchemaPath + File.separator + query.getTableName();
        tableConfigDto.setOutputDir(tmpTablePath)
                      .setBackendPath(tmpTablePath + "/backend")
                      .setFrontendPath(tmpTablePath + "/frontend");

        if (FileUtil.exist(tmpTablePath)) {
            FileUtil.del(tmpTablePath);
        }

        // 创建构造器
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);

        // 根据表级配置创建构造器
        checkTableChanged(tableConfigDto, builder);

        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        String zipFilePath = tmpSchemaPath + File.separator + "codes.zip";
        File zip = ZipUtil.zip(tmpTablePath, zipFilePath);
        ServletUtil.downloadFile(request, response, zip, true);
        return builder;
    }

    @Override
    public List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery query) {

        // 查询并检查表级配置
        CodeTableConfigDto tableConfigDto = queryAndCheckColumnConfig(query);

        // 创建构造器
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);

        // 根据表级配置创建构造器
        checkTableChanged(tableConfigDto, builder);

        CodeGenerator codeGenerator = new CodeGenerator(builder);
        return codeGenerator.preview();
    }

    public CodeColumnConfigDto syncCodeColumnConfigDto(CodeColumnConfigDto columnConfigDto, TableField field) {

        if (columnConfigDto == null) {
            columnConfigDto = new CodeColumnConfigDto();
            CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
            BeanUtil.copyProperties(field, columnConfigDto, copyOptions);
            columnConfigDto.setJavaType(field.getJavaType().getType());
        } else {
            columnConfigDto.setComment(field.getComment())
                           .setType(field.getType())
                           .setJavaType(field.getJavaType().getType())
                           .setKeyType(field.getKeyType())
                           .setNotNull(field.isNotNull());
        }
        return columnConfigDto;
    }

    private void checkTableChanged(CodeTableConfigDto tableConfigDto, ConfigBuilder builder) {

        List<TableInfo> tables = builder.queryTableInfoList();
        if (CollectionUtil.isEmpty(tables)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("查不到指定表 schema = {}, table = {}",
                    tableConfigDto.getSchemaName(), tableConfigDto.getTableName()));
        }
        TableInfo tableInfo = tables.get(0);
        if (CollectionUtil.isEmpty(tableInfo.getFields())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("查不到指定表的列信息 schema = {}, table = {}",
                    tableConfigDto.getSchemaName(), tableConfigDto.getTableName()));
        }

        // 检查表字段是否有变化
        if (isColumnsChanged(tableConfigDto.getColumns(), tableInfo.getFields())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                StrUtil.format("schema = {}, table = {} 表已经发生变化，请先同步配置",
                    tableConfigDto.getSchemaName(), tableConfigDto.getTableName()));
        }
    }

    private boolean isColumnsChanged(List<CodeColumnConfigDto> columns, List<TableField> fields) {

        if (CollectionUtil.isEmpty(fields)) {
            log.error("fields 为空");
            return false;
        }

        if (CollectionUtil.isEmpty(columns)) {
            log.error("columns 为空");
            return false;
        }

        Map<String, CodeColumnConfigDto> map = columns.stream().collect(
            Collectors.toMap(CodeColumnConfigDto::getFieldName, Function.identity()));
        for (TableField field : fields) {
            // columns 不包含 fields 中的字段，说明表中已添加新字段
            if (!map.containsKey(field.getFieldName())) {
                log.error("{} 表新增 {} 字段", field.getTableName(), field.getFieldName());
                return true;
            }
            boolean flag = isColumnChanged(map.get(field.getFieldName()), field);
            if (flag) {
                log.error("{} 表 {} 字段发生变化", field.getTableName(), field.getFieldName());
                return true;
            }
        }
        return false;
    }

    public boolean isColumnChanged(CodeColumnConfigDto columnConfigDto, TableField field) {
        if (columnConfigDto == null || field == null) {
            return false;
        }

        boolean flag = columnConfigDto.getComment().equals(field.getComment())
            && columnConfigDto.getType().equals(field.getType())
            && columnConfigDto.getJavaType().equals(field.getJavaType().getType())
            && columnConfigDto.getKeyType().equals(field.getKeyType());
        return !flag;
    }

    /**
     * 将 {@link TableField} 转为 {@link CodeColumnConfigDto}
     *
     * @param field      TableField 对象
     * @param schemaName schema 名
     * @param tableName  table 名
     * @return /
     */
    private CodeColumnConfigDto transToCodeColumnConfigDto(TableField field, String schemaName, String tableName) {

        if (field == null) {
            return null;
        }

        CodeColumnConfigDto column = BeanUtil.toBean(field, CodeColumnConfigDto.class);
        column.setSchemaName(schemaName).setTableName(tableName);
        if (field.getJavaType() != null) {
            column.setJavaType(field.getJavaType().getType());
            JavaColumnType javaType = (JavaColumnType) field.getJavaType();
            switch (javaType) {
                case DATE_SQL:
                case TIME:
                case TIMESTAMP:
                case LOCAL_DATE:
                case LOCAL_TIME:
                case LOCAL_DATE_TIME:
                case DATE:
                    column.setListType(ListType.Date.getCode());
                    column.setFormType(FormType.DateTimePicker.getCode());
                    break;
                case STRING:
                default:
                    column.setFormType(FormType.Input.getCode());
                    column.setValidateType(ValidateType.STRING.getCode());
                    break;
            }
        }
        return column;
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

        CodeDatabaseDto databaseDto = databaseService.pojoById(tableConfigDto.getDbId());
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
        if (StrUtil.isNotBlank(tableConfigDto.getTablePrefix())) {
            strategyConfig.setTablePrefix(tableConfigDto.getTablePrefix());
        }

        TemplateConfig templateConfig = new TemplateConfig();

        TableInfo tableInfo = transToTableInfo(tableConfigDto);

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);
        tableInfo = builder.processTable(tableInfo);
        builder.setTableInfoList(Collections.singletonList(tableInfo));
        return builder;
    }

    /**
     * 将 {@link CodeTableConfigDto} 转为 {@link TableInfo}
     *
     * @param tableConfigDto CodeTableConfigDto 对象
     * @return /
     */
    private TableInfo transToTableInfo(CodeTableConfigDto tableConfigDto) {

        if (tableConfigDto == null) {
            return null;
        }

        TableInfo tableInfo = BeanUtil.toBean(tableConfigDto, TableInfo.class);
        if (CollectionUtil.isNotEmpty(tableConfigDto.getColumns())) {
            List<TableField> fields = new ArrayList<>();
            Map<String, TableField> fieldNameMap = new HashMap<>(tableConfigDto.getColumns().size());
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

        if (columnConfigDto == null) {
            return null;
        }

        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
        TableField tableField = BeanUtil.toBean(columnConfigDto, TableField.class, copyOptions);
        tableField.setJavaType(JavaColumnType.getJavaColumnTypeByType(columnConfigDto.getJavaType()));
        return tableField;
    }

    private String getCurrentUsername() {
        String username = SecurityUtil.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            return "admin";
        }
        return username;
    }

}
