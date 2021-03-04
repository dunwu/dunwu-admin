package ${package.Controller};

import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataListResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${package.Service}.${table.serviceName};
<#if enableSwagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
<#if !restControllerStyle>
import org.springframework.stereotype.Controller;
</#if>

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Controller 类
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if enableSwagger>
@Api(tags = "${table.comment!} Controller 类")
</#if>
@RequiredArgsConstructor
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} service;

    <#if !enableSwagger>
    public ${table.controllerName}(${table.serviceName} service) {
        this.service = service;
    }
    </#if>

    /** 添加一条 {@link ${entity}} 记录 */
    <#if enableSwagger>
    @ApiOperation("添加一条 ${entity} 记录")
    </#if>
    @PostMapping("add")
    public BaseResult add(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        service.save(entity);
        return BaseResult.ok();
    }

    /** 批量添加 {@link ${entity}} 记录 */
    <#if enableSwagger>
    @ApiOperation("批量添加 ${entity} 记录")
    </#if>
    @PostMapping("add/batch")
    public BaseResult addBatch(@Validated(AddCheck.class) @RequestBody Collection<${entity}> list) {
        service.saveBatch(list);
        return BaseResult.ok();
    }

    /** 更新一条 {@link ${entity}} 记录 */
    <#if enableSwagger>
    @ApiOperation("更新一条 ${entity} 记录")
    </#if>
    @PostMapping("edit")
    public BaseResult edit(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        service.updateById(entity);
        return BaseResult.ok();
    }

    /** 批量更新 {@link ${entity}} 记录 */
    <#if enableSwagger>
    @ApiOperation("批量更新 ${entity} 记录")
    </#if>
    @PostMapping("edit/batch")
    public BaseResult editBatch(@Validated(EditCheck.class) @RequestBody Collection<${entity}> list) {
        service.updateBatchById(list);
        return BaseResult.ok();
    }

    <#if enableSwagger>
    @ApiOperation("删除一条 ${entity} 记录")
    </#if>
    @PostMapping("del/{id}")
    public BaseResult deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return BaseResult.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 集合批量删除 ${entity} 记录")
    </#if>
    @PostMapping("del/batch")
    public BaseResult deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return BaseResult.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 条件，查询匹配条件的 ${table.dtoName} 列表")
    @GetMapping("list")
    </#if>
    public DataListResult<${table.dtoName}> list(${table.queryName} query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 和 pageable 条件，分页查询 ${table.dtoName} 记录")
    </#if>
    @GetMapping("page")
    public PageResult<${table.dtoName}> page(${table.queryName} query, Pageable pageable) {
        return PageResult.ok(service.pojoPageByQuery(query, pageable));
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    </#if>
    @GetMapping("count")
    public DataResult<Integer> count(${table.queryName} query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @GetMapping("{id}")
    <#if enableSwagger>
    @ApiOperation("根据 ID 查询 ${table.dtoName} 记录")
    </#if>
    public DataResult<${table.dtoName}> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @GetMapping("export")
    @ApiOperation("根据 ID 集合批量导出 ${table.dtoName} 列表数据")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("export/page")
    @ApiOperation("根据 query 和 pageable 条件批量导出 ${table.dtoName} 列表数据")
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
