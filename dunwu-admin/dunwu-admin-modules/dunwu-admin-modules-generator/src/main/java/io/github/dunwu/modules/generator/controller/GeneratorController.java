package io.github.dunwu.modules.generator.controller;

import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final TableService tableService;
    private final CodeTableConfigService tableConfigService;
    private final CodeColumnConfigService columnConfigService;

    public GeneratorController(GeneratorService generatorService,
        TableService tableService, CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService) {
        this.generatorService = generatorService;
        this.tableService = tableService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
    }

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/all")
    public ResponseEntity<Object> queryTables() {
        return new ResponseEntity<>(tableService.getTables(), HttpStatus.OK);
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "/tables/page")
    public ResponseEntity<Object> queryTables(@RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return new ResponseEntity<>(tableService.getTables(name, startEnd), HttpStatus.OK);
    }

    @ApiOperation("查询字段数据")
    @GetMapping(value = "/columns")
    public ResponseEntity<Object> queryColumns(@RequestParam String tableName) {
        CodeColumnConfigQuery query = new CodeColumnConfigQuery();
        query.setTableName(tableName);
        List<CodeColumnConfigDto> columnInfos = columnConfigService.pojoListByQuery(query);
        return new ResponseEntity<>(PageUtil.toPage(columnInfos, columnInfos.size()), HttpStatus.OK);
    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<CodeColumnConfig> columnInfos) {
        columnConfigService.saveBatch(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generator(@PathVariable String tableName, @PathVariable Integer type,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }

        CodeTableConfigQuery query = new CodeTableConfigQuery();
        query.setTableName(tableName);
        CodeTableConfigDto entity = tableConfigService.find(query);
        switch (type) {
            // 生成代码
            case 0:
                generatorService.generator(entity, generatorService.getColumns(tableName));
                break;
            // 预览
            case 1:
                return generatorService.preview(entity, generatorService.getColumns(tableName));
            // 打包
            case 2:
                generatorService.download(entity, generatorService.getColumns(tableName), request, response);
                break;
            default:
                throw new BadRequestException("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
