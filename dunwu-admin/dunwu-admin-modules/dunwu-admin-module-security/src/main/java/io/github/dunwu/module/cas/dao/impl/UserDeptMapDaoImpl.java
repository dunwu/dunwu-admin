package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.module.cas.dao.UserDeptMapDao;
import io.github.dunwu.module.cas.dao.mapper.UserDeptMapMapper;
import io.github.dunwu.module.cas.entity.UserDeptMap;

/**
 * 用户部门关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@Dao
public class UserDeptMapDaoImpl extends BaseExtDaoImpl<UserDeptMapMapper, UserDeptMap> implements UserDeptMapDao {

}
