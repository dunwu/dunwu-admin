package io.github.dunwu.module.sys.controller;

import io.github.dunwu.module.sys.entity.GlobalConfig;
import io.github.dunwu.module.sys.entity.dto.GlobalConfigDto;
import io.github.dunwu.module.sys.entity.query.GlobalConfigQuery;
import io.github.dunwu.module.sys.service.GlobalConfigService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统全局配置表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/sys/config")
@Api(tags = "系统全局配置表 Controller 类")
@RequiredArgsConstructor
public class GlobalConfigController {

    private final GlobalConfigService service;

    @ApiOperation("添加一条 GlobalConfig 记录")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody GlobalConfig entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 GlobalConfig 记录")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<GlobalConfig> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 GlobalConfig 记录")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody GlobalConfig entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 GlobalConfig 记录")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<GlobalConfig> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 GlobalConfig 记录")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 GlobalConfig 记录")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 GlobalConfigQuery 查询 GlobalConfigDto 列表")
    @GetMapping("list")
    public DataListResult<GlobalConfigDto> list(GlobalConfigQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 GlobalConfigQuery 和 Pageable 分页查询 GlobalConfigDto 列表")
    @GetMapping("page")
    public PageResult<GlobalConfigDto> page(GlobalConfigQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 GlobalConfigDto")
    @GetMapping("{id}")
    public DataResult<GlobalConfigDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 GlobalConfigQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(GlobalConfigQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 GlobalConfigDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 GlobalConfigQuery 和 Pageable 分页查询 GlobalConfigDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(GlobalConfigQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
