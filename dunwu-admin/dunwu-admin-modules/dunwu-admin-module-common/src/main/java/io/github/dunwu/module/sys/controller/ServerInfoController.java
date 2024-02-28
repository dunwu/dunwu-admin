package io.github.dunwu.module.sys.controller;

import io.github.dunwu.module.sys.service.ServerInfoService;
import io.github.dunwu.tool.data.response.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zheng Jie
 * @date 2020-05-02
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "【系统】系统监控")
@RequestMapping("monitor/server")
public class ServerInfoController {

    private final ServerInfoService serverService;

    @GetMapping
    @ApiOperation("查询服务监控")
    @PreAuthorize("@exp.check('monitor:view')")
    public DataResult query() {
        return DataResult.ok(serverService.getServers());
    }

}
