package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.UserRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.UserRoleMapMapper;
import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 用户角色关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Dao
public class UserRoleMapDaoImpl extends BaseExtDaoImpl<UserRoleMapMapper, UserRoleMap> implements UserRoleMapDao {

}
