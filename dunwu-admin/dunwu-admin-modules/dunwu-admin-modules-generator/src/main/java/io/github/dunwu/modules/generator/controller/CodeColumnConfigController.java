// package io.github.dunwu.modules.generator.controller;
//
// import io.github.dunwu.data.core.BaseResult;
// import io.github.dunwu.data.core.DataListResult;
// import io.github.dunwu.data.core.DataResult;
// import io.github.dunwu.data.core.PageResult;
// import io.github.dunwu.data.validator.annotation.AddCheck;
// import io.github.dunwu.data.validator.annotation.EditCheck;
// import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
// import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
// import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
// import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Pageable;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.*;
//
// import java.io.IOException;
// import java.io.Serializable;
// import java.util.Collection;
// import javax.servlet.http.HttpServletResponse;
//
// /**
//  * 代码生成-字段级别配置 Controller 类
//  *
//  * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
//  * @since 2021-03-02
//  */
// @RestController
// @RequestMapping("/generator/column")
// @Api(tags = "代码生成-字段级别配置 Controller 类")
// @RequiredArgsConstructor
// public class CodeColumnConfigController {
//
//     private final CodeColumnConfigService service;
//
//     /** 添加一条 {@link CodeColumnConfig} 记录 */
//     @ApiOperation("添加一条 CodeColumnConfig 记录")
//     @PostMapping("add")
//     public BaseResult add(@Validated(AddCheck.class) @RequestBody CodeColumnConfig entity) {
//         service.save(entity);
//         return BaseResult.ok();
//     }
//
//     /** 批量添加 {@link CodeColumnConfig} 记录 */
//     @ApiOperation("批量添加 CodeColumnConfig 记录")
//     @PostMapping("add/batch")
//     public BaseResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<CodeColumnConfig> list) {
//         service.saveBatch(list);
//         return BaseResult.ok();
//     }
//
//     /** 更新一条 {@link CodeColumnConfig} 记录 */
//     @ApiOperation("更新一条 CodeColumnConfig 记录")
//     @PostMapping("edit")
//     public BaseResult edit(@Validated(EditCheck.class) @RequestBody CodeColumnConfig entity) {
//         service.updateById(entity);
//         return BaseResult.ok();
//     }
//
//     /** 批量更新 {@link CodeColumnConfig} 记录 */
//     @ApiOperation("批量更新 CodeColumnConfig 记录")
//     @PostMapping("edit/batch")
//     public BaseResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<CodeColumnConfig> list) {
//         service.updateBatchById(list);
//         return BaseResult.ok();
//     }
//
//     @ApiOperation("删除一条 CodeColumnConfig 记录")
//     @PostMapping("del/{id}")
//     public BaseResult deleteById(@PathVariable Serializable id) {
//         service.removeById(id);
//         return BaseResult.ok();
//     }
//
//     @ApiOperation("根据 ID 集合批量删除 CodeColumnConfig 记录")
//     @PostMapping("del/batch")
//     public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
//         service.removeByIds(ids);
//         return BaseResult.ok();
//     }
//
//     @ApiOperation("根据 query 条件，查询匹配条件的 CodeColumnConfigDto 列表")
//     @GetMapping("list")
//     public DataListResult<CodeColumnConfigDto> list(CodeColumnConfigQuery query) {
//         return DataListResult.ok(service.pojoListByQuery(query));
//     }
//
//     @ApiOperation("根据 query 和 pageable 条件，分页查询 CodeColumnConfigDto 记录")
//     @GetMapping("page")
//     public PageResult<CodeColumnConfigDto> page(CodeColumnConfigQuery query, Pageable pageable) {
//         return PageResult.ok(service.pojoPageByQuery(query, pageable));
//     }
//
//     @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
//     @GetMapping("count")
//     public DataResult<Integer> count(CodeColumnConfigQuery query) {
//         return DataResult.ok(service.countByQuery(query));
//     }
//
//     @GetMapping("{id}")
//     @ApiOperation("根据 ID 查询 CodeColumnConfigDto 记录")
//     public DataResult<CodeColumnConfigDto> getById(@PathVariable Serializable id) {
//         return DataResult.ok(service.pojoById(id));
//     }
//
//     @GetMapping("export")
//     @ApiOperation("根据 ID 集合批量导出 CodeColumnConfigDto 列表数据")
//     public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
//         throws IOException {
//         service.exportList(ids, response);
//     }
//
//     @GetMapping("export/page")
//     @ApiOperation("根据 query 和 pageable 条件批量导出 CodeColumnConfigDto 列表数据")
//     public void exportPage(CodeColumnConfigQuery query, Pageable pageable, HttpServletResponse response)
//         throws IOException {
//         service.exportPage(query, pageable, response);
//     }
//
//     @ApiOperation("同步字段数据")
//     @PostMapping(value = "sync")
//     public BaseResult sync(@RequestBody CodeColumnConfigQuery query) {
//         service.syncTables(query);
//         return BaseResult.ok();
//     }
//
// }
