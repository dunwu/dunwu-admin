package io.github.dunwu.module.mnt.service;

import io.github.dunwu.module.mnt.entity.DeployHistory;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.query.DeployHistoryQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 部署历史管理 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public interface DeployHistoryService extends IService {

    /**
     * 添加一条 {@link DeployHistory} 记录
     *
     * @param entity {@link DeployHistory} 数据实体
     * @return true / false
     */
    boolean insert(DeployHistory entity);

    /**
     * 批量添加 {@link DeployHistory} 记录
     *
     * @param list {@link DeployHistory} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<DeployHistory> list);

    /**
     * 根据 ID 更新一条 {@link DeployHistory} 记录
     *
     * @param entity {@link DeployHistory} 数据实体
     * @return true / false
     */
    boolean updateById(DeployHistory entity);

    /**
     * 根据 ID 批量更新 {@link DeployHistory} 记录
     *
     * @param list {@link DeployHistory} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<DeployHistory> list);

    /**
     * 添加或更新一条 {@link DeployHistory} 记录
     *
     * @param entity {@link DeployHistory} 数据实体
     * @return true / false
     */
    boolean save(DeployHistory entity);

    /**
     * 批量添加或更新 {@link DeployHistory} 记录
     *
     * @param list {@link DeployHistory} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<DeployHistory> list);

    /**
     * 根据 ID 删除一条 {@link DeployHistory} 记录
     *
     * @param id {@link DeployHistory} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link DeployHistory} 记录
     *
     * @param ids {@link DeployHistory} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DeployHistoryDto} 全量数据列表
     *
     * @return {@link List<DeployHistoryDto>}
     */
    List<DeployHistoryDto> pojoList();

    /**
     * 根据 {@link DeployHistoryQuery} 查询 {@link DeployHistoryDto} 列表
     *
     * @param query 查询条件，根据 {@link DeployHistoryQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DeployHistoryDto>}
     */
    List<DeployHistoryDto> pojoListByQuery(DeployHistoryQuery query);

    /**
     * 根据 {@link DeployHistoryQuery} 和 {@link Pageable} 分页查询 {@link DeployHistoryDto} 列表
     *
     * @param query    查询条件，根据 {@link DeployHistoryQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<DeployHistoryDto>}
     */
    Page<DeployHistoryDto> pojoSpringPageByQuery(DeployHistoryQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link DeployHistoryDto}
     *
     * @param id {@link DeployHistory} 主键
     * @return {@link DeployHistoryDto}
     */
    DeployHistoryDto pojoById(Serializable id);

    /**
     * 根据 {@link DeployHistoryQuery} 查询 {@link DeployHistoryDto} 列表
     *
     * @param query 查询条件，根据 {@link DeployHistoryQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DeployHistoryDto}
     */
    DeployHistoryDto pojoByQuery(DeployHistoryQuery query);

    /**
     * 根据 {@link DeployHistoryQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DeployHistoryQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DeployHistoryQuery query);

    /**
     * 根据 id 列表查询 {@link DeployHistoryDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link DeployHistoryQuery} 和 {@link Pageable} 分页查询 {@link DeployHistoryDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link DeployHistoryQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(DeployHistoryQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link DeployHistory} 转为 {@link DeployHistoryDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DeployHistoryDto doToDto(DeployHistory entity);

    /**
     * 将 {@link DeployHistoryDto} 转为 {@link DeployHistory}
     *
     * @param dto Dto 实体
     * @return /
     */
    DeployHistory dtoToDo(DeployHistoryDto dto);

}
