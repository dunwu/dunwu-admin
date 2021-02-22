package io.github.dunwu.modules.system.controller;

import cn.hutool.core.lang.Dict;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.log.annotation.Log;
import io.github.dunwu.modules.system.entity.SysRole;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.query.SysRoleQuery;
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
 * 系统角色信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("/api/sys/role")
@Api(tags = "SysRoleController")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService service;

    @PostMapping
    @Log("创建一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:add')")
    @ApiOperation("创建一条 SysRole 记录")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysRole entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("更新一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("更新一条 SysRole 记录")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysRole entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @Log("删除一条 SysRole 记录")
    @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("删除一条 SysRole 记录")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    @Log("根据 ID 集合批量删除 SysRole 记录")
    @PreAuthorize("@exp.check('role:del')")
    @ApiOperation("根据 ID 集合批量删除 SysRole 记录")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("查询 SysRoleDto 记录")
    public ResponseEntity<Object> view(SysRoleQuery query, Pageable pageable) {
        return page(query, pageable);
    }

    @GetMapping("page")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysRoleDto 记录")
    public ResponseEntity<Object> page(SysRoleQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 查询 SysRole 记录")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @GetMapping("count")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    public ResponseEntity<Object> count(SysRoleQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("list")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysRoleDto 列表")
    public ResponseEntity<Object> list(SysRoleQuery query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @GetMapping("export/list")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 ID 集合批量导出 SysRoleDto 列表数据")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @GetMapping("export")
    @PreAuthorize("@exp.check('role:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysRoleDto 列表数据")
    public void exportPageData(SysRoleQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

    @PutMapping(value = "menu")
    @PreAuthorize("@exp.check('role:edit')")
    @ApiOperation("修改角色菜单")
    public ResponseEntity<Object> saveMenus(@RequestBody SysRoleDto pojo) {
        return new ResponseEntity<>(service.updateMenusByRoleId(pojo.getId(), pojo.getMenus()), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "level")
    @ApiOperation("获取用户级别")
    public ResponseEntity<Object> getLevel() {
        return new ResponseEntity<>(Dict.create().set("level", service.getRoleLevel()), HttpStatus.OK);
    }

}
