package io.github.dunwu.module.storage.controller;

import io.github.dunwu.module.storage.entity.FileContent;
import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.query.FileContentQuery;
import io.github.dunwu.module.storage.service.FileContentService;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
 * 文件内容表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/tool/file/content")
@Api(tags = "文件内容表 Controller 类")
@RequiredArgsConstructor
public class FileContentController {

    private final FileContentService service;

    @ApiOperation("添加一条 FileContent 记录")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody FileContent entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 FileContent 记录")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<FileContent> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 FileContent 记录")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody FileContent entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 FileContent 记录")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<FileContent> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 FileContent 记录")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 FileContent 记录")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 FileContentQuery 查询 FileContentDto 列表")
    @GetMapping("list")
    public DataListResult<FileContentDto> list(FileContentQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 FileContentQuery 和 Pageable 分页查询 FileContentDto 列表")
    @GetMapping("page")
    public PageResult<FileContentDto> page(FileContentQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 FileContentDto")
    @GetMapping("{id}")
    public DataResult<FileContentDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 FileContentQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(FileContentQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 FileContentDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 FileContentQuery 和 Pageable 分页查询 FileContentDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(FileContentQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

}
