package io.github.dunwu.modules.system.controller;

import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysDeptRelationDto;
import io.github.dunwu.modules.system.entity.query.SysDeptQuery;
import io.github.dunwu.modules.system.service.SysDeptService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@RestController
@RequestMapping("sys/dept")
@Api(tags = "系统：部门管理")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService service;

    @Log("添加一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:add')")
    @ApiOperation("添加一条 SysDept 记录")
    @PostMapping("add")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysDeptDto entity) {
        return new ResponseEntity<>(service.save(entity), HttpStatus.CREATED);
    }

    @Log("更新一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:edit')")
    @ApiOperation("更新一条 SysDept 记录")
    @PostMapping("edit")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysDeptDto entity) {
        return new ResponseEntity<>(service.updateById(entity), HttpStatus.ACCEPTED);
    }

    @Log("删除一条 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("删除一条 SysDept 记录")
    @PostMapping("del/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @Log("根据 ID 集合批量删除 SysDept 记录")
    @PreAuthorize("@exp.check('dept:del')")
    @ApiOperation("根据 ID 集合批量删除 SysDept 记录")
    @PostMapping("del")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysDeptDto 列表")
    @GetMapping("list")
    public ResponseEntity<Object> list(SysDeptQuery query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysDeptDto 记录")
    @GetMapping("page")
    public ResponseEntity<Object> page(SysDeptQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public ResponseEntity<Object> count(SysDeptQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 查询 SysDept 记录")
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysDeptDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysDeptQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPageData(query, pageable, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 ID 集合批量导出 SysDeptDto 列表数据")
    @GetMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportByIds(ids, response);
    }

    @PreAuthorize("@exp.check('dept:view')")
    @ApiOperation("根据 query 条件，返回 SysDeptDto 树形列表")
    @GetMapping("treeList")
    public ResponseEntity<Object> treeList(SysDeptQuery query) {
        return new ResponseEntity<>(service.treeListMap(query), HttpStatus.OK);
    }

    @ApiOperation("查询部门:根据ID获取同级与上级数据")
    @PreAuthorize("@exp.check('dept:view')")
    @GetMapping("superior")
    public ResponseEntity<Object> superiorList(Collection<Long> ids) {
        Collection<SysDeptDto> depts = new ArrayList<>();
        for (Long id : ids) {
            SysDeptDto sysDeptDto = service.pojoById(id);
            if (sysDeptDto == null) {
                continue;
            }

            if (sysDeptDto.getPid() != null) {
                // 获取上级部门
                SysDeptDto parentDept = service.pojoById(sysDeptDto.getPid());
                depts.add(parentDept);

                // 获取所有同级部门
                SysDeptQuery query = new SysDeptQuery();
                query.setPid(sysDeptDto.getPid());
                Collection<SysDeptDto> list = service.pojoListByQuery(query);
                depts.addAll(list);
            }
        }
        Map<String, Object> stringObjectMap = new HashMap<>(service.buildTreeList(depts));
        return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);
    }

    @PutMapping("relation")
    @Log("更新一条 SysDept 记录的关联关系")
    @PreAuthorize("@exp.check('dept:edit')")
    @ApiOperation("更新一条 SysDept 记录的关联关系")
    public ResponseEntity<Object> updateRelations(@Validated @RequestBody SysDeptRelationDto dto) {
        return new ResponseEntity<>(service.updateRelations(dto), HttpStatus.ACCEPTED);
    }

}
