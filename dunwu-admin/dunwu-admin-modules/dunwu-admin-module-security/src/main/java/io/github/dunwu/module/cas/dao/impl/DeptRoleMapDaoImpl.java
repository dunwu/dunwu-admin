package io.github.dunwu.module.cas.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.DeptRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.DeptRoleMapMapper;
import io.github.dunwu.module.cas.entity.DeptRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门角色关联 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Dao
public class DeptRoleMapDaoImpl extends BaseExtDaoImpl<DeptRoleMapMapper, DeptRoleMap> implements DeptRoleMapDao {

    @Override
    public Set<? extends Serializable> getRoleIdsByDeptId(Serializable deptId) {
        QueryWrapper<DeptRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptRoleMap.DEPT_ID, deptId);
        List<DeptRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptRoleMap::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<? extends Serializable> getDeptIdsByRoleId(Serializable roleId) {
        QueryWrapper<DeptRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptRoleMap.ROLE_ID, roleId);
        List<DeptRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptRoleMap::getDeptId).collect(Collectors.toSet());
    }

}
