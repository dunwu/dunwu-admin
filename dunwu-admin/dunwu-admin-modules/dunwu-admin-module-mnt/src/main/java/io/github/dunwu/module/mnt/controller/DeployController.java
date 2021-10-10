package io.github.dunwu.module.mnt.controller;

import io.github.dunwu.module.mnt.entity.Deploy;
import io.github.dunwu.module.mnt.entity.dto.DeployDto;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.query.DeployQuery;
import io.github.dunwu.module.mnt.service.DeployService;
import io.github.dunwu.tool.data.DataListResult;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.MapResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.github.dunwu.tool.io.FileUtil;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 部署管理 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@RestController
@RequestMapping("/mnt/deploy")
@Api(tags = "部署管理 Controller 类")
@RequiredArgsConstructor
public class DeployController {

    private final String fileSavePath = FileUtil.getTmpDirPath() + "/";
    private final DeployService service;

    @ApiOperation("添加一条 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "添加", value = "'向 mnt_deploy 表中添加一条记录，内容为：' + #dto")
    @PostMapping("add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody DeployDto dto) {
        return DataResult.ok(service.insert(dto));
    }

    @ApiOperation("批量添加 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "批量添加", value = "'向 mnt_deploy 表中批量添加 ' + #list.size + ' 条记录'")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<Deploy> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "更新", value = "'更新 mnt_deploy 表中 id = ' + #dto.id + ' 的记录，内容为：' + #dto")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody DeployDto dto) {
        return DataResult.ok(service.updateById(dto));
    }

    @ApiOperation("根据 id 批量更新 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "批量更新", value = "'批量更新 mnt_deploy 表中 ' + #list.size + ' 条记录'")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<Deploy> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "删除", value = "'删除 mnt_deploy 表中 id = ' + #entity.id + ' 的记录'")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 Deploy 记录")
    @AppLog(bizType = "部署管理", operType = "批量删除", value = "'批量删除 mnt_deploy 表中 ' + #list.size + ' 条记录'")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 DeployQuery 查询 DeployDto 列表")
    @GetMapping("list")
    public DataListResult<DeployDto> list(DeployQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 DeployQuery 和 Pageable 分页查询 DeployDto 列表")
    @GetMapping("page")
    public PageResult<DeployDto> page(DeployQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 DeployDto")
    @GetMapping("{id}")
    public DataResult<DeployDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 DeployQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(DeployQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 DeployDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部署管理", operType = "导出", value = "'导出 mnt_deploy 表中 id = ' + #ids + ' 的记录'")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 DeployQuery 和 Pageable 分页查询 DeployDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部署管理", operType = "导出", value = "分页导出 mnt_deploy 表中的记录")
    @GetMapping("export/page")
    public void exportPage(DeployQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @AppLog("上传文件部署")
    @ApiOperation("上传文件部署")
    @PreAuthorize("@exp.check('mnt:deploy:exec')")
    @PostMapping("upload")
    public MapResult<String, Object> upload(@RequestBody MultipartFile file, HttpServletRequest request)
        throws Exception {
        Long id = Long.valueOf(request.getParameter("id"));
        String fileName = "";
        if (file != null) {
            fileName = file.getOriginalFilename();
            File deployFile = new File(fileSavePath + fileName);
            FileUtil.del(deployFile);
            file.transferTo(deployFile);
            //文件下一步要根据文件名字来
            service.deployApp(fileSavePath + fileName, id);
        } else {
            System.out.println("没有找到相对应的文件");
        }
        System.out.println("文件上传的原名称为:" + Objects.requireNonNull(file).getOriginalFilename());
        Map<String, Object> map = new HashMap<>(2);
        map.put("errno", 0);
        map.put("id", fileName);
        return MapResult.ok(map);
    }

    @AppLog("获取服务运行状态")
    @ApiOperation("获取服务运行状态")
    @PreAuthorize("@exp.check('mnt:deploy:view')")
    @PostMapping("getServerStatus")
    public DataResult<String> getServerStatus(@Validated @RequestBody DeployDto dto) {
        return DataResult.ok(service.getServerStatus(dto));
    }

    @AppLog("系统还原")
    @ApiOperation("系统还原")
    @PreAuthorize("@exp.check('mnt:deploy:exec')")
    @PostMapping("rollbackServer")
    public DataResult<String> rollbackServer(@Validated @RequestBody DeployHistoryDto dto) {
        return DataResult.ok(service.rollbackServer(dto));
    }

    @AppLog("启动服务")
    @ApiOperation("启动服务")
    @PreAuthorize("@exp.check('mnt:deploy:exec')")
    @PostMapping("startServer")
    public DataResult<String> startServer(@Validated @RequestBody DeployDto dto) {
        return DataResult.ok(service.startServer(dto));
    }

    @AppLog("停止服务")
    @ApiOperation("停止服务")
    @PostMapping(value = "/stopServer")
    @PreAuthorize("@exp.check('deploy:edit')")
    public DataResult<String> stopServer(@Validated @RequestBody DeployDto dto) {
        return DataResult.ok(service.stopServer(dto));
    }

}
