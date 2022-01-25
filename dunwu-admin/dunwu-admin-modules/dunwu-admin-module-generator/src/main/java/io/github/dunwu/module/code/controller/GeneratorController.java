package io.github.dunwu.module.code.controller;

import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.CodeTableConfig;
import io.github.dunwu.module.code.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.module.code.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.module.code.entity.dto.CodeTableConfigDto;
import io.github.dunwu.module.code.entity.dto.TableColumnInfoDto;
import io.github.dunwu.module.code.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.module.code.service.GeneratorService;
import io.github.dunwu.module.code.service.TableService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.MapResult;
import io.github.dunwu.tool.generator.engine.CodeGenerateContentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成管理 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
@Slf4j
@RestController
@RequestMapping("code")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final TableService tableService;
    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    public GeneratorController(GeneratorService generatorService,
        TableService tableService) {
        this.generatorService = generatorService;
        this.tableService = tableService;
    }

    @ApiOperation("生成代码")
    @GetMapping(value = "generate/{schemaName}/{tableName}")
    public DataResult<Boolean> generateCode(@PathVariable String schemaName, @PathVariable String tableName) {
        if (!generatorEnabled) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        generatorService.generateCode(codeTableConfigQuery);
        return DataResult.ok();
    }

    @ApiOperation("预览代码")
    @GetMapping(value = "preview/{schemaName}/{tableName}")
    public DataListResult<CodeGenerateContentDto> previewCode(@PathVariable String schemaName,
        @PathVariable String tableName) {
        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        List<CodeGenerateContentDto> previewList = generatorService.previewCode(codeTableConfigQuery);
        return DataListResult.ok(previewList);
    }

    @ApiOperation("下载代码")
    @GetMapping(value = "download/{schemaName}/{tableName}")
    public void downloadCode(@PathVariable String schemaName, @PathVariable String tableName,
        HttpServletRequest request, HttpServletResponse response) {
        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        generatorService.downloadCode(codeTableConfigQuery, request, response);
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("global/query")
    public DataResult<CodeGlobalConfigDto> queryGlobalConfig(CodeGlobalConfigQuery query) {
        return DataResult.ok(generatorService.queryOrCreateGlobalConfig(query));
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("global/save")
    public DataResult<Boolean> saveGlobalConfig(@Validated @RequestBody CodeGlobalConfig entity) {
        return DataResult.ok(generatorService.saveGlobalConfig(entity));
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("table/query")
    public DataResult<CodeTableConfigDto> queryTableConfig(CodeTableConfigQuery query) {
        return DataResult.ok(generatorService.queryOrCreateCodeTableConfig(query));
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("table/save")
    public DataResult<Boolean> saveTableConfig(@Validated @RequestBody CodeTableConfig entity) {
        return DataResult.ok(generatorService.saveTableConfig(entity));
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 CodeColumnConfigDto 列表")
    @GetMapping("column/query")
    public DataListResult<CodeColumnConfigDto> queryColumnConfig(CodeColumnConfigQuery query) {
        return DataListResult.ok(generatorService.queryColumnConfigList(query));
    }

    @ApiOperation("批量更新 CodeColumnConfig 记录")
    @PostMapping("column/saveBatch")
    public DataResult<Boolean> saveColumnConfigList(@Validated @RequestBody TableColumnInfoDto entity) {
        return DataResult.ok(generatorService.saveColumnConfigList(entity));
    }

    @ApiOperation("同步表")
    @GetMapping("table/sync")
    public DataListResult<CodeColumnConfigDto> querySyncTableInfo(CodeTableConfigQuery query) {
        return DataListResult.ok(generatorService.querySyncTableInfo(query));
    }

    @ApiOperation("查询数据库数据")
    @GetMapping("table/all/page")
    public MapResult<String, Object> queryAllTables(@RequestParam Long dbId,
        @RequestParam(defaultValue = "") String tableName,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        return MapResult.ok(tableService.getTables(dbId, tableName, page, size));
    }

}
