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
@RequestMapping("api/sys/menu")
@Api(tags = "SysMenuController")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService service;

    @PostMapping
    @Log("创建一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:add')")
    @ApiOperation("创建一条 SysMenu 记录")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysMenu entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("更新一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:edit')")
    @ApiOperation("更新一条 SysMenu 记录")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysMenu entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    @Log("删除一条 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("删除一条 SysMenu 记录")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    @Log("根据 ID 集合批量删除 SysMenu 记录")
    @PreAuthorize("@exp.check('menu:del')")
    @ApiOperation("根据 ID 集合批量删除 SysMenu 记录")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @GetMapping
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("查询 SysMenuDto 记录")
    public ResponseEntity<Object> view(SysMenuQuery query) {
        return new ResponseEntity<>(service.treeListMap(query), HttpStatus.OK);
    }

    @GetMapping("page")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysMenuDto 记录")
    public ResponseEntity<Object> page(SysMenuQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 查询 SysMenu 记录")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @GetMapping("count")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    public ResponseEntity<Object> count(SysMenuQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @GetMapping("list")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysMenuDto 列表")
    public ResponseEntity<Object> list(SysMenuQuery query) {
        List<SysMenuDto> list = service.pojoListByQuery(query);
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", list);
        map.put("totalElements", list.size());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("export/list")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 ID 集合批量导出 SysMenuDto 列表数据")
    public void exportByIds(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @GetMapping("export")
    @PreAuthorize("@exp.check('menu:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysMenuDto 列表数据")
    public void exportPageData(SysMenuQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

    @GetMapping(value = "tree")
    @PreAuthorize("@exp.check('menu:view','role:view')")
    @ApiOperation("返回全部的菜单")
    public ResponseEntity<Object> treeList() {
        return new ResponseEntity<>(service.treeObject(), HttpStatus.OK);
    }

    @ApiOperation("获取当前用户展示于前端的菜单列表")
    @GetMapping(value = "build")
    public ResponseEntity<Object> menuListForCurrentUser() {
        return new ResponseEntity<>(service.buildMenuListForCurrentUser(), HttpStatus.OK);
    }

    @ApiOperation("查询部门:根据ID获取同级与上级数据")
    @PostMapping("superior")
    @PreAuthorize("@exp.check('dept:list')")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Collection<SysMenuDto> menus = new ArrayList<>();
        for (Long id : ids) {
            SysMenuDto menuDto = service.pojoById(id);
            if (menuDto != null && menuDto.getPid() != null) {
                // 获取上级菜单
                SysMenuDto parentDept = service.pojoById(menuDto.getPid());
                menus.add(parentDept);

                // 获取所有同级菜单
                SysMenuQuery query = new SysMenuQuery();
                query.setPid(menuDto.getPid());
                Collection<SysMenuDto> list = service.pojoListByQuery(query);
                menus.addAll(list);
            }
        }
        Map<String, Object> stringObjectMap = new HashMap<>(service.buildTreeList(menus));
        return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);
    }

}
