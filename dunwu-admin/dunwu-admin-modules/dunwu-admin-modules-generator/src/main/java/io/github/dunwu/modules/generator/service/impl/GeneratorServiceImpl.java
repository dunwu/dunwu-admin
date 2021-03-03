package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.modules.generator.service.CodeGlobalConfigService;
import io.github.dunwu.modules.generator.service.GeneratorService;
import io.github.dunwu.util.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 代码生成服务接口实现类
 *
 * @author peng.zhang
 * @date 2021/3/3
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final CodeGlobalConfigService globalConfigService;

    public GeneratorServiceImpl(CodeGlobalConfigService globalConfigService) {
        this.globalConfigService = globalConfigService;
    }

    @Override
    public CodeGlobalConfigDto findGlobalConfigByCurrentUser() {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }

        CodeGlobalConfigQuery query = new CodeGlobalConfigQuery();
        query.setCreateBy(username);
        return globalConfigService.pojoByQuery(query);
    }

    @Override
    public boolean saveGlobalConfigByCurrentUser(CodeGlobalConfig entity) {
        CodeGlobalConfigDto dto = findGlobalConfigByCurrentUser();
        if (dto == null) {
            return globalConfigService.save(entity);
        } else {
            return globalConfigService.updateById(entity);
        }
    }

}
