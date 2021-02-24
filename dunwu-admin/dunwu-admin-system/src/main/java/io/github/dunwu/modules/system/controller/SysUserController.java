package io.github.dunwu.modules.system.controller;

import io.github.dunwu.config.RsaProperties;
import io.github.dunwu.data.validator.annotation.AddCheck;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.modules.monitor.annotation.Log;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.entity.query.SysUserQuery;
import io.github.dunwu.modules.system.entity.vo.UserPassVo;
import io.github.dunwu.modules.system.service.SysUserService;
import io.github.dunwu.modules.system.service.VerifyService;
import io.github.dunwu.util.RsaUtils;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.util.enums.CodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@RestController
@RequestMapping("sys/user")
@Api(tags = "系统：用户管理")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService service;
    private final PasswordEncoder passwordEncoder;
    private final VerifyService verifyService;

    @Log("添加一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:add')")
    @ApiOperation("添加一条 SysUser 记录")
    @PostMapping("add")
    public ResponseEntity<Object> add(@Validated(AddCheck.class) @RequestBody SysUserDto entity) {
        return new ResponseEntity<>(service.saveUserRelatedRecords(entity), HttpStatus.CREATED);
    }

    @Log("更新一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:edit')")
    @ApiOperation("更新一条 SysUser 记录")
    @PostMapping("edit")
    public ResponseEntity<Object> edit(@Validated(EditCheck.class) @RequestBody SysUserDto entity) {
        return new ResponseEntity<>(service.updateUserRelatedRecords(entity), HttpStatus.ACCEPTED);
    }

    @Log("根据 ID 删除一条 SysUser 记录")
    @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("删除一条 SysUser 记录")
    @PostMapping("del/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.removeById(id), HttpStatus.ACCEPTED);
    }

    @Log("根据 ID 集合批量删除 SysUser 记录")
    @PreAuthorize("@exp.check('user:del')")
    @ApiOperation("根据 ID 集合批量删除 SysUser 记录")
    @PostMapping("del")
    public ResponseEntity<Object> deleteByIds(@RequestBody Collection<Serializable> ids) {
        return new ResponseEntity<>(service.removeByIds(ids), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的 SysUserDto 列表")
    @GetMapping("list")
    public ResponseEntity<Object> list(SysUserQuery query) {
        return new ResponseEntity<>(service.pojoListByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件，分页查询 SysUserDto 记录")
    @GetMapping("page")
    public ResponseEntity<Object> page(SysUserQuery query, Pageable pageable) {
        return new ResponseEntity<>(service.pojoPageByQuery(query, pageable), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 条件，查询匹配条件的总记录数")
    @GetMapping("count")
    public ResponseEntity<Object> count(SysUserQuery query) {
        return new ResponseEntity<>(service.countByQuery(query), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 查询 SysUser 记录")
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Serializable id) {
        return new ResponseEntity<>(service.pojoById(id), HttpStatus.OK);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 query 和 pageable 条件批量导出 SysUserDto 列表数据")
    @GetMapping("export/page")
    public void exportPage(SysUserQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        service.exportPage(query, pageable, response);
    }

    @PreAuthorize("@exp.check('user:view')")
    @ApiOperation("根据 ID 集合批量导出 SysUserDto 列表数据")
    @GetMapping("export/list")
    public void exportList(@RequestBody Collection<Serializable> ids, HttpServletResponse response)
        throws IOException {
        service.exportList(ids, response);
    }

    @Log("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PostMapping("edit/center")
    public ResponseEntity<Object> center(@Validated(EditCheck.class) @RequestBody SysUserDto entity) {
        if (!entity.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BadRequestException("不能修改他人资料");
        }
        service.updateCenter(entity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("edit/password")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassVo entity) throws Exception {
        service.updatePass(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @ApiOperation("修改头像")
    // @PostMapping(value = "/updateAvatar")
    // public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile avatar) {
    //     return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    // }

    @Log("修改用户邮箱")
    @ApiOperation("修改用户邮箱")
    @PostMapping(value = "edit/email/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code,
        @Validated(EditCheck.class) @RequestBody SysUserDto entity) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, entity.getPassword());
        SysUserDto userDto = service.pojoByUsername(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new BadRequestException("密码错误");
        }
        verifyService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + entity.getEmail(), code);
        SysUser user = new SysUser();
        user.setId(entity.getId());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        service.updateById(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
