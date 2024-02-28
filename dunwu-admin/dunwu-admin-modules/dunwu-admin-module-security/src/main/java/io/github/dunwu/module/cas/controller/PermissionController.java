package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.module.cas.entity.dto.PermissionDto;
import io.github.dunwu.module.cas.entity.query.PermissionQuery;
import io.github.dunwu.module.cas.service.PermissionService;
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
 * 权限 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/cas/perm")
@Api(tags = "【权限】权限资源管理")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @ApiOperation("添加一条 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Permission entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Permission> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Permission entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Permission> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Permission 记录")
    @PreAuthorize("@exp.check('cas:perm:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 PermissionQuery 查询 PermissionDto 列表")
    @PreAuthorize("@exp.check('cas:perm:view')")
    @GetMapping("/list")
    public DataListResult<PermissionDto> list(PermissionQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 PermissionQuery 分页查询 PermissionDto 列表")
    @PreAuthorize("@exp.check('cas:perm:view')")
    @GetMapping("/page")
    public PageResult<PermissionDto> page(Pageable pageable, PermissionQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 PermissionDto")
    @PreAuthorize("@exp.check('cas:perm:view')")
    @GetMapping("/{id}")
    public DataResult<PermissionDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 PermissionQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(PermissionQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 PermissionDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:perm:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 PermissionQuery 分页查询 PermissionDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:perm:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, PermissionQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
