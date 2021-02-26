package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.dto.SysDictOptionDto;
import io.github.dunwu.modules.system.entity.query.SysDictOptionQuery;
import io.github.dunwu.modules.system.service.SysDictOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典项 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dict/option")
@Api(tags = "系统：字典选项管理")
@RequiredArgsConstructor
public class SysDictOptionController {

    private final SysDictOptionService service;

    @Log("添加一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:add')")
    @ApiOperation("添加一条 SysDictOption 记录")
    @PostMapping("add")
    public BaseResult add(@Validated(AddCheck.class) @RequestBody SysDictOptionDto entity) {
        service.save(entity);
        return BaseResult.ok();
    }

    @Log("更新一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:edit')")
    @ApiOperation("更新一条 SysDictOption 记录")
    @PostMapping("edit")
    public BaseResult edit(@Validated(EditCheck.class) @RequestBody SysDictOptionDto entity) {
        service.updateById(entity);
        return BaseResult.ok();
    }

    @Log("删除一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("删除一条 SysDictOption 记录")
    @PostMapping("del/{id}")
    public BaseResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return BaseResult.ok();
    }

    @Log("根据 ID 集合批量删除 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDictOption 记录")
    @PostMapping("del/batch")
    public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return BaseResult.ok();
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDictOptionDto 列表")
    @GetMapping("list")
    public DataListResult<SysDictOptionDto> list(SysDictOptionQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDictOptionDto 记录")
    @GetMapping("page")
    public PageResult<SysDictOptionDto> page(SysDictOptionQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult<Integer> count(SysDictOptionQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 查询 SysDictOptionDto 记录")
    @GetMapping("{id}")
    public DataResult<SysDictOptionDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDictOptionDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysDictOptionQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDictOptionDto 列表数据")
    @GetMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

}
