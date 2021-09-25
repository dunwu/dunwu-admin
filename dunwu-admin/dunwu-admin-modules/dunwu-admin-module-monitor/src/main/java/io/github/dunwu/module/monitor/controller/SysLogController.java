package io.github.dunwu.module.monitor.controller;

import io.github.dunwu.module.monitor.annotation.AppLog;
import io.github.dunwu.module.monitor.entity.SysLog;
import io.github.dunwu.module.monitor.entity.dto.SysLogDto;
import io.github.dunwu.module.monitor.entity.query.SysLogQuery;
import io.github.dunwu.module.monitor.service.SysLogService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/monitor/log")
@Api(tags = "系统：日志管理")
@RequiredArgsConstructor
public class SysLogController {

    private final SysLogService service;

    @PreAuthorize("@exp.check()")
    @ApiOperation("添加一条 SysLog 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody SysLog entity) {
        service.save(entity);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("批量添加 SysLog 记录")
    @PostMapping("add/batch")
    public DataResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<SysLog> list) {
        service.saveBatch(list);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 更新一条 SysLog 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody SysLog entity) {
        service.updateById(entity);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 批量更新 SysLog 记录")
    @PostMapping("edit/batch")
    public DataResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<SysLog> list) {
        service.updateBatchById(list);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 删除一条 SysLog 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return DataResult.ok();
    }

    @AppLog("批量删除数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 列表批量删除 SysLog 记录")
    @PostMapping("del/batch")
    public DataResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return DataResult.ok();
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 查询 SysLogDto 列表")
    @GetMapping("list")
    public DataResult list(SysLogQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 和 Pageable 分页查询 SysLogDto 列表")
    @GetMapping("page")
    public DataResult page(SysLogQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check()")
    @GetMapping("{id}")
    @ApiOperation("根据 id 查询 SysLogDto")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult count(SysLogQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 id 列表查询 SysLogDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @AppLog("导出 SysLog 数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 SysLogQuery 和 Pageable 分页查询 SysLogDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(SysLogQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @AppLog("清空数据")
    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PostMapping("clear")
    public DataResult clear() {
        List<SysLogDto> list = service.pojoListByQuery(new SysLogQuery());
        Set<Long> ids = list.stream().map(SysLogDto::getId).collect(Collectors.toSet());
        service.removeByIds(ids);
        return DataResult.ok();
    }

}
