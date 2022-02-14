package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.RoleDao;
import io.github.dunwu.module.cas.dao.mapper.RoleMapper;
import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 角色表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Dao
public class RoleDaoImpl extends BaseExtDaoImpl<RoleMapper, Role> implements RoleDao {

}
