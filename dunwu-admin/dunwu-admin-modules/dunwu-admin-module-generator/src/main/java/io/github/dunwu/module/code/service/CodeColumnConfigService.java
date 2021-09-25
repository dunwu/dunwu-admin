package io.github.dunwu.module.code.service;

import io.github.dunwu.module.code.entity.CodeColumnConfig;
import io.github.dunwu.module.code.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.module.code.entity.query.CodeColumnConfigQuery;
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
 * 代码生成-字段级别配置 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
public interface CodeColumnConfigService extends IService {

    /**
     * 添加一条 {@link CodeColumnConfig} 记录
     *
     * @param entity {@link CodeColumnConfig} 数据实体
     * @return true / false
     */
    boolean insert(CodeColumnConfig entity);

    /**
     * 批量添加 {@link CodeColumnConfig} 记录
     *
     * @param list {@link CodeColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<CodeColumnConfig> list);

    /**
     * 根据 ID 更新一条 {@link CodeColumnConfig} 记录
     *
     * @param entity {@link CodeColumnConfig} 数据实体
     * @return true / false
     */
    boolean updateById(CodeColumnConfig entity);

    /**
     * 根据 ID 批量更新 {@link CodeColumnConfig} 记录
     *
     * @param list {@link CodeColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<CodeColumnConfig> list);

    /**
     * 添加或更新一条 {@link CodeColumnConfig} 记录
     *
     * @param entity {@link CodeColumnConfig} 数据实体
     * @return true / false
     */
    boolean save(CodeColumnConfig entity);

    /**
     * 批量添加或更新 {@link CodeColumnConfig} 记录
     *
     * @param list {@link CodeColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<CodeColumnConfig> list);

    /**
     * 根据 ID 删除一条 {@link CodeColumnConfig} 记录
     *
     * @param id {@link CodeColumnConfig} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link CodeColumnConfig} 记录
     *
     * @param ids {@link CodeColumnConfig} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link CodeColumnConfigQuery} 查询 {@link CodeColumnConfigDto} 列表
     *
     * @param query 查询条件，根据 CodeColumnConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeColumnConfigDto>}
     */
    List<CodeColumnConfigDto> pojoListByQuery(CodeColumnConfigQuery query);

    /**
     * 根据 {@link CodeColumnConfigQuery} 和 {@link Pageable} 分页查询 {@link CodeColumnConfigDto} 列表
     *
     * @param query    查询条件，根据 CodeColumnConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<CodeColumnConfigDto>}
     */
    Page<CodeColumnConfigDto> pojoPageByQuery(CodeColumnConfigQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link CodeColumnConfigDto}
     *
     * @param id {@link CodeColumnConfig} 主键
     * @return {@link List<CodeColumnConfigDto>}
     */
    CodeColumnConfigDto pojoById(Serializable id);

    /**
     * 根据 {@link CodeColumnConfigQuery} 查询 {@link CodeColumnConfigDto} 列表
     *
     * @param query 查询条件，根据 CodeColumnConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeColumnConfigDto>}
     */
    CodeColumnConfigDto pojoByQuery(CodeColumnConfigQuery query);

    /**
     * 根据 {@link CodeColumnConfigQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 CodeColumnConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(CodeColumnConfigQuery query);

    /**
     * 根据 id 列表查询 {@link CodeColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 {@link CodeColumnConfigQuery} 和 {@link Pageable} 分页查询 {@link CodeColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 CodeColumnConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(CodeColumnConfigQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * 将 {@link CodeColumnConfig} 转为 {@link CodeColumnConfigDto}
     *
     * @param model 数据实体
     * @return /
     */
    CodeColumnConfigDto doToDto(CodeColumnConfig model);

    /**
     * 将 {@link CodeColumnConfigDto} 转为 {@link CodeColumnConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    CodeColumnConfig dtoToDo(CodeColumnConfigDto dto);

}
