package io.github.dunwu.module.cas.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.JobRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.JobRoleMapMapper;
import io.github.dunwu.module.cas.entity.JobRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 岗位角色关联 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Dao
public class JobRoleMapDaoImpl extends BaseExtDaoImpl<JobRoleMapMapper, JobRoleMap> implements JobRoleMapDao {

    @Override
    public boolean insertBatchByRoleIds(Long jobId, Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }

        Set<? extends Serializable> set = getRoleIdsByJobId(jobId);
        roleIds.removeIf(set::contains);
        if (CollectionUtil.isEmpty(roleIds)) {
            return true;
        }
        List<JobRoleMap> list = roleIds.stream().map(i -> new JobRoleMap(jobId, i))
                                       .collect(Collectors.toList());
        return insertBatch(list);
    }

    @Override
    public boolean deleteBatchByRoleIds(Long jobId, Collection<Long> roleIds) {
        QueryWrapper<JobRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(JobRoleMap.JOB_ID, jobId)
               .in(JobRoleMap.ROLE_ID, roleIds);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByJobId(Serializable jobId) {
        QueryWrapper<JobRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(JobRoleMap.JOB_ID, jobId);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByRoleId(Serializable roleId) {
        QueryWrapper<JobRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(JobRoleMap.ROLE_ID, roleId);
        return delete(wrapper);
    }

    @Override
    public Set<? extends Serializable> getRoleIdsByJobId(Serializable jobId) {
        QueryWrapper<JobRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(JobRoleMap.JOB_ID, jobId);
        List<JobRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(JobRoleMap::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<? extends Serializable> getJobIdsByRoleId(Serializable roleId) {
        QueryWrapper<JobRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(JobRoleMap.ROLE_ID, roleId);
        List<JobRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(JobRoleMap::getJobId).collect(Collectors.toSet());
    }

}
