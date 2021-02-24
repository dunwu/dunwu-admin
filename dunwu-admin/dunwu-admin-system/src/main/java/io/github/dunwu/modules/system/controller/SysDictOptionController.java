package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.SysDictOption;
import io.github.dunwu.modules.system.entity.query.SysDictOptionQuery;
import io.github.dunwu.modules.system.service.SysDictOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @Log("创建一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:add')")
    @ApiOperation("创建一条 SysDictOption 记录")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysDictOption entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("更新一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:edit')")
    @ApiOperation("更新一条 SysDictOption 记录")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysDictOption entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @Log("删除一条 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("删除一条 SysDictOption 记录")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    @Log("根据 ID 集合批量删除 SysDictOption 记录")
    @PreAuthorize("@exp.check('dict:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDictOption 记录")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("查询 SysDictOptionDto 记录")
    public ResponseEntity<Object> view(SysDictOptionQuery query, Pageable pageable) {
        return page(query, pageable);
    }

    @GetMapping("page")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDictOptionDto 记录")
    public ResponseEntity<Object> page(SysDictOptionQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 查询 SysDictOption 记录")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @GetMapping("count")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    public ResponseEntity<Object> count(SysDictOptionQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("list")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDictOptionDto 列表")
    public ResponseEntity<Object> list(SysDictOptionQuery query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @GetMapping("export")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDictOptionDto 列表数据")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @GetMapping("export/page")
    @PreAuthorize("@exp.check('dict:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDictOptionDto 列表数据")
    public void exportPageData(SysDictOptionQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPageData(query, pageable, response);
    }

}
