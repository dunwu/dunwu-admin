package io.github.dunwu.modules.generator.service;

import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;

/**
 * 代码生成服务接口
 *
 * @author peng.zhang
 * @date 2021/3/3
 */
public interface GeneratorService {

    /**
     * 查询当前用户的 CodeGlobalConfigDto 配置
     *
     * @return
     */
    CodeGlobalConfigDto findGlobalConfigByCurrentUser();

    /**
     * 保存当前用户的 CodeGlobalConfigDto 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveGlobalConfigByCurrentUser(CodeGlobalConfig entity);

}
