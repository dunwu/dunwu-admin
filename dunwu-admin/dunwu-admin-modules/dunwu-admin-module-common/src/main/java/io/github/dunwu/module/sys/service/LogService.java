package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.Log;
import io.github.dunwu.module.sys.entity.dto.LogDto;
import io.github.dunwu.module.sys.entity.query.LogQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import io.github.dunwu.tool.web.log.LogStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志记录 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
public interface LogService extends IService {

    /**
     * 添加一条 {@link Log} 记录
     *
     * @param entity {@link Log} 数据实体
     * @return true / false
     */
    boolean insert(Log entity);

    /**
     * 批量添加 {@link Log} 记录
     *
     * @param list {@link Log} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Log> list);

    /**
     * 根据 ID 更新一条 {@link Log} 记录
     *
     * @param entity {@link Log} 数据实体
     * @return true / false
     */
    boolean updateById(Log entity);

    /**
     * 根据 ID 批量更新 {@link Log} 记录
     *
     * @param list {@link Log} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Log> list);

    /**
     * 添加或更新一条 {@link Log} 记录
     *
     * @param entity {@link Log} 数据实体
     * @return true / false
     */
    boolean save(Log entity);

    /**
     * 批量添加或更新 {@link Log} 记录
     *
     * @param list {@link Log} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Log> list);

    /**
     * 根据 ID 删除一条 {@link Log} 记录
     *
     * @param id {@link Log} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Log} 记录
     *
     * @param ids {@link Log} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link LogDto} 全量数据列表
     *
     * @return {@link List<LogDto>}
     */
    List<LogDto> pojoList();

    /**
     * 根据 {@link LogQuery} 查询 {@link LogDto} 列表
     *
     * @param query 查询条件，根据 {@link LogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<LogDto>}
     */
    List<LogDto> pojoListByQuery(LogQuery query);

    /**
     * 根据 {@link LogQuery} 和 {@link Pageable} 分页查询 {@link LogDto} 列表
     *
     * @param query    查询条件，根据 {@link LogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<LogDto>}
     */
    Page<LogDto> pojoSpringPageByQuery(LogQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link LogDto}
     *
     * @param id {@link Log} 主键
     * @return {@link LogDto}
     */
    LogDto pojoById(Serializable id);

    /**
     * 根据 {@link LogQuery} 查询 {@link LogDto} 列表
     *
     * @param query 查询条件，根据 {@link LogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link LogDto}
     */
    LogDto pojoByQuery(LogQuery query);

    /**
     * 根据 {@link LogQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link LogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(LogQuery query);

    /**
     * 根据 id 列表查询 {@link LogDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link LogQuery} 和 {@link Pageable} 分页查询 {@link LogDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link LogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(LogQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link Log} 转为 {@link LogDto}
     *
     * @param entity 数据实体
     * @return /
     */
    LogDto doToDto(Log entity);

    /**
     * 将 {@link LogDto} 转为 {@link Log}
     *
     * @param dto Dto 实体
     * @return /
     */
    Log dtoToDo(LogDto dto);

}
