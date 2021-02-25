package io.github.dunwu.modules.system.controller;

import cn.hutool.core.lang.Dict;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.SysRole;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.query.SysRoleQuery;
import io.github.dunwu.modules.system.service.SysRoleService;
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
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/role")
@Api(tags = "系统：角色管理")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService service;

    @Log("添加一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:add')")
    @ApiOperation("添加一条 SysRole 记录")
    @PostMapping("add")
    public BaseResult add(@Validated(AddCheck.class) @RequestBody SysRole entity) {
        service.save(entity);
        return BaseResult.ok();
    }

    @Log("更新一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("更新一条 SysRole 记录")
    @PostMapping("edit")
    public BaseResult edit(@Validated(EditCheck.class) @RequestBody SysRole entity) {
        service.updateById(entity);
        return BaseResult.ok();
    }

    @Log("删除一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("删除一条 SysRole 记录")
    @PostMapping("del/{id}")
    public BaseResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return BaseResult.ok();
    }

    @Log("根据 ID 集合批量删除 SysRole 记录")
    @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("根据 ID 集合批量删除 SysRole 记录")
    @PostMapping("del")
    public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return BaseResult.ok();
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysRoleDto 列表")
    @GetMapping("list")
    public DataListResult<SysRoleDto> list(SysRoleQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysRoleDto 记录")
    @GetMapping("page")
    public PageResult<SysRoleDto> page(SysRoleQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoPageByQuery(query, pageable));
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public DataResult<Integer> count(SysRoleQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 查询 SysRoleDto 记录")
    @GetMapping("{id}")
    public DataResult<SysRoleDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysRoleDto 列表数据")
    @GetMapping("export/page")
    public void exportPageData(SysRoleQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 集合批量导出 SysRoleDto 列表数据")
    @GetMapping("export/list")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @PutMapping(value = "menu")
    @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("修改角色菜单")
    public BaseResult saveMenus(@RequestBody SysRoleDto pojo) {
        service.updateMenusByRoleId(pojo.getId(), pojo.getMenus());
        return BaseResult.ok();
    }

    @GetMapping(value = "level")
    @ApiOperation("获取用户级别")
    public DataResult<Dict> getLevel() {
        return DataResult.ok(Dict.create().set("level", service.getRoleLevel()));
    }

}
