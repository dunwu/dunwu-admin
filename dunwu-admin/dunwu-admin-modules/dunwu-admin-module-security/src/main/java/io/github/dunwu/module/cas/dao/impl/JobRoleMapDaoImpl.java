package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.JobRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.JobRoleMapMapper;
import io.github.dunwu.module.cas.entity.JobRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 岗位角色关联 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Dao
public class JobRoleMapDaoImpl extends BaseExtDaoImpl<JobRoleMapMapper, JobRoleMap> implements JobRoleMapDao {

}
