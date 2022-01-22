package io.github.dunwu.module.sys.controller;

import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictOptionQuery;
import io.github.dunwu.module.sys.service.DictOptionService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典选项 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/sys/dict/option")
@Api(tags = "数据字典选项 Controller 类")
@RequiredArgsConstructor
public class DictOptionController {

    private final DictOptionService service;

    @ApiOperation("添加一条 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody DictOption entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<DictOption> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody DictOption entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<DictOption> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 DictOption 记录")
    @PreAuthorize("@exp.check('sys:dict/option:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DictOptionQuery 查询 DictOptionDto 列表")
    @PreAuthorize("@exp.check('sys:dict/option:view')")
    @GetMapping("/list")
    public DataListResult<DictOptionDto> list(DictOptionQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 DictOptionQuery 分页查询 DictOptionDto 列表")
    @PreAuthorize("@exp.check('sys:dict/option:view')")
    @GetMapping("/page")
    public PageResult<DictOptionDto> page(Pageable pageable, DictOptionQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 DictOptionDto")
    @PreAuthorize("@exp.check('sys:dict/option:view')")
    @GetMapping("/{id}")
    public DataResult<DictOptionDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DictOptionQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(DictOptionQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DictOptionDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:dict/option:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 DictOptionQuery 分页查询 DictOptionDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:dict/option:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, DictOptionQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
