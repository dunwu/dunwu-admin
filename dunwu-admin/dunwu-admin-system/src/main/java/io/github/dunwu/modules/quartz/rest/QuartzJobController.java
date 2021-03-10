/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.dunwu.modules.quartz.rest;

import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.quartz.domain.QuartzJob;
import io.github.dunwu.modules.quartz.service.QuartzJobService;
import io.github.dunwu.modules.quartz.service.dto.JobQueryCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("jobs")
@Api(tags = "系统:定时任务管理")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";
    private final QuartzJobService quartzJobService;

    @ApiOperation("查询定时任务")
    @GetMapping
    @PreAuthorize("@exp.check('timing:view')")
    public ResponseEntity<Object> query(JobQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzJobService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @ApiOperation("导出任务数据")
    @GetMapping(value = "export")
    @PreAuthorize("@exp.check('timing:view')")
    public void download(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        quartzJobService.download(quartzJobService.queryAll(criteria), response);
    }

    @ApiOperation("导出日志数据")
    @GetMapping(value = "/logs/download")
    @PreAuthorize("@exp.check('timing:view')")
    public void downloadLog(HttpServletResponse response, JobQueryCriteria criteria) throws IOException {
        quartzJobService.downloadLog(quartzJobService.queryAllLog(criteria), response);
    }

    @ApiOperation("查询任务执行日志")
    @GetMapping(value = "/logs")
    @PreAuthorize("@exp.check('timing:view')")
    public ResponseEntity<Object> queryJobLog(JobQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(quartzJobService.queryAllLog(criteria, pageable), HttpStatus.OK);
    }

    @AppLog("新增定时任务")
    @ApiOperation("新增定时任务")
    @PostMapping
    @PreAuthorize("@exp.check('timing:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getId() != null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
                "A new " + ENTITY_NAME + " cannot already have an ID");
        }
        quartzJobService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AppLog("修改定时任务")
    @ApiOperation("修改定时任务")
    @PutMapping
    @PreAuthorize("@exp.check('timing:edit')")
    public ResponseEntity<Object> update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources) {
        quartzJobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @AppLog("更改定时任务状态")
    @ApiOperation("更改定时任务状态")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@exp.check('timing:edit')")
    public ResponseEntity<Object> update(@PathVariable Long id) {
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @AppLog("执行定时任务")
    @ApiOperation("执行定时任务")
    @PutMapping(value = "/exec/{id}")
    @PreAuthorize("@exp.check('timing:edit')")
    public ResponseEntity<Object> execution(@PathVariable Long id) {
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @AppLog("删除定时任务")
    @ApiOperation("删除定时任务")
    @DeleteMapping
    @PreAuthorize("@exp.check('timing:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        quartzJobService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
