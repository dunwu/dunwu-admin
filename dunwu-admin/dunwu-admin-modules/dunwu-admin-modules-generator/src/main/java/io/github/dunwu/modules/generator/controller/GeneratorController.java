package io.github.dunwu.modules.generator.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.generator.config.GlobalConfig;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.dto.TableColumnInfoDto;
import io.github.dunwu.modules.generator.entity.dto.TableSyncDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.modules.generator.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@Slf4j
@RestController
@RequestMapping("code")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final TableService tableService;

    public GeneratorController(GeneratorService generatorService,
        TableService tableService) {
        this.generatorService = generatorService;
        this.tableService = tableService;
    }

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @ApiOperation("生成代码")
    @GetMapping(value = "generate/{schemaName}/{tableName}")
    public Result generateCode(@PathVariable String schemaName, @PathVariable String tableName) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        generatorService.generateCode(codeTableConfigQuery);
        return Result.ok();
    }

    @ApiOperation("生成代码")
    @GetMapping(value = "preview/{schemaName}/{tableName}")
    public Result previewCode(@PathVariable String schemaName, @PathVariable String tableName) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        List<CodeGenerateContentDto> previewList = generatorService.previewCode(codeTableConfigQuery);
        return Result.ok(previewList);
    }

    @ApiOperation("生成代码")
    @GetMapping(value = "download/{schemaName}/{tableName}")
    public Result downloadCode(@PathVariable String schemaName, @PathVariable String tableName,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        generatorService.downloadCode(codeTableConfigQuery, request, response);
        return Result.ok();
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "table/sync")
    public Result syncTable(@RequestBody TableSyncDto tableSyncDto) {
        if (tableSyncDto == null || CollectionUtil.isEmpty(tableSyncDto.getTables())) {
            throw new IllegalArgumentException("参数错误");
        }

        generatorService.syncTables(tableSyncDto);
        return Result.ok();
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("global/find")
    public Result findGlobalConfigByCurrentUser() {
        CodeGlobalConfigDto dto = generatorService.findGlobalConfigByCurrentUser();
        if (dto == null) {
            GlobalConfig globalConfig = new GlobalConfig();
            dto = BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
        }
        return Result.ok(dto);
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("global/save")
    public Result saveGlobalConfigByCurrentUser(@Validated(EditCheck.class) @RequestBody CodeGlobalConfig entity) {
        generatorService.saveGlobalConfigByCurrentUser(entity);
        return Result.ok();
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("table/find/{schemaName}/{tableName}")
    public Result findTableConfigByCurrentUser(@PathVariable String schemaName,
        @PathVariable String tableName) {
        CodeTableConfigQuery query = new CodeTableConfigQuery();
        query.setSchemaName(schemaName).setTableName(tableName);
        CodeTableConfigDto dto = generatorService.findTableConfigByCurrentUser(query);
        if (dto == null) {
            CodeGlobalConfigDto globalConfigDto = generatorService.findGlobalConfigByCurrentUser();
            CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("id");
            dto = BeanUtil.toBean(globalConfigDto, CodeTableConfigDto.class, copyOptions);
            dto.setSchemaName(query.getSchemaName())
               .setTableName(query.getTableName());
            if (StrUtil.isBlank(globalConfigDto.getAuthor())) {
                dto.setAuthor(globalConfigDto.getAuthor());
            }
        }
        return Result.ok(dto);
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("table/save")
    public Result saveTableConfigByCurrentUser(@Validated(EditCheck.class) @RequestBody CodeTableConfig entity) {
        generatorService.saveTableConfigByCurrentUser(entity);
        return Result.ok();
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 CodeColumnConfigDto 列表")
    @GetMapping("column/find/{schemaName}/{tableName}")
    public Result findColumnConfigByCurrentUser(@PathVariable String schemaName,
        @PathVariable String tableName) {
        CodeColumnConfigQuery query = new CodeColumnConfigQuery();
        query.setSchemaName(schemaName).setTableName(tableName);
        return Result.ok(generatorService.findColumnConfigByCurrentUser(query));
    }

    @ApiOperation("批量更新 CodeColumnConfig 记录")
    @PostMapping("column/saveBatch")
    public Result saveColumnsConfigByCurrentUser(
        @Validated(EditCheck.class) @RequestBody TableColumnInfoDto entity) {
        generatorService.saveColumnsConfigByCurrentUser(entity);
        return Result.ok();
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "table/all/page")
    public Result queryTables(@RequestParam(defaultValue = "") String tableName,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return Result.ok(tableService.getTables(tableService.getCurrentSchema(), tableName, startEnd));
    }

}
