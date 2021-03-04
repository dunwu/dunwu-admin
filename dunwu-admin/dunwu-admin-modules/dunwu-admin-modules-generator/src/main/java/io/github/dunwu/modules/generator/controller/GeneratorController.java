package io.github.dunwu.modules.generator.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.generator.config.GlobalConfig;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@RestController
@RequestMapping("generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final CodeTableConfigService tableConfigService;
    private final CodeColumnConfigService columnConfigService;
    private final TableService tableService;

    public GeneratorController(GeneratorService generatorService,
        CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService, TableService tableService) {
        this.generatorService = generatorService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
        this.tableService = tableService;
    }

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public DataListResult<CodeGenerateContentDto> generator(@PathVariable String tableName, @PathVariable Integer type,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setTableName(tableName);
        CodeTableConfigDto tableConfig = tableConfigService.find(codeTableConfigQuery);
        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(tableConfig.getSchemaName()).setTableName(tableConfig.getTableName());
        List<CodeColumnConfigDto> columnConfigs = columnConfigService.pojoListByQuery(codeColumnConfigQuery);

        switch (type) {
            // 预览
            case 1:
                List<CodeGenerateContentDto> previewList = columnConfigService.getPreviewList(tableConfig,
                    columnConfigs);
                return DataListResult.ok(previewList);
            // 生成
            case 2:
                columnConfigService.generate(tableConfig, columnConfigs, request, response);
                return new DataListResult<>(ResultStatus.OK);
            case 3:
                columnConfigService.generate(tableConfig, columnConfigs, request, response);
                return new DataListResult<>(ResultStatus.OK);
            default:
                throw new BadRequestException("没有这个选项");
        }
    }

    @ApiOperation("生成代码")
    @GetMapping(value = "preview/{schemaName}/{tableName}")
    public DataListResult<CodeGenerateContentDto> previewGenerateCode(@PathVariable String schemaName,
        @PathVariable String tableName) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        List<CodeGenerateContentDto> previewList = generatorService.previewGenerateCode(codeTableConfigQuery);
        return DataListResult.ok(previewList);
    }

    @ApiOperation("生成代码")
    @GetMapping(value = "download/{schemaName}/{tableName}")
    public void downloadGenerateCode(@PathVariable String schemaName, @PathVariable String tableName,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setSchemaName(schemaName).setTableName(tableName);
        generatorService.downloadGenerateCode(codeTableConfigQuery, request, response);
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("global/find")
    public DataResult<CodeGlobalConfigDto> findGlobalConfigByCurrentUser() {
        CodeGlobalConfigDto dto = generatorService.findGlobalConfigByCurrentUser();
        if (dto == null) {
            GlobalConfig globalConfig = new GlobalConfig();
            dto = BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
        }
        return DataResult.ok(dto);
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("global/save")
    public BaseResult saveGlobalConfigByCurrentUser(@RequestBody CodeGlobalConfig entity) {
        generatorService.saveGlobalConfigByCurrentUser(entity);
        return BaseResult.ok();
    }

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("table/find/{schemaName}/{tableName}")
    public DataResult<CodeTableConfigDto> findTableConfigByCurrentUser(@PathVariable String schemaName,
        @PathVariable String tableName) {
        CodeTableConfigQuery query = new CodeTableConfigQuery();
        query.setSchemaName(schemaName).setTableName(tableName);
        CodeTableConfigDto dto = generatorService.findTableConfigByCurrentUser(query);
        if (dto == null) {
            CodeGlobalConfigDto globalConfigDto = generatorService.findGlobalConfigByCurrentUser();
            CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("id");
            dto = BeanUtil.toBean(globalConfigDto, CodeTableConfigDto.class, copyOptions);
            dto.setSchemaName(query.getSchemaName())
               .setTableName(query.getTableName())
               .setAuthor(globalConfigDto.getAuthor());
        }
        return DataResult.ok(dto);
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("table/save")
    public BaseResult saveTableConfigByCurrentUser(@RequestBody CodeTableConfig entity) {
        generatorService.saveTableConfigByCurrentUser(entity);
        return BaseResult.ok();
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "table/all/page")
    public DataResult<Object> queryTables(@RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return DataResult.ok(tableService.getTables(tableService.getCurrentSchema(), name, startEnd));
    }

}
