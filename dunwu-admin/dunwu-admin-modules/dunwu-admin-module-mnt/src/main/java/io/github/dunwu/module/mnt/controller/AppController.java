package io.github.dunwu.module.mnt.controller;

import io.github.dunwu.module.mnt.entity.App;
import io.github.dunwu.module.mnt.entity.dto.AppDto;
import io.github.dunwu.module.mnt.entity.query.AppQuery;
import io.github.dunwu.module.mnt.service.AppService;
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
 * 应用配置 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@RestController
@RequestMapping("/mnt/app")
@Api(tags = "应用配置 Controller 类")
@RequiredArgsConstructor
public class AppController {

    private final AppService service;

    @ApiOperation("添加一条 App 记录")
    @PreAuthorize("@exp.check('mnt:app:add')")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody App entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 App 记录")
    @PreAuthorize("@exp.check('mnt:app:add')")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<App> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 App 记录")
    @PreAuthorize("@exp.check('mnt:app:edit')")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody App entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 App 记录")
    @PreAuthorize("@exp.check('mnt:app:edit')")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<App> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 App 记录")
    @PreAuthorize("@exp.check('mnt:app:del')")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 App 记录")
    @PreAuthorize("@exp.check('mnt:app:del')")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 AppQuery 查询 AppDto 列表")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @GetMapping("list")
    public DataListResult<AppDto> list(AppQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 AppQuery 和 Pageable 分页查询 AppDto 列表")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @GetMapping("page")
    public PageResult<AppDto> page(AppQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 AppDto")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @GetMapping("{id}")
    public DataResult<AppDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 AppQuery 查询匹配条件的记录数")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @GetMapping("count")
    public DataResult<Integer> count(AppQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 AppDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 AppQuery 和 Pageable 分页查询 AppDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('mnt:app:view')")
    @GetMapping("export/page")
    public void exportPage(AppQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
