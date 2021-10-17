package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.DeptJobMap;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 部门职务关联表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
public interface DeptJobMapDao extends IExtDao<DeptJobMap> {

    /**
     * 批量插入 jobIds 和 deptId 关联的记录
     *
     * @param deptId 部门ID
     * @param jobIds 职务ID列表
     * @return true / false
     */
    boolean insertBatchByJobIds(Long deptId, Collection<Long> jobIds);

    /**
     * 批量删除 jobIds 和 deptId 关联的记录
     *
     * @param deptId 部门ID
     * @param jobIds 职务ID列表
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
    Set<Long> getJobIdsByDeptId(Long deptId);

    /**
     * 根据 jobId 获取关联的 deptId 列表
     *
     * @param jobId 职务ID
     * @return /
     */
    Set<Long> getDeptIdsByJobId(Long jobId);

}
