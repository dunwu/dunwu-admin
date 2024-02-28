package io.github.dunwu.module.sys.controller;

import io.github.dunwu.module.sys.entity.OperationLog;
import io.github.dunwu.module.sys.entity.dto.OperationLogDto;
import io.github.dunwu.module.sys.entity.query.OperationLogQuery;
import io.github.dunwu.module.sys.service.OperationLogService;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@RestController
@RequestMapping("/sys/log")
@Api(tags = "【系统】操作日志")
@RequiredArgsConstructor
public class OperationLogController {

    private final OperationLogService service;

    @ApiOperation("添加一条 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody OperationLog entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<OperationLog> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody OperationLog entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<OperationLog> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 OperationLog 记录")
    @PreAuthorize("@exp.check('sys:log:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 OperationLogQuery 查询 OperationLogDto 列表")
    @PreAuthorize("@exp.check('sys:log:view')")
    @GetMapping("/list")
    public DataListResult<OperationLogDto> list(OperationLogQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 OperationLogQuery 分页查询 OperationLogDto 列表")
    @PreAuthorize("@exp.check('sys:log:view')")
    @GetMapping("/page")
    public PageResult<OperationLogDto> page(Pageable pageable, OperationLogQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 OperationLogDto")
    @PreAuthorize("@exp.check('sys:log:view')")
    @GetMapping("/{id}")
    public DataResult<OperationLogDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 OperationLogQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(OperationLogQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 OperationLogDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:log:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 OperationLogQuery 分页查询 OperationLogDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:log:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, OperationLogQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
