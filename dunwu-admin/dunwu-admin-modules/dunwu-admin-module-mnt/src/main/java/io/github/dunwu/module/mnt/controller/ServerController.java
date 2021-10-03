package io.github.dunwu.module.mnt.controller;

import io.github.dunwu.module.mnt.entity.Server;
import io.github.dunwu.module.mnt.entity.dto.ServerDto;
import io.github.dunwu.module.mnt.entity.query.ServerQuery;
import io.github.dunwu.module.mnt.service.ServerService;
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
 * 服务器配置表 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@RestController
@RequestMapping("/mnt/server")
@Api(tags = "服务器配置表 Controller 类")
@RequiredArgsConstructor
public class ServerController {

    private final ServerService service;

    @ApiOperation("添加一条 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "添加", value = "'向 mnt_server 表中添加一条记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('mnt:server:add')")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Server entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "批量添加", value = "'向 mnt_server 表中批量添加 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('mnt:server:add')")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Server> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "更新", value = "'更新 mnt_server 表中 id = ' + #entity.id + ' 的记录，内容为：' + #entity")
    @PreAuthorize("@exp.check('mnt:server:edit')")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Server entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "批量更新", value = "'批量更新 mnt_server 表中 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('mnt:server:edit')")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Server> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "删除", value = "'删除 mnt_server 表中 id = ' + #entity.id + ' 的记录'")
    @PreAuthorize("@exp.check('mnt:server:del')")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Server 记录")
    @AppLog(bizType = "服务器配置表", operType = "批量删除", value = "'批量删除 mnt_server 表中 ' + #list.size + ' 条记录'")
    @PreAuthorize("@exp.check('mnt:server:del')")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 ServerQuery 查询 ServerDto 列表")
    @PreAuthorize("@exp.check('mnt:server:view')")
    @GetMapping("list")
    public DataListResult<ServerDto> list(ServerQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 ServerQuery 和 Pageable 分页查询 ServerDto 列表")
    @PreAuthorize("@exp.check('mnt:server:view')")
    @GetMapping("page")
    public PageResult<ServerDto> page(ServerQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 ServerDto")
    @PreAuthorize("@exp.check('mnt:server:view')")
    @GetMapping("{id}")
    public DataResult<ServerDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 ServerQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(ServerQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 ServerDto 列表，并导出 excel 表单")
    @AppLog(bizType = "服务器配置表", operType = "导出", value = "'导出 mnt_server 表中 id = ' + #ids + ' 的记录'")
    @PreAuthorize("@exp.check('mnt:server:view')")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 ServerQuery 和 Pageable 分页查询 ServerDto 列表，并导出 excel 表单")
    @AppLog(bizType = "服务器配置表", operType = "导出", value = "分页导出 mnt_server 表中的记录")
    @PreAuthorize("@exp.check('mnt:server:view')")
    @GetMapping("export/page")
    public void exportPage(ServerQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(query, pageable, response);
    }

    @AppLog("测试连接服务器")
    @ApiOperation("测试连接服务器")
    @PostMapping("test")
    public DataResult<Boolean> testConnect(@Validated @RequestBody ServerDto dto) {
        return DataResult.ok(service.testConnect(dto));
    }

}
