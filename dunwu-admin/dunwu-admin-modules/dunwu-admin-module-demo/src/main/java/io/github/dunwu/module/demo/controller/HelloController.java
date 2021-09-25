package io.github.dunwu.module.demo.controller;

import io.github.dunwu.module.demo.entity.Hello;
import io.github.dunwu.module.demo.entity.query.HelloQuery;
import io.github.dunwu.module.demo.service.HelloService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-22
 */
@RestController
@RequestMapping("/demo/hello")
@Api(tags = "测试 Controller 类")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService service;

    @ApiOperation("添加一条 Hello 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody Hello entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Hello 记录")
    @PostMapping("add/batch")
    public DataResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<Hello> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 ID 更新一条 Hello 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody Hello entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 ID 批量更新 Hello 记录")
    @PostMapping("edit/batch")
    public DataResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<Hello> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 ID 删除一条 Hello 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 ID 列表批量删除 Hello 记录")
    @PostMapping("del/batch")
    public DataResult deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 HelloQuery 查询 HelloDto 列表")
    @GetMapping("list")
    public DataResult list(HelloQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 HelloQuery 和 Pageable 分页查询 HelloDto 列表")
    @GetMapping("page")
    public DataResult page(HelloQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 HelloDto")
    @GetMapping("{id}")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 HelloQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult count(HelloQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 HelloDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 HelloQuery 和 Pageable 分页查询 HelloDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(query, pageable, response);
    }

}
