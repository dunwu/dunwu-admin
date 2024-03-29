package io.github.dunwu.module.cas.controller;

import cn.hutool.core.lang.Dict;
import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.RoleQuery;
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
 * 角色 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/cas/role")
@Api(tags = "【权限】角色管理")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @ApiOperation("添加一条 Role 记录")
    @PreAuthorize("@exp.check('cas:role:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Role entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Role 记录")
    @PreAuthorize("@exp.check('cas:role:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Role> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Role 记录")
    @PreAuthorize("@exp.check('cas:role:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Role entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Role 记录")
    @PreAuthorize("@exp.check('cas:role:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Role> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Role 记录")
    @PreAuthorize("@exp.check('cas:role:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Role 记录")
    @PreAuthorize("@exp.check('cas:role:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 RoleQuery 查询 RoleDto 列表")
    @PreAuthorize("@exp.check('cas:role:view')")
    @GetMapping("/list")
    public DataListResult<RoleDto> list(RoleQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 RoleQuery 分页查询 RoleDto 列表")
    @PreAuthorize("@exp.check('cas:role:view')")
    @GetMapping("/page")
    public PageResult<RoleDto> page(Pageable pageable, RoleQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 RoleDto")
    @PreAuthorize("@exp.check('cas:role:view')")
    @GetMapping("/{id}")
    public DataResult<RoleDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 RoleQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(RoleQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 RoleDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:role:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 RoleQuery 分页查询 RoleDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:role:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, RoleQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @PutMapping(value = "menu")
    @ApiOperation("修改角色菜单")
    public DataResult<Boolean> saveMenus(@RequestBody RoleDto pojo) {
        return DataResult.ok(service.updateMenusByRoleId(pojo.getId(), pojo.getMenus()));
    }

    @GetMapping(value = "level")
    @ApiOperation("获取用户级别")
    public DataResult<Dict> getLevel() {
        return DataResult.ok(Dict.create().set("level", service.getRoleLevel()));
    }

}
