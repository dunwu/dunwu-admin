package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.module.cas.service.JobService;
import io.github.dunwu.module.cas.service.RoleService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 职务 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/cas/job")
@Api(tags = "【权限】职务管理")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final RoleService roleService;

    @ApiOperation("添加一条 Job 记录")
    @PreAuthorize("@exp.check('cas:job:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.insert(entity));
    }

    @ApiOperation("批量添加 Job 记录")
    @PreAuthorize("@exp.check('cas:job:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Job 记录")
    @PreAuthorize("@exp.check('cas:job:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Job entity) {
        return DataResult.ok(jobService.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Job 记录")
    @PreAuthorize("@exp.check('cas:job:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Job> list) {
        return DataResult.ok(jobService.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Job 记录")
    @PreAuthorize("@exp.check('cas:job:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Job 记录")
    @PreAuthorize("@exp.check('cas:job:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(jobService.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 JobQuery 查询 JobDto 列表")
    @PreAuthorize("@exp.check('cas:job:view')")
    @GetMapping("/list")
    public DataListResult<JobDto> list(JobQuery query) {
        return DataListResult.ok(jobService.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 JobQuery 分页查询 JobDto 列表")
    @PreAuthorize("@exp.check('cas:job:view')")
    @GetMapping("/page")
    public PageResult<JobDto> page(Pageable pageable, JobQuery query) {
        return PageResult.ok(jobService.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 JobDto")
    @PreAuthorize("@exp.check('cas:job:view')")
    @GetMapping("/{id}")
    public DataResult<JobDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(jobService.pojoById(id));
    }

    @ApiOperation("根据 JobQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(JobQuery query) {
        return DataResult.ok(jobService.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 JobDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:job:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        jobService.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 JobQuery 分页查询 JobDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:job:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, JobQuery query, HttpServletResponse response) {
        jobService.exportPage(pageable, query, response);
    }

    @GetMapping("roles/{jobId}")
    @PreAuthorize("@exp.check('cas:job:view')")
    @ApiOperation("根据 Job ID 查询角色列表")
    public DataListResult<RoleDto> rolesByJobId(@PathVariable Long jobId) {
        return DataListResult.ok(roleService.pojoListByJobId(jobId));
    }

    @PutMapping("roles/{jobId}")
    @PreAuthorize("@exp.check('cas:job:edit')")
    @ApiOperation("更新一条 SysJobDto 记录")
    public DataResult<Boolean> updateRolesByJobId(@PathVariable Long jobId, @RequestBody Collection<Long> roleIds) {
        return DataResult.ok(roleService.updateRolesByJobId(jobId, roleIds));
    }

    @ApiOperation("绑定用户到指定部门")
    @PreAuthorize("@exp.check('cas:job:edit')")
    @PostMapping("bindDept/{deptId}")
    public DataResult<Boolean> bindDept(@PathVariable Long deptId, @RequestBody Collection<Long> jobIds) {
        return DataResult.ok(jobService.bindDept(deptId, jobIds));
    }

    @ApiOperation("从指定部门解绑定用户")
    @PreAuthorize("@exp.check('cas:job:edit')")
    @PostMapping("unbindDept/{deptId}")
    public DataResult<Boolean> unbindDept(@PathVariable Long deptId, @RequestBody Collection<Long> jobIds) {
        return DataResult.ok(jobService.unbindDept(deptId, jobIds));
    }

}
