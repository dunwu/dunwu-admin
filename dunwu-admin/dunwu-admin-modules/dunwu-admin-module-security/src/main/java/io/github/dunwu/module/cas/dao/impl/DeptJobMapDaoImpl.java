package io.github.dunwu.module.cas.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.DeptJobMapDao;
import io.github.dunwu.module.cas.dao.mapper.DeptJobMapMapper;
import io.github.dunwu.module.cas.entity.DeptJobMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门职务关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Dao
public class DeptJobMapDaoImpl extends BaseExtDaoImpl<DeptJobMapMapper, DeptJobMap> implements DeptJobMapDao {

    @Override
    public boolean insertBatchByJobIds(Long deptId, Collection<Long> jobIds) {
        if (CollectionUtil.isEmpty(jobIds)) {
            return false;
        }

        Set<Long> set = getJobIdsByDeptId(deptId);
        jobIds.removeIf(set::contains);
        if (CollectionUtil.isEmpty(jobIds)) {
            return true;
        }

        List<DeptJobMap> list = jobIds.stream().map(i -> new DeptJobMap(deptId, i))
                                      .collect(Collectors.toList());
        return insertBatch(list);
    }

    @Override
    public boolean deleteBatchByJobIds(Long deptId, Collection<Long> jobIds) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId)
               .in(DeptJobMap.JOB_ID, jobIds);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByDeptId(Serializable deptId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByJobId(Serializable jobId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.JOB_ID, jobId);
        return delete(wrapper);
    }

    @Override
    public Set<Long> getJobIdsByDeptId(Long deptId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId);
        List<DeptJobMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptJobMap::getJobId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getDeptIdsByJobId(Long jobId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.JOB_ID, jobId);
        List<DeptJobMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptJobMap::getDeptId).collect(Collectors.toSet());
    }

}
