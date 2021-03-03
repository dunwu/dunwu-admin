package io.github.dunwu.modules.generator.controller;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.generator.config.GlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.service.CodeGlobalConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 代码生成-全局配置 Controller 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@RestController
@RequestMapping("/generator/global")
@Api(tags = "代码生成-全局配置 Controller 类")
@RequiredArgsConstructor
public class CodeGlobalConfigController {

    private final CodeGlobalConfigService service;

    @ApiOperation("根据 query 和 pageable 条件批量导出 CodeGlobalConfigDto 列表数据")
    @GetMapping("find")
    public DataResult<CodeGlobalConfigDto> find() {
        CodeGlobalConfigDto dto = service.findByCurrentUser();
        if (dto == null) {
            GlobalConfig globalConfig = new GlobalConfig();
            dto = BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
        }
        return DataResult.ok(dto);
    }

    @ApiOperation("根据 query 和 pageable 条件批量导出 CodeGlobalConfigDto 列表数据")
    @PostMapping("save")
    public BaseResult save(@RequestBody CodeGlobalConfig entity) {
        CodeGlobalConfigDto dto = service.findByCurrentUser();
        if (dto == null) {
            service.save(entity);
        } else {
            service.updateById(entity);
        }
        return BaseResult.ok();
    }

}
