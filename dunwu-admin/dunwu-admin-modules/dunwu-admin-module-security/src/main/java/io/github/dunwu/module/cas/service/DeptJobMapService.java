package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.DeptJobMap;
import io.github.dunwu.module.cas.entity.dto.DeptJobMapDto;
import io.github.dunwu.module.cas.entity.query.DeptJobMapQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色部门关联 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-10
 */
public interface DeptJobMapService extends IService {

    /**
     * 添加一条 {@link DeptJobMap} 记录
     *
     * @param entity {@link DeptJobMap} 数据实体
     * @return true / false
     */
    boolean insert(DeptJobMap entity);

    /**
     * 批量添加 {@link DeptJobMap} 记录
     *
     * @param list {@link DeptJobMap} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<DeptJobMap> list);

    /**
     * 根据 ID 更新一条 {@link DeptJobMap} 记录
     *
     * @param entity {@link DeptJobMap} 数据实体
     * @return true / false
     */
    boolean updateById(DeptJobMap entity);

    /**
     * 根据 ID 批量更新 {@link DeptJobMap} 记录
     *
     * @param list {@link DeptJobMap} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<DeptJobMap> list);

    /**
     * 添加或更新一条 {@link DeptJobMap} 记录
     *
     * @param entity {@link DeptJobMap} 数据实体
     * @return true / false
     */
    boolean save(DeptJobMap entity);

    /**
     * 批量添加或更新 {@link DeptJobMap} 记录
     *
     * @param list {@link DeptJobMap} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<DeptJobMap> list);

    /**
     * 根据 ID 删除一条 {@link DeptJobMap} 记录
     *
     * @param id {@link DeptJobMap} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link DeptJobMap} 记录
     *
     * @param ids {@link DeptJobMap} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DeptJobMapDto} 全量数据列表
     *
     * @return {@link List<DeptJobMapDto>}
     */
    List<DeptJobMapDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link DeptJobMapDto} 列表
     *
     * @param ids {@link DeptJobMap} 主键列表
     * @return {@link List<DeptJobMapDto>}
     */
    List<DeptJobMapDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link DeptJobMapQuery} 查询 {@link DeptJobMapDto} 列表
     *
     * @param query 查询条件，根据 {@link DeptJobMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DeptJobMapDto>}
     */
    List<DeptJobMapDto> pojoListByQuery(DeptJobMapQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link DeptJobMapQuery} 分页查询 {@link DeptJobMapDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DeptJobMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<DeptJobMapDto>}
     */
    Page<DeptJobMapDto> pojoSpringPageByQuery(Pageable pageable, DeptJobMapQuery query);

    /**
     * 根据 id 查询 {@link DeptJobMapDto}
     *
     * @param id {@link DeptJobMap} 主键
     * @return {@link DeptJobMapDto}
     */
    DeptJobMapDto pojoById(Serializable id);

    /**
     * 根据 {@link DeptJobMapQuery} 查询 {@link DeptJobMapDto} 列表
     *
     * @param query 查询条件，根据 {@link DeptJobMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DeptJobMapDto}
     */
    DeptJobMapDto pojoByQuery(DeptJobMapQuery query);

    /**
     * 根据 {@link DeptJobMapQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DeptJobMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DeptJobMapQuery query);

    /**
     * 根据 id 列表查询 {@link DeptJobMapDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link DeptJobMapQuery} 分页查询 {@link DeptJobMapDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DeptJobMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, DeptJobMapQuery query, HttpServletResponse response);

    /**
     * 将 {@link DeptJobMap} 转为 {@link DeptJobMapDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DeptJobMapDto doToDto(DeptJobMap entity);

    /**
     * 将 {@link DeptJobMapDto} 转为 {@link DeptJobMap}
     *
     * @param dto Dto 实体
     * @return /
     */
    DeptJobMap dtoToDo(DeptJobMapDto dto);

    /**
     * 批量插入 jobIds 和 deptId 关联的记录
     *
     * @param deptId 部门ID
     * @return true / false
     */
    boolean insertBatchByJobIds(Long deptId, Collection<Long> jobIds);

    /**
     * 批量删除 jobIds 和 deptId 关联的记录
     *
     * @param deptId 部门ID
     * @return true / false
     */
    boolean deleteBatchByJobIds(Long deptId, Collection<Long> jobIds);

    /**
     * 删除所有 deptId 相关的记录
     *
     * @param deptId 部门ID
     * @return true / false
     */
    boolean deleteByDeptId(Serializable deptId);

    /**
     * 删除所有 jobId 相关的记录
     *
     * @param jobId 职务ID
     * @return true / false
     */
    boolean deleteByJobId(Serializable jobId);

    /**
     * 根据 deptId 获取关联的 jobId 列表
     *
     * @param deptId 部门ID
     * @return /
     */
    Set<? extends Serializable> getJobIdsByDeptId(Serializable deptId);

    /**
     * 根据 jobId 获取关联的 deptId 列表
     *
     * @param jobId 职务ID
     * @return /
     */
    Set<? extends Serializable> getDeptIdsByJobId(Serializable jobId);

}
