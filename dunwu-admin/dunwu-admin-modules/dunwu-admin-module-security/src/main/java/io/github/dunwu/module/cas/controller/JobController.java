package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.module.cas.service.JobService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.github.dunwu.tool.web.log.annotation.AppLog;
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
 * 系统岗位信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("cas/job")
@Api(tags = "系统：岗位管理")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final RoleService roleService;

    @AppLog("添加一条 SysJobDto 记录")
    @ApiOperation("添加一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:add')")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.insert(entity));
    }

    @AppLog("批量添加 Job 记录")
    @ApiOperation("批量添加 Job 记录")
    @PreAuthorize("@exp.check('job:add')")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.insertBatch(list));
    }

    @AppLog("更新一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJobDto 记录")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Job 记录")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.updateBatchById(list));
    }

    @AppLog("根据 ID 删除一条 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("根据 ID 删除一条 SysJobDto 记录")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.deleteById(id));
    }

    @AppLog("根据 ID 集合批量删除 SysJobDto 记录")
    @PreAuthorize("@exp.check('job:del')")
    @ApiOperation("根据 ID 集合批量删除 SysJobDto 记录")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(jobService.deleteBatchByIds(ids));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysJobDto 列表")
    @GetMapping("list")
    public DataListResult<JobDto> list(JobQuery query) {
        return DataListResult.ok(jobService.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysJobDto 记录")
    @GetMapping("page")
    public PageResult<JobDto> page(JobQuery query, Pageable pageable) {
        return PageResult.ok(jobService.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 JobDto")
    @GetMapping("{id}")
    public DataResult<JobDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.pojoById(id));
    }

    @ApiOperation("根据 JobQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(JobQuery query) {
        return DataResult.ok(jobService.countByQuery(query));
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 id 列表查询 JobDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        jobService.exportList(ids, response);
    }

    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 JobQuery 和 Pageable 分页查询 JobDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(JobQuery query, Pageable pageable, HttpServletResponse response) {
        jobService.exportPage(query, pageable, response);
    }

    @GetMapping("roles/{jobId}")
    @PreAuthorize("@exp.check('job:view')")
    @ApiOperation("根据 Job ID 查询角色列表")
    public DataListResult<RoleDto> rolesByJobId(@PathVariable Long jobId) {
        return DataListResult.ok(roleService.pojoListByJobId(jobId));
    }

    @PutMapping("roles/{jobId}")
    @AppLog("更新岗位/角色记录")
    @PreAuthorize("@exp.check('job:edit')")
    @ApiOperation("更新一条 SysJobDto 记录")
    public DataResult<Boolean> updateRolesByJobId(@PathVariable Long jobId, @RequestBody Collection<Long> roleIds) {
        return DataResult.ok(roleService.updateRolesByJobId(jobId, roleIds));
    }

}
