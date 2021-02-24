package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.query.SysJobQuery;
import io.github.dunwu.modules.system.service.SysJobService;
import io.github.dunwu.modules.system.service.SysRoleService;
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
 * 系统岗位信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/job")
@Api(tags = "系统：岗位管理")
@RequiredArgsConstructor
public class SysJobController {

    private final SysJobService jobService;
    private final SysRoleService roleService;

    @Log("添加一条 SysJob 记录")
    @PreAuthorize("@exp.check('job:add')")
    @ApiOperation("添加一条 SysJob 记录")
    @PostMapping("add")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysJob entity) {
        return new ResponseEntity<>(jobService.save(entity), HttpStatus.CREATED);
    }

    @Log("更新一条 SysJob 记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJob 记录")
    @PostMapping("edit")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysJob entity) {
        return new ResponseEntity<>(jobService.updateById(entity), HttpStatus.ACCEPTED);
    }

    @Log("删除一条 SysJob 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("删除一条 SysJob 记录")
    @PostMapping("del/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(jobService.removeById(id), HttpStatus.ACCEPTED);
    }

    @Log("根据 ID 集合批量删除 SysJob 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("根据 ID 集合批量删除 SysJob 记录")
    @PostMapping("del")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(jobService.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysJobDto 列表")
    @GetMapping("list")
    public ResponseEntity<Object> list(SysJobQuery query) {
        return new ResponseEntity<>(jobService.pojoListByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysJobDto 记录")
    @GetMapping("page")
    public ResponseEntity<Object> page(SysJobQuery query, Pageable pageable) {
        return new ResponseEntity<>(jobService.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public ResponseEntity<Object> count(SysJobQuery query) {
        return new ResponseEntity<>(jobService.countByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 ID 查询 SysJob 记录")
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(jobService.pojoById(id), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysJobDto 列表数据")
    @GetMapping("export/page")
    public void exportPageData(SysJobQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        jobService.exportPageData(query, pageable, response);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 ID 集合批量导出 SysJobDto 列表数据")
    @GetMapping("export/list")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        jobService.exportByIds(ids, response);
    }

    @GetMapping("roles/{jobId}")
    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 Job ID 查询角色列表")
    public ResponseEntity<Object> rolesByJobId(@PathVariable Long jobId) {
        return new ResponseEntity<>(roleService.pojoListByJobId(jobId), HttpStatus.OK);
    }

    @PutMapping("roles/{jobId}")
    @Log("更新岗位/角色记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJob 记录")
    public ResponseEntity<Object> updateRolesByJobId(@PathVariable Long jobId, @RequestBody Collection<Long> roleIds) {
        return new ResponseEntity<>(roleService.updateRolesByJobId(jobId, roleIds), HttpStatus.OK);
    }

}
