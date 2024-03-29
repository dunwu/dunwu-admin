package io.github.dunwu.module.sys.controller;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.module.sys.entity.Dict;
import io.github.dunwu.module.sys.entity.dto.DictDto;
import io.github.dunwu.module.sys.entity.dto.EnumInfoDto;
import io.github.dunwu.module.sys.entity.query.DictQuery;
import io.github.dunwu.module.sys.service.DictService;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/sys/dict")
@Api(tags = "【系统】数据字典")
@RequiredArgsConstructor
public class DictController {

    private final DictService service;

    @ApiOperation("添加一条 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody Dict entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Dict> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody Dict entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Dict> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Dict 记录")
    @PreAuthorize("@exp.check('sys:dict:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DictQuery 查询 DictDto 列表")
    @PreAuthorize("@exp.check('sys:dict:view')")
    @GetMapping("/list")
    public DataListResult<DictDto> list(DictQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 DictQuery 分页查询 DictDto 列表")
    @PreAuthorize("@exp.check('sys:dict:view')")
    @GetMapping("/page")
    public PageResult<DictDto> page(Pageable pageable, DictQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 DictDto")
    @PreAuthorize("@exp.check('sys:dict:view')")
    @GetMapping("/{id}")
    public DataResult<DictDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DictQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(DictQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("导入 excel 表单")
    @PreAuthorize("@exp.check('sys:dict:edit')")
    @PostMapping("/import/list")
    public DataResult<Boolean> importList(@RequestBody MultipartFile file) {
        service.importList(file);
        return DataResult.ok();
    }

    @ApiOperation("根据 id 列表查询 DictDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:dict:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 DictQuery 分页查询 DictDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('sys:dict:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, DictQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @ApiOperation("上传枚举 java 文件并解析")
    @PreAuthorize("@exp.check('sys:dict:edit')")
    @PostMapping("/upload/javaenum")
    public DataResult<EnumInfoDto> parseJavaEnumFile(@RequestBody MultipartFile file) {
        EnumInfoDto enumInfoDto = service.parseJavaEnumFile(file);
        log.info("baseDictDto = {}", JSONUtil.toJsonPrettyStr(enumInfoDto));
        return DataResult.ok(enumInfoDto);
    }

    @ApiOperation("保存含字典选项的字典")
    @PreAuthorize("@exp.check('sys:dict:edit')")
    @PostMapping("/save/dictWithOptions")
    public DataResult<Boolean> saveDictWithOptions(@RequestBody DictDto dto) {
        return DataResult.ok(service.saveDictWithOptions(dto));
    }

    @ApiOperation("保存含字典选项的字典")
    @GetMapping("/generate/enum/{id}")
    public void generateEnum(@PathVariable Serializable id, HttpServletRequest request, HttpServletResponse response) {
        service.downloadDictEnum(id, request, response);
    }

}
