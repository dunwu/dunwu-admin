package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 职务表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface JobService extends IService {

    /**
     * 添加一条 {@link Job} 记录
     *
     * @param entity {@link Job} 数据实体
     * @return true / false
     */
    boolean insert(Job entity);

    /**
     * 批量添加 {@link Job} 记录
     *
     * @param list {@link Job} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Job> list);

    /**
     * 根据 ID 更新一条 {@link Job} 记录
     *
     * @param entity {@link Job} 数据实体
     * @return true / false
     */
    boolean updateById(Job entity);

    /**
     * 根据 ID 批量更新 {@link Job} 记录
     *
     * @param list {@link Job} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Job> list);

    /**
     * 添加或更新一条 {@link Job} 记录
     *
     * @param entity {@link Job} 数据实体
     * @return true / false
     */
    boolean save(Job entity);

    /**
     * 批量添加或更新 {@link Job} 记录
     *
     * @param list {@link Job} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Job> list);

    /**
     * 根据 ID 删除一条 {@link Job} 记录
     *
     * @param id {@link Job} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Job} 记录
     *
     * @param ids {@link Job} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link JobDto} 全量数据列表
     *
     * @return {@link List<JobDto>}
     */
    List<JobDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link JobDto} 列表
     *
     * @param ids {@link Job} 主键列表
     * @return {@link List<JobDto>}
     */
    List<JobDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link JobQuery} 查询 {@link JobDto} 列表
     *
     * @param query 查询条件，根据 {@link JobQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<JobDto>}
     */
    List<JobDto> pojoListByQuery(JobQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link JobQuery} 分页查询 {@link JobDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link JobQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<JobDto>}
     */
    Page<JobDto> pojoSpringPageByQuery(Pageable pageable, JobQuery query);

    /**
     * 根据 id 查询 {@link JobDto}
     *
     * @param id {@link Job} 主键
     * @return {@link JobDto}
     */
    JobDto pojoById(Serializable id);

    /**
     * 根据 {@link JobQuery} 查询 {@link JobDto} 列表
     *
     * @param query 查询条件，根据 {@link JobQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link JobDto}
     */
    JobDto pojoByQuery(JobQuery query);

    /**
     * 根据 {@link JobQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link JobQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(JobQuery query);

    /**
     * 根据 id 列表查询 {@link JobDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link JobQuery} 分页查询 {@link JobDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link JobQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, JobQuery query, HttpServletResponse response);

    /**
     * 将 {@link Job} 转为 {@link JobDto}
     *
     * @param entity 数据实体
     * @return /
     */
    JobDto doToDto(Job entity);

    /**
     * 将 {@link JobDto} 转为 {@link Job}
     *
     * @param dto Dto 实体
     * @return /
     */
    Job dtoToDo(JobDto dto);

    boolean bindDept(Long deptId, Collection<Long> jobIds);

    boolean unbindDept(Long deptId, Collection<Long> jobIds);

}
