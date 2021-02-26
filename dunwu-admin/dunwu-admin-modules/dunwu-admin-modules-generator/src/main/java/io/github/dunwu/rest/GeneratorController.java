package io.github.dunwu.rest;

import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.service.GenConfigService;
import io.github.dunwu.service.GeneratorService;
import io.github.dunwu.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-02
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("generator")
@Api(tags = "系统：代码生成管理")
public class GeneratorController {

    private final GeneratorService generatorService;
    private final GenConfigService genConfigService;
    private final TableService tableService;
    private final CodeColumnConfigService codeColumnConfigService;

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
        List<CodeColumnConfigDto> columnInfos = codeColumnConfigService.pojoListByQuery(query);
        return new ResponseEntity<>(PageUtil.toPage(columnInfos, columnInfos.size()), HttpStatus.OK);
    }

    @ApiOperation("保存字段数据")
    @PutMapping
    public ResponseEntity<HttpStatus> save(@RequestBody List<CodeColumnConfig> columnInfos) {
        codeColumnConfigService.saveBatch(columnInfos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("同步字段数据")
    @PostMapping(value = "sync")
    public ResponseEntity<HttpStatus> sync(@RequestBody Collection<String> tables) {
        codeColumnConfigService.sync(tables);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("生成代码")
    @PostMapping(value = "/{tableName}/{type}")
    public ResponseEntity<Object> generator(@PathVariable String tableName, @PathVariable Integer type,
        HttpServletRequest request, HttpServletResponse response) {
        if (!generatorEnabled && type == 0) {
            throw new BadRequestException("此环境不允许生成代码，请选择预览或者下载查看！");
        }
        switch (type) {
            // 生成代码
            case 0:
                generatorService.generator(genConfigService.find(tableName), generatorService.getColumns(tableName));
                break;
            // 预览
            case 1:
                return generatorService.preview(genConfigService.find(tableName),
                    generatorService.getColumns(tableName));
            // 打包
            case 2:
                generatorService.download(genConfigService.find(tableName), generatorService.getColumns(tableName),
                    request, response);
                break;
            default:
                throw new BadRequestException("没有这个选项");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
