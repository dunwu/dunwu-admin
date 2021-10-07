package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.RoleDao;
import io.github.dunwu.module.cas.dao.mapper.RoleMapper;
import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统角色信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class RoleDaoImpl extends BaseExtDaoImpl<RoleMapper, Role> implements RoleDao {

}
