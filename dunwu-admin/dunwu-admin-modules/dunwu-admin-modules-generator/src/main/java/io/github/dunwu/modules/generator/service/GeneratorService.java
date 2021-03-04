package io.github.dunwu.modules.generator.service;

import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    CodeTableConfigDto findTableConfigByCurrentUser(CodeTableConfigQuery query);

    boolean saveTableConfigByCurrentUser(CodeTableConfig entity);

    List<CodeGenerateContentDto> previewGenerateCode(CodeTableConfigQuery codeTableConfigQuery);

    void downloadGenerateCode(CodeTableConfigQuery codeTableConfigQuery, HttpServletRequest request,
        HttpServletResponse response);

}
