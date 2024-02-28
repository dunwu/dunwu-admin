package io.github.dunwu.module.sys.controller;

import io.github.dunwu.module.sys.entity.TableColumnConfig;
import io.github.dunwu.module.sys.entity.dto.TableColumnConfigDto;
import io.github.dunwu.module.sys.entity.dto.TableConfigDto;
import io.github.dunwu.module.sys.entity.query.TableColumnConfigQuery;
import io.github.dunwu.module.sys.service.TableColumnConfigService;
import io.github.dunwu.tool.data.entity.TableInfo;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 表字段配置表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@RestController
@RequestMapping("/sys/table")
@Api(tags = "表字段配置表 Controller 类")
@RequiredArgsConstructor
public class TableController {

    private final TableColumnConfigService service;

    @ApiOperation("添加一条 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:add')")
    @PostMapping("/column/config/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody TableColumnConfig entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:add')")
    @PostMapping("/column/config/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<TableColumnConfig> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:edit')")
    @PostMapping("/column/config/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody TableColumnConfig entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:edit')")
    @PostMapping("/column/config/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<TableColumnConfig> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:del')")
    @PostMapping("/column/config/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 TableColumnConfig 记录")
    @PreAuthorize("@exp.check('sys:column:config:del')")
    @PostMapping("/column/config/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 TableColumnConfigQuery 查询 TableColumnConfigDto 列表")
    @PreAuthorize("@exp.check('sys:column:config:view')")
    @GetMapping("/column/config/list")
    public DataListResult<TableColumnConfigDto> list(TableColumnConfigQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 TableColumnConfigQuery 分页查询 TableColumnConfigDto 列表")
    @PreAuthorize("@exp.check('sys:column:config:view')")
    @GetMapping("/column/config/page")
    public PageResult<TableColumnConfigDto> page(Pageable pageable, TableColumnConfigQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 TableColumnConfigDto")
    @PreAuthorize("@exp.check('sys:column:config:view')")
    @GetMapping("/column/config/{id}")
    public DataResult<TableColumnConfigDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 TableColumnConfigQuery 查询匹配条件的记录数")
    @GetMapping("/column/config/count")
    public DataResult<Integer> count(TableColumnConfigQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("导入 excel 表单")
    @PreAuthorize("@exp.check('sys:column:config:edit')")
    @PostMapping("/column/config/import/list")
    public DataResult<Boolean> importList(@RequestBody MultipartFile file) {
        service.importList(file);
        return DataResult.ok();
    }

    @ApiOperation("根据 id 列表查询 TableColumnConfigDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:column:config:view')")
    @PostMapping("/column/config/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 TableColumnConfigQuery 分页查询 TableColumnConfigDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:column:config:view')")
    @GetMapping("/column/config/export/page")
    public void exportPage(Pageable pageable, TableColumnConfigQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    // ========================================================================
    // 扩展接口
    // ========================================================================

    @GetMapping("/page/sync")
    public PageResult<TableInfo> syncTablePage(String tableName, Pageable pageable) {
        return PageResult.ok(service.syncTablePage(tableName, pageable));
    }

    @GetMapping("/config/sync")
    public DataResult<TableConfigDto> syncTableConfig(TableColumnConfigQuery query) {
        return DataResult.ok(service.syncTableConfig(query));
    }

    @PostMapping("/config/save")
    public DataResult<Boolean> saveTableConfig(@RequestBody TableConfigDto dto) {
        return DataResult.ok(service.saveTableConfig(dto));
    }

}
