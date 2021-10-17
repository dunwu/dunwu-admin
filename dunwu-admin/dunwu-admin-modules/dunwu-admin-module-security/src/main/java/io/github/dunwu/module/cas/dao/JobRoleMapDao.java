package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.JobRoleMap;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 岗位角色关联 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
public interface JobRoleMapDao extends IExtDao<JobRoleMap> {

    boolean insertBatchByRoleIds(Long jobId, Collection<Long> roleIds);

    boolean deleteBatchByRoleIds(Long jobId, Collection<Long> roleIds);

    boolean deleteByJobId(Serializable jobId);

    boolean deleteByRoleId(Serializable roleId);

    Set<? extends Serializable> getRoleIdsByJobId(Serializable jobId);

    Set<? extends Serializable> getJobIdsByRoleId(Serializable roleId);

}
