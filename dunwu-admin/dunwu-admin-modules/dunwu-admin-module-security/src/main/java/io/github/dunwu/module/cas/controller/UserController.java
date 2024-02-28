package io.github.dunwu.module.cas.controller;

import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.entity.vo.DeptJobUserMapVo;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.tool.data.response.DataListResult;
import io.github.dunwu.tool.data.response.DataResult;
import io.github.dunwu.tool.data.response.PageResult;
import io.github.dunwu.tool.data.validator.annotation.AddCheck;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * 用户 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/cas/user")
@Api(tags = "【权限】用户管理")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @ApiOperation("添加一条 User 记录")
    @PreAuthorize("@exp.check('cas:user:add')")
    @PostMapping("/add")
    public DataResult<Boolean> add(@Validated(AddCheck.class) @RequestBody User entity) {
        return DataResult.ok(service.insert(entity));
    }

    @ApiOperation("批量添加 User 记录")
    @PreAuthorize("@exp.check('cas:user:add')")
    @PostMapping("/add/batch")
    public DataResult<Boolean> addBatch(@Validated(AddCheck.class) @RequestBody Collection<User> list) {
        return DataResult.ok(service.insertBatch(list));
    }

    @ApiOperation("根据 id 更新一条 User 记录")
    @PreAuthorize("@exp.check('cas:user:edit')")
    @PostMapping("/edit")
    public DataResult<Boolean> edit(@Validated(EditCheck.class) @RequestBody User entity) {
        return DataResult.ok(service.updateById(entity));
    }

    @ApiOperation("根据 id 批量更新 User 记录")
    @PreAuthorize("@exp.check('cas:user:edit')")
    @PostMapping("/edit/batch")
    public DataResult<Boolean> editBatch(@Validated(EditCheck.class) @RequestBody Collection<User> list) {
        return DataResult.ok(service.updateBatchById(list));
    }

    @ApiOperation("根据 id 删除一条 User 记录")
    @PreAuthorize("@exp.check('cas:user:del')")
    @PostMapping("/del/{id}")
    public DataResult<Boolean> deleteById(@PathVariable Serializable id) {
        return DataResult.ok(service.deleteById(id));
    }

    @ApiOperation("根据 id 列表批量删除 User 记录")
    @PreAuthorize("@exp.check('cas:user:del')")
    @PostMapping("/del/batch")
    public DataResult<Boolean> deleteBatchByIds(@RequestBody Collection<? extends Serializable> ids) {
        return DataResult.ok(service.deleteBatchByIds(ids));
    }

    @ApiOperation("根据 UserQuery 查询 UserDto 列表")
    @PreAuthorize("@exp.check('cas:user:view')")
    @GetMapping("/list")
    public DataListResult<UserDto> list(UserQuery query) {
        return DataListResult.ok(service.pojoListByQuery(query));
    }

    @ApiOperation("根据 Pageable 和 UserQuery 分页查询 UserDto 列表")
    @PreAuthorize("@exp.check('cas:user:view')")
    @GetMapping("/page")
    public PageResult<UserDto> page(Pageable pageable, UserQuery query) {
        return PageResult.ok(service.pojoSpringPageByQuery(pageable, query));
    }

    @ApiOperation("根据 id 查询 UserDto")
    @PreAuthorize("@exp.check('cas:user:view')")
    @GetMapping("/{id}")
    public DataResult<UserDto> getById(@PathVariable Serializable id) {
        return DataResult.ok(service.pojoById(id));
    }

    @ApiOperation("根据 UserQuery 查询匹配条件的记录数")
    @GetMapping("/count")
    public DataResult<Integer> count(UserQuery query) {
        return DataResult.ok(service.countByQuery(query));
    }

    @ApiOperation("根据 id 列表查询 UserDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:user:view')")
    @PostMapping("/export/list")
    public void exportList(@RequestBody Collection<? extends Serializable> ids, HttpServletResponse response) {
        service.exportList(ids, response);
    }

    @ApiOperation("根据 Pageable 和 UserQuery 分页查询 UserDto 列表，并导出 excel 表单")
    @PreAuthorize("@exp.check('cas:user:view')")
    @GetMapping("/export/page")
    public void exportPage(Pageable pageable, UserQuery query, HttpServletResponse response) {
        service.exportPage(pageable, query, response);
    }

    @ApiOperation("查询指定部门下的用户列表")
    @PreAuthorize("@exp.check('cas:user:view')")
    @GetMapping("listByDeptId")
    public DataListResult<UserDto> listByDeptId(Serializable deptId) {
        return DataListResult.ok(service.pojoListByDeptId(deptId));
    }

    @ApiOperation("绑定用户到指定部门")
    @PreAuthorize("@exp.check('cas:user:edit')")
    @PostMapping("bindDept")
    public DataResult<Boolean> bindDept(@RequestBody DeptJobUserMapVo vo) {
        return DataResult.ok(service.bindDept(vo));
    }

    @ApiOperation("从指定部门解绑定用户")
    @PreAuthorize("@exp.check('cas:user:edit')")
    @PostMapping("unbindDept")
    public DataResult<Boolean> unbindDept(@RequestBody DeptJobUserMapVo vo) {
        return DataResult.ok(service.unbindDept(vo));
    }

    @ApiOperation("添加一条 User 记录，并保存关联的角色列表 roles")
    @PreAuthorize("@exp.check('cas:user:add')")
    @PostMapping("/addWithRoles")
    public DataResult<Boolean> addWithRoles(@Validated(AddCheck.class) @RequestBody UserDto dto) {
        return DataResult.ok(service.insertWithRoles(dto));
    }

    @ApiOperation("更新一条 User 记录，并保存关联的角色列表 roles")
    @PreAuthorize("@exp.check('cas:user:edit')")
    @PostMapping("/editWithRoles")
    public DataResult<Boolean> editWithRoles(@Validated(EditCheck.class) @RequestBody UserDto dto) {
        return DataResult.ok(service.updateWithRoles(dto));
    }

}
