package io.github.dunwu.module.code.controller;

import cn.hutool.core.io.FileUtil;
import io.github.dunwu.module.code.entity.CodeDatabase;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;
import io.github.dunwu.module.code.entity.query.CodeDatabaseQuery;
import io.github.dunwu.module.code.service.CodeDatabaseService;
import io.github.dunwu.module.code.util.SqlUtils;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据库管理 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
@RestController
@RequestMapping("/code/database")
@Api(tags = "数据库管理 Controller 类")
@RequiredArgsConstructor
public class CodeDatabaseController {

    private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
    private final CodeDatabaseService service;

    @ApiOperation("添加一条 CodeDatabase 记录")
    @PostMapping("add")
    public DataResult add(@Validated(AddCheck.class) @RequestBody CodeDatabase entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 CodeDatabase 记录")
    @PostMapping("add/batch")
    public DataResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<CodeDatabase> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 ID 更新一条 CodeDatabase 记录")
    @PostMapping("edit")
    public DataResult edit(@Validated(EditCheck.class) @RequestBody CodeDatabase entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 ID 批量更新 CodeDatabase 记录")
    @PostMapping("edit/batch")
    public DataResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<CodeDatabase> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 ID 删除一条 CodeDatabase 记录")
    @PostMapping("del/{id}")
    public DataResult deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 ID 列表批量删除 CodeDatabase 记录")
    @PostMapping("del/batch")
    public DataResult deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 CodeDatabaseQuery 查询 CodeDatabaseDto 列表")
    @GetMapping("list")
    public DataResult list(CodeDatabaseQuery query) {
        return DataResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 CodeDatabaseQuery 和 Pageable 分页查询 CodeDatabaseDto 列表")
    @GetMapping("page")
    public DataResult page(CodeDatabaseQuery query, Pageable pageable) {
        return DataResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @GetMapping("{id}")
    @ApiOperation("根据 id 查询 CodeDatabaseDto")
    public DataResult getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 CodeDatabaseQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult count(CodeDatabaseQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 CodeDatabaseDto 列表，并导出 excel 表单")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 CodeDatabaseQuery 和 Pageable 分页查询 CodeDatabaseDto 列表，并导出 excel 表单")
    @GetMapping("export/page")
    public void exportPage(CodeDatabaseQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

    @ApiOperation("测试数据库链接")
    @PostMapping("/testConnect")
    @PreAuthorize("@exp.check('database:view')")
    public DataResult testConnect(@Validated @RequestBody CodeDatabaseDto entity) {
        return DataResult.ok(service.testConnection(entity));
    }

    @ApiOperation("执行SQL脚本")
    @PostMapping(value = "/upload")
    @PreAuthorize("@exp.check('database:add')")
    public DataResult upload(@RequestBody MultipartFile file, HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        CodeDatabaseDto database = service.pojoById(id);
        String fileName;
        if (database != null) {
            fileName = file.getOriginalFilename();
            File executeFile = new File(fileSavePath + fileName);
            FileUtil.del(executeFile);
            file.transferTo(executeFile);
            String result = SqlUtils.executeFile(database.getJdbcUrl(), database.getUsername(),
                database.getPassword(), executeFile);
            return DataResult.ok(result);
        } else {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Database not exist");
        }
    }

}
