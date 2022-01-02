package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.OperationLog;
import io.github.dunwu.module.sys.entity.dto.OperationLogDto;
import io.github.dunwu.module.sys.entity.query.OperationLogQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
public interface OperationLogService extends IService {

    /**
     * 添加一条 {@link OperationLog} 记录
     *
     * @param entity {@link OperationLog} 数据实体
     * @return true / false
     */
    boolean insert(OperationLog entity);

    /**
     * 批量添加 {@link OperationLog} 记录
     *
     * @param list {@link OperationLog} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<OperationLog> list);

    /**
     * 根据 ID 更新一条 {@link OperationLog} 记录
     *
     * @param entity {@link OperationLog} 数据实体
     * @return true / false
     */
    boolean updateById(OperationLog entity);

    /**
     * 根据 ID 批量更新 {@link OperationLog} 记录
     *
     * @param list {@link OperationLog} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<OperationLog> list);

    /**
     * 添加或更新一条 {@link OperationLog} 记录
     *
     * @param entity {@link OperationLog} 数据实体
     * @return true / false
     */
    boolean save(OperationLog entity);

    /**
     * 批量添加或更新 {@link OperationLog} 记录
     *
     * @param list {@link OperationLog} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<OperationLog> list);

    /**
     * 根据 ID 删除一条 {@link OperationLog} 记录
     *
     * @param id {@link OperationLog} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link OperationLog} 记录
     *
     * @param ids {@link OperationLog} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link OperationLogDto} 全量数据列表
     *
     * @return {@link List<OperationLogDto>}
     */
    List<OperationLogDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link OperationLogDto} 列表
     *
     * @param ids {@link OperationLog} 主键列表
     * @return {@link List<OperationLogDto>}
     */
    List<OperationLogDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link OperationLogQuery} 查询 {@link OperationLogDto} 列表
     *
     * @param query 查询条件，根据 {@link OperationLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<OperationLogDto>}
     */
    List<OperationLogDto> pojoListByQuery(OperationLogQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link OperationLogQuery} 分页查询 {@link OperationLogDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link OperationLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<OperationLogDto>}
     */
    Page<OperationLogDto> pojoSpringPageByQuery(Pageable pageable, OperationLogQuery query);

    /**
     * 根据 id 查询 {@link OperationLogDto}
     *
     * @param id {@link OperationLog} 主键
     * @return {@link OperationLogDto}
     */
    OperationLogDto pojoById(Serializable id);

    /**
     * 根据 {@link OperationLogQuery} 查询 {@link OperationLogDto} 列表
     *
     * @param query 查询条件，根据 {@link OperationLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link OperationLogDto}
     */
    OperationLogDto pojoByQuery(OperationLogQuery query);

    /**
     * 根据 {@link OperationLogQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link OperationLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(OperationLogQuery query);

    /**
     * 根据 id 列表查询 {@link OperationLogDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link OperationLogQuery} 分页查询 {@link OperationLogDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link OperationLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, OperationLogQuery query, HttpServletResponse response);

    /**
     * 将 {@link OperationLog} 转为 {@link OperationLogDto}
     *
     * @param entity 数据实体
     * @return /
     */
    OperationLogDto doToDto(OperationLog entity);

    /**
     * 将 {@link OperationLogDto} 转为 {@link OperationLog}
     *
     * @param dto Dto 实体
     * @return /
     */
    OperationLog dtoToDo(OperationLogDto dto);

}
