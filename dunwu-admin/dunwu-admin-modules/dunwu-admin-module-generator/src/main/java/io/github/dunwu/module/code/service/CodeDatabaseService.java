package io.github.dunwu.module.code.service;

import io.github.dunwu.tool.data.core.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import io.github.dunwu.module.code.entity.query.CodeDatabaseQuery;
import io.github.dunwu.module.code.entity.CodeDatabase;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据库管理 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
public interface CodeDatabaseService extends IService {

    /**
     * 添加一条 {@link CodeDatabase} 记录
     *
     * @param entity {@link CodeDatabase} 数据实体
     * @return true / false
     */
    boolean insert(CodeDatabase entity);

    /**
     * 批量添加 {@link CodeDatabase} 记录
     *
     * @param list {@link CodeDatabase} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<CodeDatabase> list);

    /**
     * 根据 ID 更新一条 {@link CodeDatabase} 记录
     *
     * @param entity {@link CodeDatabase} 数据实体
     * @return true / false
     */
    boolean updateById(CodeDatabase entity);

    /**
     * 根据 ID 批量更新 {@link CodeDatabase} 记录
     *
     * @param list {@link CodeDatabase} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<CodeDatabase> list);

    /**
     * 添加或更新一条 {@link CodeDatabase} 记录
     *
     * @param entity {@link CodeDatabase} 数据实体
     * @return true / false
     */
    boolean save(CodeDatabase entity);

    /**
     * 批量添加或更新 {@link CodeDatabase} 记录
     *
     * @param list {@link CodeDatabase} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<CodeDatabase> list);

    /**
     * 根据 ID 删除一条 {@link CodeDatabase} 记录
     *
     * @param id {@link CodeDatabase} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link CodeDatabase} 记录
     *
     * @param ids {@link CodeDatabase} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link CodeDatabaseQuery} 查询 {@link CodeDatabaseDto} 列表
     *
     * @param query 查询条件，根据 CodeDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeDatabaseDto>}
     */
    List<CodeDatabaseDto> pojoListByQuery(CodeDatabaseQuery query);

    /**
     * 根据 {@link CodeDatabaseQuery} 和 {@link Pageable} 分页查询 {@link CodeDatabaseDto} 列表
     *
     * @param query    查询条件，根据 CodeDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<CodeDatabaseDto>}
     */
    Page<CodeDatabaseDto> pojoPageByQuery(CodeDatabaseQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link CodeDatabaseDto}
     *
     * @param id {@link CodeDatabase} 主键
     * @return {@link List<CodeDatabaseDto>}
     */
    CodeDatabaseDto pojoById(Serializable id);

    /**
     * 根据 {@link CodeDatabaseQuery} 查询 {@link CodeDatabaseDto} 列表
     *
     * @param query 查询条件，根据 CodeDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeDatabaseDto>}
     */
    CodeDatabaseDto pojoByQuery(CodeDatabaseQuery query);

    /**
     * 根据 {@link CodeDatabaseQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 CodeDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(CodeDatabaseQuery query);

    /**
     * 根据 id 列表查询 {@link CodeDatabaseDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 {@link CodeDatabaseQuery} 和 {@link Pageable} 分页查询 {@link CodeDatabaseDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 CodeDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(CodeDatabaseQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * 将 {@link CodeDatabase} 转为 {@link CodeDatabaseDto}
     *
     * @param model 数据实体
     * @return /
     */
    CodeDatabaseDto doToDto(CodeDatabase model);

    /**
     * 将 {@link CodeDatabaseDto} 转为 {@link CodeDatabase}
     *
     * @param dto Dto 实体
     * @return /
     */
    CodeDatabase dtoToDo(CodeDatabaseDto dto);

    boolean testConnection(CodeDatabaseDto dto);

}
