package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.DeptRoleMap;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Set;

/**
 * 部门角色关联 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
public interface DeptRoleMapDao extends IExtDao<DeptRoleMap> {

    Set<? extends Serializable> getRoleIdsByDeptId(Serializable deptId);

    Set<? extends Serializable> getDeptIdsByRoleId(Serializable roleId);

}
