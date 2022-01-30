package io.github.dunwu.module.code.service;

import io.github.dunwu.module.code.entity.CodeColumnConfig;
import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.CodeTableConfig;
import io.github.dunwu.module.code.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.module.code.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.module.code.entity.dto.CodeTableConfigDto;
import io.github.dunwu.module.code.entity.dto.TableColumnInfoDto;
import io.github.dunwu.module.code.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.engine.CodeGenerateContentDto;

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
     * @param query 查询实体
     * @return /
     */
    CodeGlobalConfigDto queryOrCreateGlobalConfig(CodeGlobalConfigQuery query);

    /**
     * 根据条件查询表配置，如果不存在，返回默认配置
     *
     * @param query 查询实体
     * @return /
     */
    CodeTableConfigDto queryOrCreateCodeTableConfig(CodeTableConfigQuery query);

    /**
     * 查询当前用户的 {@link CodeColumnConfigDto} 配置
     * <ul>
     *     <li>如果已经存在指定的数据表的列属性配置，则直接返回</li>
     *     <li>如果不存在指定的数据表的列属性配置，则查询指定表的实际属性，并组装默认的配置后返回结果</li>
     * </ul>
     *
     * @param query 查询实体
     * @return /
     */
    List<CodeColumnConfigDto> queryOrCreateColumnConfigList(CodeColumnConfigQuery query);

    List<CodeColumnConfigDto> syncQueryColumnConfigList(CodeTableConfigQuery query);

    /**
     * 保存当前用户的 CodeGlobalConfigDto 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveGlobalConfig(CodeGlobalConfig entity);

    // /**
    //  * 查询当前用户的 {@link CodeTableConfigDto} 配置
    //  *
    //  * @param query 查询实体
    //  * @return /
    //  */
    // CodeTableConfigDto queryTableConfig(CodeTableConfigQuery query);

    /**
     * 保存当前用户的 {@link CodeTableConfig} 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveTableConfig(CodeTableConfig entity);

    /**
     * 保存当前用户的 {@link CodeColumnConfig} 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveColumnConfigList(TableColumnInfoDto entity);

    /**
     * 根据表级别配置、列级别配置生成代码
     *
     * @param query 查询实体
     * @return /
     */
    ConfigBuilder generateCode(CodeTableConfigQuery query);

    /**
     * 根据表级别配置、列级别配置生成代码并下载到前端
     *
     * @param query    查询实体
     * @param request  http 请求
     * @param response http 应答
     * @return /
     */
    ConfigBuilder downloadCode(CodeTableConfigQuery query, HttpServletRequest request,
        HttpServletResponse response);

    /**
     * 根据表级别配置、列级别配置返回生成代码的预览内容
     *
     * @param query 查询实体
     * @return /
     */
    List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery query);

}
