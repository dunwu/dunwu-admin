package io.github.dunwu.modules.monitor.controller;

import io.github.dunwu.modules.monitor.service.ServerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Api(tags = "系统-服务监控管理")
@RequestMapping("monitor/server")
public class ServerInfoController {

    private final ServerInfoService serverService;

    @GetMapping
    @ApiOperation("查询服务监控")
    @PreAuthorize("@exp.check('monitor:view')")
    public ResponseEntity<Object> query() {
        return new ResponseEntity<>(serverService.getServers(), HttpStatus.OK);
    }

}
