package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.query.MenuQuery;
import io.github.dunwu.module.cas.entity.vo.MenuVo;
import io.github.dunwu.module.cas.service.MenuService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 菜单表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/cas/menu")
@Api(tags = "菜单表 Controller 类")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @ApiOperation("添加一条 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "添加", value = "'向 cas_menu 表中添加一条记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('cas:menu:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Menu entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "批量添加", value = "'向 cas_menu 表中批量添加 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('cas:menu:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Menu> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "更新", value = "'更新 cas_menu 表中 id = ' + #entity.id + ' 的记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('cas:menu:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Menu entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "批量更新", value = "'批量更新 cas_menu 表中 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('cas:menu:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Menu> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "删除", value = "'删除 cas_menu 表中 id = ' + #entity.id + ' 的记录'")
    @PreAuthorize("@exp.check('cas:menu:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Menu 记录")
    @AppLog(bizType = "菜单表", operType = "批量删除", value = "'批量删除 cas_menu 表中 id = ' + #ids + ' 的记录'")
    @PreAuthorize("@exp.check('cas:menu:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 MenuQuery 查询 MenuDto 列表")
    @PreAuthorize("@exp.check('cas:menu:view')")
    @GetMapping("/list")
    public DataListResult<MenuDto> list(MenuQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 MenuQuery 分页查询 MenuDto 列表")
    @PreAuthorize("@exp.check('cas:menu:view')")
    @GetMapping("/page")
    public PageResult<MenuDto> page(Pageable pageable, MenuQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 MenuDto")
    @PreAuthorize("@exp.check('cas:menu:view')")
    @GetMapping("/{id}")
    public DataResult<MenuDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 MenuQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(MenuQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 MenuDto 列表，并导出 excel 表单")
    @AppLog(bizType = "菜单表", operType = "导出", value = "'导出 cas_menu 表中 id = ' + #ids + ' 的记录'")
    @PreAuthorize("@exp.check('cas:menu:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 MenuQuery 分页查询 MenuDto 列表，并导出 excel 表单")
    @AppLog(bizType = "菜单表", operType = "导出", value = "分页导出 cas_menu 表中的记录")
    @PreAuthorize("@exp.check('cas:menu:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, MenuQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @PreAuthorize("@exp.check('cas:menu:view')")
    @ApiOperation("根据 query 条件，返回 SysMenuDto 树形列表")
    @GetMapping("treeList")
    public DataListResult<MenuDto> treeList(MenuQuery query) {
        return DataListResult.ok(service.treeList(query));
    }

    @PreAuthorize("@exp.check('cas:menu:view')")
    @ApiOperation("根据ID获取同级与上级数据")
    @PostMapping("superiorTreeList")
    public DataListResult<MenuDto> superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return DataListResult.ok(service.treeListByIds(ids));
    }

    @PreAuthorize("@exp.check('cas:menu:view')")
    @ApiOperation("根据ID获取所有孩子节点ID")
    @GetMapping("childrenIds")
    public DataListResult<Long> childrenIds(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        ids.addAll(service.childrenIds(id));
        return DataListResult.ok(ids);
    }

    @ApiOperation("获取当前用户展示于前端的菜单列表")
    @GetMapping(value = "mine")
    public DataListResult<MenuVo> mineList() {
        return DataListResult.ok(service.buildMenuListForCurrentUser());
    }

}
