package io.github.dunwu.module.mnt.controller;

import io.github.dunwu.module.mnt.entity.DeployHistory;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.query.DeployHistoryQuery;
import io.github.dunwu.module.mnt.service.DeployHistoryService;
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
 * 部署历史管理 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@RestController
@RequestMapping("/mnt/deploy/history")
@Api(tags = "部署历史管理 Controller 类")
@RequiredArgsConstructor
public class DeployHistoryController {

    private final DeployHistoryService service;

    @ApiOperation("添加一条 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:add')")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody DeployHistory entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:add')")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<DeployHistory> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:edit')")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody DeployHistory entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:edit')")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<DeployHistory> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:del')")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 DeployHistory 记录")
    @PreAuthorize("@exp.check('mnt:deployHistory:del')")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DeployHistoryQuery 查询 DeployHistoryDto 列表")
    @PreAuthorize("@exp.check('mnt:deployHistory:view')")
    @GetMapping("list")
    public DataListResult<DeployHistoryDto> list(DeployHistoryQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 DeployHistoryQuery 和 Pageable 分页查询 DeployHistoryDto 列表")
    @PreAuthorize("@exp.check('mnt:deployHistory:view')")
    @GetMapping("page")
    public PageResult<DeployHistoryDto> page(DeployHistoryQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 DeployHistoryDto")
    @PreAuthorize("@exp.check('mnt:deployHistory:view')")
    @GetMapping("{id}")
    public DataResult<DeployHistoryDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DeployHistoryQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(DeployHistoryQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DeployHistoryDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('mnt:deployHistory:view')")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 DeployHistoryQuery 和 Pageable 分页查询 DeployHistoryDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('mnt:deployHistory:view')")
    @GetMapping("export/page")
    public void exportPage(DeployHistoryQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
