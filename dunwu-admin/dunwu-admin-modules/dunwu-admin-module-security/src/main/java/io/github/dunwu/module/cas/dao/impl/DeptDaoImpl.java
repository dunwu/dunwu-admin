package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.DeptDao;
import io.github.dunwu.module.cas.dao.mapper.DeptMapper;
import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 部门表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Dao
public class DeptDaoImpl extends BaseExtDaoImpl<DeptMapper, Dept> implements DeptDao {

}
