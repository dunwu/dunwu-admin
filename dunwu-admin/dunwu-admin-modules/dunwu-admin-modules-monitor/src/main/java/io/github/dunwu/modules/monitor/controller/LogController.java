package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.monitor.dao.LogRecordDao;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.entity.dto.LogDto;
import io.github.dunwu.modules.monitor.entity.query.LogQuery;
import io.github.dunwu.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/monitor/log")
@Api(tags = "系统：日志管理")
@RequiredArgsConstructor
public class LogController {

    private final LogRecordDao dao;

    @Log("清空数据")
    @DeleteMapping("clear")
    @ApiOperation(value = "根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    public ResponseEntity<Object> clear() {
        List<LogRecord> list = dao.list();
        Set<Long> ids = list.stream().map(LogRecord::getId).collect(Collectors.toSet());
        return new ResponseEntity<>(dao.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @Log("批量删除数据")
    @DeleteMapping
    @ApiOperation(value = "根据 ID 批量删除 SysLog 记录")
    @PreAuthorize("@exp.check()")
    public ResponseEntity<Object> delete(@RequestBody Set<String> ids) {
        return new ResponseEntity<>(dao.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据 ID 查询 SysLog 记录")
    @PreAuthorize("@exp.check()")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(dao.getById(id), HttpStatus.OK);
    }

    @GetMapping("count")
    @ApiOperation(value = "根据 entity 条件，查询 SysLog 总记录数")
    @PreAuthorize("@exp.check()")
    public ResponseEntity<Object> count(LogQuery query) {
        return new ResponseEntity<>(dao.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("page")
    @PreAuthorize("@exp.check()")
    @ApiOperation(value = "根据 query 和 pageable 条件，分页查询 SysLog 记录")
    public ResponseEntity<Object> page(LogQuery query, Pageable pageable) {
        Page<LogDto> page = dao.pojoPageByQuery(query, pageable, LogDto.class);
        return new ResponseEntity<>(PageUtil.toPage(page), HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "根据 query 和 pageable 条件，分页查询 SysLog 记录")
    @PreAuthorize("@exp.check()")
    public ResponseEntity<Object> view(LogQuery query, Pageable pageable) {
        return new ResponseEntity<>(dao.pojoPageByQuery(query, pageable, LogDto.class), HttpStatus.OK);
    }

    @Log("导出 SysLog 数据")
    @ApiOperation("导出 SysLog 数据")
    @GetMapping(value = "export")
    @PreAuthorize("@exp.check()")
    public void exportData(HttpServletResponse response, LogQuery query, Pageable pageable) throws IOException {
        Page<LogDto> pageResult = dao.pojoPageByQuery(query, pageable, LogDto.class);
        dao.exportData(pageResult.getContent(), response);
    }

}
