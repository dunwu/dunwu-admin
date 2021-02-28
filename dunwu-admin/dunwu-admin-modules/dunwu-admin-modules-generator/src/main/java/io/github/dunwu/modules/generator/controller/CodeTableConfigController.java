package io.github.dunwu.modules.generator.controller;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/generator/table")
@Api(tags = "代码生成-表级别配置 Controller 类")
public class CodeTableConfigController {

    private final CodeTableConfigService service;
    private final TableService tableService;

    public CodeTableConfigController(CodeTableConfigService service,
        TableService tableService) {
        this.service = service;
        this.tableService = tableService;
    }

    /** 添加一条 {@link CodeTableConfig} 记录 */
    @ApiOperation("添加一条 CodeTableConfig 记录")
    @PostMapping("add")
    public BaseResult add(@Validated(AddCheck.class) @RequestBody CodeTableConfig entity) {
        service.save(entity);
        return BaseResult.ok();
    }

    /** 批量添加 {@link CodeTableConfig} 记录 */
    @ApiOperation("批量添加 CodeTableConfig 记录")
    @PostMapping("add/batch")
    public BaseResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<CodeTableConfig> list) {
        service.saveBatch(list);
        return BaseResult.ok();
    }

    /** 更新一条 {@link CodeTableConfig} 记录 */
    @ApiOperation("更新一条 CodeTableConfig 记录")
    @PostMapping("edit")
    public BaseResult edit(@Validated(EditCheck.class) @RequestBody CodeTableConfig entity) {
        service.updateById(entity);
        return BaseResult.ok();
    }

    /** 批量更新 {@link CodeTableConfig} 记录 */
    @ApiOperation("批量更新 CodeTableConfig 记录")
    @PostMapping("edit/batch")
    public BaseResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<CodeTableConfig> list) {
        service.updateBatchById(list);
        return BaseResult.ok();
    }

    @ApiOperation("删除一条 CodeTableConfig 记录")
    @PostMapping("del/{id}")
    public BaseResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return BaseResult.ok();
    }

    @ApiOperation("根据 ID 集合批量删除 CodeTableConfig 记录")
    @PostMapping("del/batch")
    public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return BaseResult.ok();
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 CodeTableConfigDto 列表")
    @GetMapping("list")
    public DataListResult<CodeTableConfigDto> list(CodeTableConfigQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 query 和 pageable 条件，分页查询 CodeTableConfigDto 记录")
    @GetMapping("page")
    public PageResult<CodeTableConfigDto> page(CodeTableConfigQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult<Integer> count(CodeTableConfigQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @GetMapping("{id}")
    @ApiOperation("根据 ID 查询 CodeTableConfigDto 记录")
    public DataResult<CodeTableConfigDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @GetMapping("export")
    @ApiOperation("根据 ID 集合批量导出 CodeTableConfigDto 列表数据")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 CodeTableConfigDto 列表数据")
    public void exportPage(CodeTableConfigQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @GetMapping("find")
    public DataResult<CodeTableConfigDto> find(CodeTableConfigQuery query) {
        return DataResult.ok(service.find(query));
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "all")
    public DataResult<Object> queryTables() {
        return DataResult.ok(tableService.getTables());
    }

    @ApiOperation("查询数据库数据")
    @GetMapping(value = "all/page")
    public DataResult<Object> queryTables(@RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size) {
        int[] startEnd = PageUtil.transToStartEnd(page, size);
        return DataResult.ok(tableService.getTables(name, startEnd));
    }

}
