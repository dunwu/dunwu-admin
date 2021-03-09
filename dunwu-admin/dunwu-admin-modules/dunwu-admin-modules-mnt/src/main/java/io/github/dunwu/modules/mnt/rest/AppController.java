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
package io.github.dunwu.modules.mnt.rest;

import io.github.dunwu.modules.mnt.domain.App;
import io.github.dunwu.modules.mnt.service.AppService;
import io.github.dunwu.modules.mnt.service.dto.AppQueryCriteria;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "运维：应用管理")
@RequestMapping("app")
public class AppController {

    private final AppService appService;

    @ApiOperation("导出应用数据")
    @GetMapping(value = "export")
    @PreAuthorize("@exp.check('app:view')")
    public void download(HttpServletResponse response, AppQueryCriteria criteria) throws IOException {
        appService.download(appService.queryAll(criteria), response);
    }

    @ApiOperation(value = "查询应用")
    @GetMapping
	@PreAuthorize("@exp.check('app:view')")
    public ResponseEntity<Object> query(AppQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(appService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @AppLog("新增应用")
    @ApiOperation(value = "新增应用")
    @PostMapping
	@PreAuthorize("@exp.check('app:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody App resources){
        appService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AppLog("修改应用")
    @ApiOperation(value = "修改应用")
    @PutMapping
	@PreAuthorize("@exp.check('app:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody App resources){
        appService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @AppLog("删除应用")
    @ApiOperation(value = "删除应用")
	@DeleteMapping
	@PreAuthorize("@exp.check('app:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids){
        appService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
