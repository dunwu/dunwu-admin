package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.monitor.dao.LogRecordDao;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.entity.dto.LogDto;
import io.github.dunwu.modules.monitor.entity.query.LogQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志记录 Controller
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@RestController
@RequestMapping("monitor/log")
@Api(tags = "系统：日志管理")
@RequiredArgsConstructor
public class LogController {

    private final LogRecordDao dao;

    @Log("清空数据")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @PostMapping("clear")
    public BaseResult clear() {
        List<LogRecord> list = dao.list();
        Set<Long> ids = list.stream().map(LogRecord::getId).collect(Collectors.toSet());
        dao.removeByIds(ids);
        return BaseResult.ok();
    }

    @Log("批量删除数据")
    @ApiOperation("根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @PostMapping("del/batch")
    public BaseResult deleteByIds(@RequestBody Set<String> ids) {
        dao.removeByIds(ids);
        return BaseResult.ok();
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysJobDto 列表")
    @GetMapping("list")
    public DataListResult<LogRecord> list(LogQuery query) {
        return DataListResult.ok(dao.listByQuery(query));
    }

    @PreAuthorize("@exp.check()")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysLog 记录")
    @GetMapping("page")
    public PageResult<LogRecord> page(LogQuery query, Pageable pageable) {
        return PageResult.ok(dao.pageByQuery(query, pageable));
    }

    @ApiOperation("根据 entity 条件，查询 SysLog 总记录数")
    @PreAuthorize("@exp.check()")
    @GetMapping("count")
    public DataResult<Integer> count(LogQuery query) {
        return DataResult.ok(dao.countByQuery(query));
    }

    @ApiOperation("根据 ID 查询 SysLog 记录")
    @PreAuthorize("@exp.check()")
    @GetMapping("{id}")
    public DataResult<LogRecord> getById(@PathVariable String id) {
        return DataResult.ok(dao.getById(id));
    }

    @Log("导出 SysLog 数据")
    @ApiOperation("导出 SysLog 数据")
    @PreAuthorize("@exp.check()")
    @GetMapping("export/page")
    public void exportData(HttpServletResponse response, LogQuery query, Pageable pageable) throws IOException {
        Page<LogDto> pageResult = dao.pojoPageByQuery(query, pageable, LogDto.class);
        dao.exportData(pageResult.getContent(), response);
    }

}
