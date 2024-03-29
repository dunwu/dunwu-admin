package io.github.dunwu.module.code.service;

import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.module.code.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-全局配置 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
public interface CodeGlobalConfigService extends IService {

    /**
     * 添加一条 {@link CodeGlobalConfig} 记录
     *
     * @param entity {@link CodeGlobalConfig} 数据实体
     * @return true / false
     */
    boolean insert(CodeGlobalConfig entity);

    /**
     * 批量添加 {@link CodeGlobalConfig} 记录
     *
     * @param list {@link CodeGlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<CodeGlobalConfig> list);

    /**
     * 根据 ID 更新一条 {@link CodeGlobalConfig} 记录
     *
     * @param entity {@link CodeGlobalConfig} 数据实体
     * @return true / false
     */
    boolean updateById(CodeGlobalConfig entity);

    /**
     * 根据 ID 批量更新 {@link CodeGlobalConfig} 记录
     *
     * @param list {@link CodeGlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<CodeGlobalConfig> list);

    /**
     * 添加或更新一条 {@link CodeGlobalConfig} 记录
     *
     * @param entity {@link CodeGlobalConfig} 数据实体
     * @return true / false
     */
    boolean save(CodeGlobalConfig entity);

    /**
     * 批量添加或更新 {@link CodeGlobalConfig} 记录
     *
     * @param list {@link CodeGlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<CodeGlobalConfig> list);

    /**
     * 根据 ID 删除一条 {@link CodeGlobalConfig} 记录
     *
     * @param id {@link CodeGlobalConfig} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link CodeGlobalConfig} 记录
     *
     * @param ids {@link CodeGlobalConfig} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link CodeGlobalConfigQuery} 查询 {@link CodeGlobalConfigDto} 列表
     *
     * @param query 查询条件，根据 CodeGlobalConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeGlobalConfigDto>}
     */
    List<CodeGlobalConfigDto> pojoListByQuery(CodeGlobalConfigQuery query);

    /**
     * 根据 {@link CodeGlobalConfigQuery} 和 {@link Pageable} 分页查询 {@link CodeGlobalConfigDto} 列表
     *
     * @param query    查询条件，根据 CodeGlobalConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<CodeGlobalConfigDto>}
     */
    Page<CodeGlobalConfigDto> pojoSpringPageByQuery(CodeGlobalConfigQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link CodeGlobalConfigDto}
     *
     * @param id {@link CodeGlobalConfig} 主键
     * @return {@link List<CodeGlobalConfigDto>}
     */
    CodeGlobalConfigDto pojoById(Serializable id);

    /**
     * 根据 {@link CodeGlobalConfigQuery} 查询 {@link CodeGlobalConfigDto} 列表
     *
     * @param query 查询条件，根据 CodeGlobalConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeGlobalConfigDto>}
     */
    CodeGlobalConfigDto pojoByQuery(CodeGlobalConfigQuery query);

    /**
     * 根据 {@link CodeGlobalConfigQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 CodeGlobalConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(CodeGlobalConfigQuery query);

    /**
     * 根据 id 列表查询 {@link CodeGlobalConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 {@link CodeGlobalConfigQuery} 和 {@link Pageable} 分页查询 {@link CodeGlobalConfigDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 CodeGlobalConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(CodeGlobalConfigQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * 将 {@link CodeGlobalConfig} 转为 {@link CodeGlobalConfigDto}
     *
     * @param model 数据实体
     * @return /
     */
    CodeGlobalConfigDto doToDto(CodeGlobalConfig model);

    /**
     * 将 {@link CodeGlobalConfigDto} 转为 {@link CodeGlobalConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    CodeGlobalConfig dtoToDo(CodeGlobalConfigDto dto);

}
