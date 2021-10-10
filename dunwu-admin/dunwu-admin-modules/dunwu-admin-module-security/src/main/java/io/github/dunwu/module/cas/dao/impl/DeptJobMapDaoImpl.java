package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.DeptJobMapDao;
import io.github.dunwu.module.cas.dao.mapper.DeptJobMapMapper;
import io.github.dunwu.module.cas.entity.DeptJobMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 角色部门关联 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-10
 */
@Dao
public class DeptJobMapDaoImpl extends BaseExtDaoImpl<DeptJobMapMapper, DeptJobMap> implements DeptJobMapDao {

}
