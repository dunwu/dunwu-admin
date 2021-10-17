package io.github.dunwu.module.cas.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.RolePermissionMapDao;
import io.github.dunwu.module.cas.dao.mapper.RolePermissionMapMapper;
import io.github.dunwu.module.cas.entity.RolePermissionMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
@Dao
public class RolePermissionMapDaoImpl extends BaseExtDaoImpl<RolePermissionMapMapper, RolePermissionMap>
    implements RolePermissionMapDao {

    @Override
    public boolean insertBatchByPermissionIds(Long roleId, Collection<Long> permissionIds) {
        if (CollectionUtil.isEmpty(permissionIds)) {
            return false;
        }

        Set<Long> set = getPermissionIdsByRoleId(roleId);
        if (CollectionUtil.isNotEmpty(set)) {
            permissionIds.removeIf(set::contains);
        }
        if (CollectionUtil.isEmpty(permissionIds)) {
            return true;
        }
        List<RolePermissionMap> list = permissionIds.stream().map(i -> new RolePermissionMap(roleId, i))
                                                    .collect(Collectors.toList());
        return insertBatch(list);
    }

    @Override
    public boolean deleteBatchByRoleIds(Long roleId, Collection<? extends Serializable> permissionIds) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.eq(RolePermissionMap.ROLE_ID, roleId)
               .in(RolePermissionMap.PERMISSION_ID, permissionIds);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByRoleId(Serializable roleId) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.eq(RolePermissionMap.ROLE_ID, roleId);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByPermissionId(Serializable permissionId) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.eq(RolePermissionMap.PERMISSION_ID, permissionId);
        return delete(wrapper);
    }

    @Override
    public Set<Long> getRoleIdsByPermissionId(Long permissionId) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.eq(RolePermissionMap.PERMISSION_ID, permissionId);
        List<RolePermissionMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(RolePermissionMap::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getPermissionIdsByRoleId(Long roleId) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.eq(RolePermissionMap.ROLE_ID, roleId);
        List<RolePermissionMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(RolePermissionMap::getPermissionId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getPermissionIdsByRoleIds(Collection<Long> roleIds) {
        QueryWrapper<RolePermissionMap> wrapper = new QueryWrapper<>();
        wrapper.in(RolePermissionMap.ROLE_ID, roleIds);
        List<RolePermissionMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream()
                   .distinct()
                   .map(RolePermissionMap::getPermissionId)
                   .collect(Collectors.toSet());
    }

}
