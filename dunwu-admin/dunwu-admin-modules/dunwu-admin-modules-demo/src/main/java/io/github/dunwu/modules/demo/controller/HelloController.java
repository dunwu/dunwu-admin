package io.github.dunwu.modules.demo.controller;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.dto.HelloDto;
import io.github.dunwu.modules.demo.entity.query.HelloQuery;
import io.github.dunwu.modules.demo.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 *  Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-05
 */
@RestController
@RequestMapping("/demo/hello")
@Api(tags = " Controller 类")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService service;


    /** 添加一条 {@link Hello} 记录 */
    @ApiOperation("添加一条 Hello 记录")
    @PostMapping("add")
    public BaseResult add(@Validated(AddCheck.class) @RequestBody Hello entity) {
        service.save(entity);
        return BaseResult.ok();
    }

    /** 批量添加 {@link Hello} 记录 */
    @ApiOperation("批量添加 Hello 记录")
    @PostMapping("add/batch")
    public BaseResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<Hello> list) {
        service.saveBatch(list);
        return BaseResult.ok();
    }

    /** 更新一条 {@link Hello} 记录 */
    @ApiOperation("更新一条 Hello 记录")
    @PostMapping("edit")
    public BaseResult edit(@Validated(EditCheck.class) @RequestBody Hello entity) {
        service.updateById(entity);
        return BaseResult.ok();
    }

    /** 批量更新 {@link Hello} 记录 */
    @ApiOperation("批量更新 Hello 记录")
    @PostMapping("edit/batch")
    public BaseResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<Hello> list) {
        service.updateBatchById(list);
        return BaseResult.ok();
    }

    @ApiOperation("删除一条 Hello 记录")
    @PostMapping("del/{id}")
    public BaseResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return BaseResult.ok();
    }

    @ApiOperation("根据 ID 集合批量删除 Hello 记录")
    @PostMapping("del/batch")
    public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return BaseResult.ok();
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 HelloDto 列表")
    @GetMapping("list")
    public DataListResult<HelloDto> list(HelloQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 query 和 pageable 条件，分页查询 HelloDto 记录")
    @GetMapping("page")
    public PageResult<HelloDto> page(HelloQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult<Integer> count(HelloQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @GetMapping("{id}")
    @ApiOperation("根据 ID 查询 HelloDto 记录")
    public DataResult<HelloDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @GetMapping("export")
    @ApiOperation("根据 ID 集合批量导出 HelloDto 列表数据")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 HelloDto 列表数据")
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
