package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.query.SysMenuQuery;
import io.github.dunwu.modules.system.service.SysMenuService;
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
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/menu")
@Api(tags = "系统：菜单管理")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService service;

    @Log("创建一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:add')")
    @ApiOperation("创建一条 SysMenu 记录")
    @PostMapping("add")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysMenu entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @Log("更新一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:edit')")
    @ApiOperation("更新一条 SysMenu 记录")
    @PostMapping("edit")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysMenu entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @Log("删除一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("删除一条 SysMenu 记录")
    @PostMapping("del/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @Log("根据 ID 集合批量删除 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("根据 ID 集合批量删除 SysMenu 记录")
    @PostMapping("del")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysMenuDto 列表")
    @GetMapping("list")
    public ResponseEntity<Object> list(SysMenuQuery query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysMenuDto 记录")
    @GetMapping("page")
    public ResponseEntity<Object> page(SysMenuQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public ResponseEntity<Object> count(SysMenuQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 查询 SysMenu 记录")
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysMenuDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysMenuQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 集合批量导出 SysMenuDto 列表数据")
    @GetMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，返回 SysMenuDto 树形列表")
    @GetMapping("treeList")
    public ResponseEntity<Object> treeList(SysMenuQuery query) {
        return new ResponseEntity<>(service.treeList(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据ID获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public ResponseEntity<Object> superiorTreeList(@RequestBody Collection<Serializable> ids) {
        Collection<SysMenuDto> treeList = new ArrayList<>();
        for (Serializable id : ids) {
            SysMenuDto entity = service.pojoById(id);
            if (entity == null) {
                continue;
            }

            if (entity.getPid() != null) {
                // 获取上级菜单
                SysMenuDto parent = service.pojoById(entity.getPid());
                treeList.add(parent);

                // 获取所有同级菜单
                SysMenuQuery query = new SysMenuQuery();
                query.setPid(entity.getPid());
                Collection<SysMenuDto> list = service.pojoListByQuery(query);
                treeList.addAll(list);
            }
        }

        Map<String, Object> stringObjectMap = new HashMap<>(service.buildTreeList(treeList));
        return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据ID获取所有孩子节点ID")
    @PostMapping("childrenIds")
    public ResponseEntity<Object> childrenIds(@RequestBody Long id) {
        List<Long> ids = service.childrenIds(id);
        ids.add(id);
        return new ResponseEntity<>(service.childrenIds(id), HttpStatus.OK);
    }

    @ApiOperation("获取当前用户展示于前端的菜单列表")
    @GetMapping(value = "mine")
    public ResponseEntity<Object> mineList() {
        return new ResponseEntity<>(service.buildMenuListForCurrentUser(), HttpStatus.OK);
    }

}
