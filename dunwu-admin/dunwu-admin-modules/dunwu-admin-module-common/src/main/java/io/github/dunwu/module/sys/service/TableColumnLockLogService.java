package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.TableColumnLockLog;
import io.github.dunwu.module.sys.entity.dto.TableColumnLockLogDto;
import io.github.dunwu.module.sys.entity.query.TableColumnLockLogQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import io.github.dunwu.tool.data.request.PageQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 表字段锁定记录表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
public interface TableColumnLockLogService extends IService {

    /**
     * 添加一条 {@link TableColumnLockLog} 记录
     *
     * @param entity {@link TableColumnLockLog} 数据实体
     * @return true / false
     */
    boolean insert(TableColumnLockLog entity);

    /**
     * 批量添加 {@link TableColumnLockLog} 记录
     *
     * @param list {@link TableColumnLockLog} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<TableColumnLockLog> list);

    /**
     * 根据 ID 更新一条 {@link TableColumnLockLog} 记录
     *
     * @param entity {@link TableColumnLockLog} 数据实体
     * @return true / false
     */
    boolean updateById(TableColumnLockLog entity);

    /**
     * 根据 ID 批量更新 {@link TableColumnLockLog} 记录
     *
     * @param list {@link TableColumnLockLog} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<TableColumnLockLog> list);

    /**
     * 添加或更新一条 {@link TableColumnLockLog} 记录
     *
     * @param entity {@link TableColumnLockLog} 数据实体
     * @return true / false
     */
    boolean save(TableColumnLockLog entity);

    /**
     * 批量添加或更新 {@link TableColumnLockLog} 记录
     *
     * @param list {@link TableColumnLockLog} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<TableColumnLockLog> list);

    /**
     * 根据 ID 删除一条 {@link TableColumnLockLog} 记录
     *
     * @param id {@link TableColumnLockLog} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link TableColumnLockLog} 记录
     *
     * @param ids {@link TableColumnLockLog} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link TableColumnLockLogDto} 全量数据列表
     *
     * @return {@link List<TableColumnLockLogDto>}
     */
    List<TableColumnLockLogDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link TableColumnLockLogDto} 列表
     *
     * @param ids {@link TableColumnLockLog} 主键列表
     * @return {@link List<TableColumnLockLogDto>}
     */
    List<TableColumnLockLogDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link TableColumnLockLogQuery} 查询 {@link TableColumnLockLogDto} 列表
     *
     * @param query 查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<TableColumnLockLogDto>}
     */
    List<TableColumnLockLogDto> pojoListByQuery(TableColumnLockLogQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnLockLogQuery} 分页查询 {@link TableColumnLockLogDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<TableColumnLockLogDto>}
     */
    Page<TableColumnLockLogDto> pojoSpringPageByQuery(Pageable pageable, TableColumnLockLogQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnLockLogQuery} 分页查询 {@link TableColumnLockLogDto} 列表
     *
     * @param pageQuery 分页查询条件
     * @param query     查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<TableColumnLockLogDto>}
     */
    Page<TableColumnLockLogDto> pojoSpringPageByQuery(PageQuery pageQuery, TableColumnLockLogQuery query);

    /**
     * 根据 id 查询 {@link TableColumnLockLogDto}
     *
     * @param id {@link TableColumnLockLog} 主键
     * @return {@link TableColumnLockLogDto}
     */
    TableColumnLockLogDto pojoById(Serializable id);

    /**
     * 根据 {@link TableColumnLockLogQuery} 查询 {@link TableColumnLockLogDto} 列表
     *
     * @param query 查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link TableColumnLockLogDto}
     */
    TableColumnLockLogDto pojoByQuery(TableColumnLockLogQuery query);

    /**
     * 根据 {@link TableColumnLockLogQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(TableColumnLockLogQuery query);

    /**
     * 导入 excel 表单，将表单数据转为数据实体并存储
     *
     * @param file {@link MultipartFile} 实体
     */
    void importList(MultipartFile file);

    /**
     * 根据 id 列表查询 {@link TableColumnLockLogDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnLockLogQuery} 分页查询 {@link TableColumnLockLogDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, TableColumnLockLogQuery query, HttpServletResponse response);

    /**
     * 根据 {@link PageQuery} 和 {@link TableColumnLockLogQuery} 分页查询 {@link TableColumnLockLogDto} 列表，并导出 excel 表单
     *
     * @param pageQuery 分页查询条件
     * @param query     查询条件，根据 {@link TableColumnLockLogQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response  {@link HttpServletResponse} 实体
     */
    void exportPage(PageQuery pageQuery, TableColumnLockLogQuery query, HttpServletResponse response);

    /**
     * 将 {@link TableColumnLockLog} 转为 {@link TableColumnLockLogDto}
     *
     * @param entity 数据实体
     * @return /
     */
    TableColumnLockLogDto doToDto(TableColumnLockLog entity);

    /**
     * 将 {@link TableColumnLockLogDto} 转为 {@link TableColumnLockLog}
     *
     * @param dto Dto 实体
     * @return /
     */
    TableColumnLockLog dtoToDo(TableColumnLockLogDto dto);

}
