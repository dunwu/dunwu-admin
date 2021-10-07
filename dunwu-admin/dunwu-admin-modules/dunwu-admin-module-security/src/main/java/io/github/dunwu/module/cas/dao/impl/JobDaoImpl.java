package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.dao.mapper.JobMapper;
import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统岗位信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class JobDaoImpl extends BaseExtDaoImpl<JobMapper, Job> implements JobDao {

}
