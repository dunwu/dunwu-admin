package io.github.dunwu.module.security.controller;

import io.github.dunwu.module.security.entity.dto.OnlineUserDto;
import io.github.dunwu.module.security.service.AuthService;
import io.github.dunwu.tool.data.DataResult;
import io.github.dunwu.tool.data.PageResult;
import io.github.dunwu.tool.web.log.annotation.AppLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证码配置
 *
 * @author peng.zhang
 * @date 2021-10-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("auth/online")
@Api(tags = "【权限管理】在线用户管理接口")
public class OnlineController {

    private final AuthService authService;

    @ApiOperation("分页查询在线用户")
    @PreAuthorize("@exp.check()")
    @GetMapping("page")
    public PageResult<OnlineUserDto> query(Pageable pageable, String filter) {
        return PageResult.ok(authService.pageOnlineUsers(pageable, filter));
    }

    @ApiOperation("批量强制用户下线")
    @PreAuthorize("@exp.check()")
    @PostMapping("del/batch")
    public DataResult<Boolean> delete(@RequestBody Set<String> ids) {
        return DataResult.ok(authService.batchOffline(ids));
    }

    @ApiOperation("根据 Pageable 和 DeptQuery 分页查询 DeptDto 列表，并导出 excel 表单")
    @AppLog(bizType = "部门", operType = "导出", value = "分页导出 cas_dept 表中的记录")
    @PreAuthorize("@exp.check('cas:dept:view')")
    @GetMapping("/export/page")
    public void exportPage(String filter, Pageable pageable, HttpServletResponse response) {
        authService.exportPage(pageable, filter, response);
    }

}
