package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.PermissionDao;
import io.github.dunwu.module.cas.dao.mapper.PermissionMapper;
import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 权限表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Dao
public class PermissionDaoImpl extends BaseExtDaoImpl<PermissionMapper, Permission> implements PermissionDao {

}
