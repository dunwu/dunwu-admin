package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.dunwu.data.core.DataException;
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
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.dto.TableColumnInfoDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.CodeGlobalConfigService;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.util.*;
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

    public GeneratorServiceImpl(CodeGlobalConfigService globalConfigService,
        CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService) {
        this.globalConfigService = globalConfigService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
    }

    @Override
    public CodeGlobalConfigDto findGlobalConfigByCurrentUser() {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }

        CodeGlobalConfigQuery query = new CodeGlobalConfigQuery();
        query.setCreateBy(username);
        return globalConfigService.pojoByQuery(query);
    }

    @Override
    public boolean saveGlobalConfigByCurrentUser(CodeGlobalConfig entity) {
        // CodeGlobalConfigDto dto = findGlobalConfigByCurrentUser();
        if (entity.getId() == null) {
            return globalConfigService.save(entity);
        } else {
            return globalConfigService.updateById(entity);
        }
    }

    @Override
    public CodeTableConfigDto findTableConfigByCurrentUser(CodeTableConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }
        query.setCreateBy(username);
        return tableConfigService.pojoByQuery(query);
    }

    @Override
    public boolean saveTableConfigByCurrentUser(CodeTableConfig entity) {
        if (entity.getId() == null) {
            return tableConfigService.save(entity);
        } else {
            return tableConfigService.updateById(entity);
        }
    }

    @Override
    public List<CodeColumnConfigDto> findColumnConfigByCurrentUser(CodeColumnConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }
        query.setCreateBy(username);
        return columnConfigService.pojoListByQuery(query);
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
        Set<Serializable> ids = oldRecords.stream()
                                          .map(CodeColumnConfigDto::getId)
                                          .collect(Collectors.toSet());
        if (CollectionUtil.isNotEmpty(ids)) {
            columnConfigService.removeByIds(ids);
            return columnConfigService.saveBatch(list);
        }
        return columnConfigService.saveBatch(list);
    }

    @Override
    public ConfigBuilder generateCode(CodeTableConfigQuery codeTableConfigQuery) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = findGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = findTableConfigByCurrentUser(codeTableConfigQuery);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(codeTableConfigQuery.getSchemaName())
                             .setTableName(codeTableConfigQuery.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = transToConfigBuilder(globalConfigDto, tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        return builder;
    }

    @Override
    public void downloadCode(CodeTableConfigQuery codeTableConfigQuery, HttpServletRequest request,
        HttpServletResponse response) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = findGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = findTableConfigByCurrentUser(codeTableConfigQuery);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(codeTableConfigQuery.getSchemaName())
                             .setTableName(codeTableConfigQuery.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        String tmpSchemaPath = System.getProperty("java.io.tmpdir")
            + "dunwu"
            + File.separator
            + codeTableConfigQuery.getSchemaName();
        String tmpTablePath = tmpSchemaPath + File.separator + codeTableConfigQuery.getTableName();
        globalConfigDto.setOutputDir(tmpTablePath)
                       .setBackendPath(tmpTablePath + "/backend")
                       .setFrontendPath(tmpTablePath + "/backend");
        tableConfigDto.setOutputDir(tmpTablePath)
                      .setBackendPath(tmpTablePath + "/backend")
                      .setFrontendPath(tmpTablePath + "/backend");

        log.info("临时代码生成路径：{}", tmpTablePath);

        ConfigBuilder builder = transToConfigBuilder(globalConfigDto, tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        String zipFilePath = tmpSchemaPath + File.separator + "codes.zip";
        FileUtil.mkdir(tmpTablePath);
        ZipUtil.zip(tmpTablePath, zipFilePath);

        ServletUtil.downloadFile(response, new File(zipFilePath), true);
    }

    @Override
    public List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery codeTableConfigQuery) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = findGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = findTableConfigByCurrentUser(codeTableConfigQuery);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(codeTableConfigQuery.getSchemaName())
                             .setTableName(codeTableConfigQuery.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = transToConfigBuilder(globalConfigDto, tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        return codeGenerator.preview();
    }

    public ConfigBuilder transToConfigBuilder(CodeGlobalConfigDto globalConfigDto, CodeTableConfigDto tableConfigDto) {
        String url =
            "jdbc:mysql://localhost:3306/eladmin?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig(url, "com.mysql.cj.jdbc.Driver", "root", "root");

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(tableConfigDto.getAuthor()).setOutputDir(tableConfigDto.getOutputDir());

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

    public TableInfo transToTableInfo(CodeTableConfigDto tableConfigDto) {
        List<TableField> fields = new ArrayList<>();
        Map<String, TableField> fieldNameMap = new HashMap<>(fields.size());
        for (CodeColumnConfigDto column : tableConfigDto.getColumns()) {
            TableField field = transToTableField(column);
            fields.add(field);
            fieldNameMap.put(field.getFieldName(), field);
        }
        TableInfo tableInfo = BeanUtil.toBean(tableConfigDto, TableInfo.class);
        tableInfo.setFields(fields);
        tableInfo.setFieldMap(fieldNameMap);
        return tableInfo;
    }

    public TableField transToTableField(CodeColumnConfigDto columnConfigDto) {
        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
        TableField tableField = BeanUtil.toBean(columnConfigDto, TableField.class, copyOptions);
        tableField.setJavaType(JavaColumnType.getJavaColumnTypeByType(columnConfigDto.getJavaType()));
        return tableField;
    }

}
