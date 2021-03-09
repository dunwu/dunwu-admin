package io.github.dunwu.modules.demo.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.query.HelloQuery;
import io.github.dunwu.modules.demo.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/demo/hello")
@Api(tags = "测试 Controller 类")
@RequiredArgsConstructor
public class HelloController {

    private final HelloService service;

    @ApiOperation("添加一条 Hello 记录")
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody Hello entity) {
        service.save(entity);
        return Result.ok();
    }

    @ApiOperation("批量添加 Hello 记录")
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<Hello> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    @ApiOperation("更新一条 Hello 记录")
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody Hello entity) {
        service.updateById(entity);
        return Result.ok();
    }

    @ApiOperation("批量更新 Hello 记录")
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<Hello> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    @ApiOperation("删除一条 Hello 记录")
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据 ID 集合批量删除 Hello 记录")
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("根据 query 条件，查询匹配条件的 HelloDto 列表")
    @GetMapping("list")
    public Result list(HelloQuery query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 query 和 pageable 条件，分页查询匹配条件的 HelloDto 记录")
    @GetMapping("page")
    public Result page(HelloQuery query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public Result count(HelloQuery query) {
        return Result.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 ID 查询 HelloDto 记录")
    @GetMapping("{id}")
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @ApiOperation("根据 ID 集合批量导出 HelloDto 列表数据")
    @GetMapping("export/list")
    public void exportList(String ids, HttpServletResponse response)
        throws IOException {
        JSONArray objects = JSONUtil.parseArray(ids);
        List<Long> idList = objects.toList(Long.class);
        service.exportList(idList, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 HelloDto 列表数据")
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
