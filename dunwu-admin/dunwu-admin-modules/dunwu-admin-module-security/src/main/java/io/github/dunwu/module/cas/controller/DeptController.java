package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
import io.github.dunwu.module.cas.service.DeptService;
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
 * 部门 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@RestController
@RequestMapping("/cas/dept")
@Api(tags = "部门 Controller 类")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService service;

    @ApiOperation("添加一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "添加", value = "'向 cas_dept 表中添加一条记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('cas:dept:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量添加", value = "'向 cas_dept 表中批量添加 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('cas:dept:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "更新", value = "'更新 cas_dept 表中 id = ' + #entity.id + ' 的记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('cas:dept:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Dept entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量更新", value = "'批量更新 cas_dept 表中 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('cas:dept:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Dept> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Dept 记录")
    @AppLog(bizType = "部门", operType = "删除", value = "'删除 cas_dept 表中 id = ' + #entity.id + ' 的记录'")
    @PreAuthorize("@exp.check('cas:dept:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Dept 记录")
    @AppLog(bizType = "部门", operType = "批量删除", value = "'批量删除 cas_dept 表中 id = ' + #ids + ' 的记录'")
    @PreAuthorize("@exp.check('cas:dept:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DeptQuery 查询 DeptDto 列表")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("/list")
    public DataListResult<DeptDto> list(DeptQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 DeptQuery 分页查询 DeptDto 列表")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("/page")
    public PageResult<DeptDto> page(Pageable pageable, DeptQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 DeptDto")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("/{id}")
    public DataResult<DeptDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DeptQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(DeptQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DeptDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部门", operType = "导出", value = "'导出 cas_dept 表中 id = ' + #ids + ' 的记录'")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 DeptQuery 分页查询 DeptDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部门", operType = "导出", value = "分页导出 cas_dept 表中的记录")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, DeptQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @ApiOperation("根据 query 条件，返回 DeptDto 树形列表")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("treeList")
    public DataListResult<DeptDto> treeList(DeptQuery query) {
        return DataListResult.ok(service.pojoIncompleteTreeListByQuery(query));
    }

    @ApiOperation("根据ID获取同级与上级数据")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @PostMapping("superiorTreeList")
    public DataListResult<DeptDto> superiorTreeList(@RequestBody Collection<Serializable> ids) {
        return DataListResult.ok(service.pojoSuperiorListByIds(ids));
    }

    // @Log("更新一条 SysDept 记录的关联关系")
    // @PreAuthorize("@exp.check('cas:dept:edit')")
    // @ApiOperation("更新一条 SysDept 记录的关联关系")
    // @PutMapping("relation")
    // public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
    //     return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    // }
}
