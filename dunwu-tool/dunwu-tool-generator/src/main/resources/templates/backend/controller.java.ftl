package ${package.Controller};

import io.github.dunwu.data.core.Result;
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

    <#if enableSwagger>
    @ApiOperation("添加一条 ${entity} 记录")
    </#if>
    @PostMapping("add")
    public Result add(@Validated(AddCheck.class) @RequestBody ${entity} entity) {
        service.save(entity);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("批量添加 ${entity} 记录")
    </#if>
    @PostMapping("add/batch")
    public Result addBatch(@Validated(AddCheck.class) @RequestBody Collection<${entity}> list) {
        service.saveBatch(list);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("更新一条 ${entity} 记录")
    </#if>
    @PostMapping("edit")
    public Result edit(@Validated(EditCheck.class) @RequestBody ${entity} entity) {
        service.updateById(entity);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("批量更新 ${entity} 记录")
    </#if>
    @PostMapping("edit/batch")
    public Result editBatch(@Validated(EditCheck.class) @RequestBody Collection<${entity}> list) {
        service.updateBatchById(list);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("删除一条 ${entity} 记录")
    </#if>
    @PostMapping("del/{id}")
    public Result deleteById(@PathVariable Serializable id) {
        service.removeById(id);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 ID 集合批量删除 ${entity} 记录")
    </#if>
    @PostMapping("del/batch")
    public Result deleteByIds(@RequestBody Collection<Serializable> ids) {
        service.removeByIds(ids);
        return Result.ok();
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 条件，查询匹配条件的 ${table.dtoName} 列表")
    @GetMapping("list")
    </#if>
    public Result list(${table.queryName} query) {
        return Result.ok(service.pojoListByQuery(query));
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 和 pageable 条件，分页查询 ${table.dtoName} 记录")
    </#if>
    @GetMapping("page")
    public Result page(${table.queryName} query, Pageable pageable) {
        return Result.ok(service.pojoPageByQuery(query, pageable));
    }

    <#if enableSwagger>
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    </#if>
    @GetMapping("count")
    public Result count(${table.queryName} query) {
        return Result.ok(service.countByQuery(query));
    }

    @GetMapping("{id}")
    <#if enableSwagger>
    @ApiOperation("根据 ID 查询 ${table.dtoName} 记录")
    </#if>
    public Result getById(@PathVariable Serializable id) {
        return Result.ok(service.pojoById(id));
    }

    @GetMapping("export")
    <#if enableSwagger>
    @ApiOperation("根据 ID 集合批量导出 ${table.dtoName} 列表数据")
    </#if>
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @GetMapping("export/page")
    <#if enableSwagger>
    @ApiOperation("根据 query 和 pageable 条件批量导出 ${table.dtoName} 列表数据")
    </#if>
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        service.exportPage(query, pageable, response);
    }

}
