package io.github.dunwu.modules.generator.controller;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.constant.ResultStatus;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.generator.config.GlobalConfig;
import io.github.dunwu.generator.engine.TemplateContent;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
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

    public GeneratorController(GeneratorService generatorService,
        CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService) {
        this.generatorService = generatorService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
    }

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public DataListResult<TemplateContent> generator(@PathVariable String tableName, @PathVariable Integer type,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery codeTableConfigQuery = new CodeTableConfigQuery();
        codeTableConfigQuery.setName(tableName);
        CodeTableConfigDto tableConfig = tableConfigService.find(codeTableConfigQuery);
        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(tableConfig.getSchemaName()).setTableName(tableConfig.getName());
        List<CodeColumnConfigDto> columnConfigs = columnConfigService.pojoListByQuery(codeColumnConfigQuery);

        switch (type) {
            // 预览
            case 1:
                List<TemplateContent> previewList = columnConfigService.getPreviewList(tableConfig, columnConfigs);
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

    @ApiOperation("查询当前用户的 CodeGlobalConfigDto 配置")
    @GetMapping("global/find")
    public DataResult<CodeGlobalConfigDto> find() {
        CodeGlobalConfigDto dto = generatorService.findGlobalConfigByCurrentUser();
        if (dto == null) {
            GlobalConfig globalConfig = new GlobalConfig();
            dto = BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
        }
        return DataResult.ok(dto);
    }

    @ApiOperation("保存当前用户的 CodeGlobalConfigDto 配置")
    @PostMapping("global/save")
    public BaseResult save(@RequestBody CodeGlobalConfig entity) {
        generatorService.saveGlobalConfigByCurrentUser(entity);
        return BaseResult.ok();
    }

}
