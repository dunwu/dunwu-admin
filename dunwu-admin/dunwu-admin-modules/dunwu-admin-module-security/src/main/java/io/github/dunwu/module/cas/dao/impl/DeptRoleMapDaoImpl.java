package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.DeptRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.DeptRoleMapMapper;
import io.github.dunwu.module.cas.entity.DeptRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 部门角色关联 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Dao
public class DeptRoleMapDaoImpl extends BaseExtDaoImpl<DeptRoleMapMapper, DeptRoleMap> implements DeptRoleMapDao {

}
