package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/cas/user")
@Api(tags = "系统用户 Controller 类")
@RequiredArgsConstructor
public class UserController {

    public static final String INIT_PASSWORD = "123456";
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation("添加一条 User 记录")
    @AppLog(bizType = "系统用户", operType = "添加", value = "'向 cas_user 表中添加一条记录，内容为：' + #entity")
    @PostMapping("add")
    public DataResult<Long> add(@Validated(AddCheck.class) @RequestBody UserDto entity) {
        entity.setPassword(passwordEncoder.encode(INIT_PASSWORD));
        return DataResult.ok(service.saveUserRelatedRecords(entity));
    }

    @ApiOperation("批量添加 User 记录")
    @AppLog(bizType = "系统用户", operType = "批量添加", value = "'向 cas_user 表中批量添加 ' + #list.size + ' 条记录'")
    @PostMapping("add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<User> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 User 记录")
    @AppLog(bizType = "系统用户", operType = "更新", value = "'更新 cas_user 表中 id = ' + #entity.id + ' 的记录，内容为：' + #entity")
    @PostMapping("edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody UserDto entity) {
        return DataResult.ok(service.updateUserRelatedRecords(entity));
    }

    @ApiOperation("根据 id 批量更新 User 记录")
    @AppLog(bizType = "系统用户", operType = "批量更新", value = "'批量更新 cas_user 表中 ' + #list.size + ' 条记录'")
    @PostMapping("edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<User> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 User 记录")
    @AppLog(bizType = "系统用户", operType = "删除", value = "'删除 cas_user 表中 id = ' + #entity.id + ' 的记录'")
    @PostMapping("del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 User 记录")
    @AppLog(bizType = "系统用户", operType = "批量删除", value = "'批量删除 cas_user 表中 ' + #list.size + ' 条记录'")
    @PostMapping("del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 UserQuery 查询 UserDto 列表")
    @GetMapping("list")
    public DataListResult<UserDto> list(UserQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 UserQuery 和 Pageable 分页查询 UserDto 列表")
    @GetMapping("page")
    public PageResult<UserDto> page(UserQuery query, Pageable pageable) {
        return PageResult.ok(service.pojoSpringPageByQuery(query, pageable));
    }

    @ApiOperation("根据 id 查询 UserDto")
    @GetMapping("{id}")
    public DataResult<UserDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 UserQuery 查询匹配条件的记录数")
    @GetMapping("count")
    public DataResult<Integer> count(UserQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 UserDto 列表，并导出 excel 表单")
    @AppLog(bizType = "系统用户", operType = "导出", value = "'导出 cas_user 表中 id = ' + #ids + ' 的记录'")
    @PostMapping("export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 UserQuery 和 Pageable 分页查询 UserDto 列表，并导出 excel 表单")
    @AppLog(bizType = "系统用户", operType = "导出", value = "分页导出 cas_user 表中的记录")
    @GetMapping("export/page")
    public void exportPage(UserQuery query, Pageable pageable, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @GetMapping("listByDeptId")
    public DataListResult<UserDto> listByDeptId(Serializable deptId) {
        return DataListResult.ok(service.pojoListByDeptId(deptId));
    }

    @ApiOperation("绑定用户到指定部门")
    @PostMapping("bindDept/{deptId}")
    public DataResult<Boolean> bindDept(@PathVariable Long deptId, @RequestBody Collection<Long> userIds) {
        return DataResult.ok(service.bindDept(deptId, userIds));
    }

    @ApiOperation("从指定部门解绑定用户")
    @PostMapping("unbindDept/{deptId}")
    public DataResult<Boolean> unbindDept(@PathVariable Long deptId, @RequestBody Collection<Long> userIds) {
        return DataResult.ok(service.unbindDept(deptId, userIds));
    }

}
