package io.github.dunwu.module.cas.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.UserRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.UserRoleMapMapper;
import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Dao
public class UserRoleMapDaoImpl extends BaseExtDaoImpl<UserRoleMapMapper, UserRoleMap> implements UserRoleMapDao {

    @Override
    public boolean insertBatchByRoleIds(Long userId, Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }

        Set<? extends Serializable> set = getRoleIdsByUserId(userId);
        roleIds.removeIf(set::contains);
        if (CollectionUtil.isEmpty(roleIds)) {
            return true;
        }
        List<UserRoleMap> list = roleIds.stream().map(i -> new UserRoleMap(userId, i))
                                        .collect(Collectors.toList());
        return insertBatch(list);
    }

    @Override
    public boolean deleteBatchByRoleIds(Long userId, Collection<Long> roleIds) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId)
               .in(UserRoleMap.ROLE_ID, roleIds);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByUserId(Serializable userId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId);
        return delete(wrapper);
    }

    @Override
    public boolean deleteByRoleId(Serializable roleId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.ROLE_ID, roleId);
        return delete(wrapper);
    }

    @Override
    public Set<? extends Serializable> getRoleIdsByUserId(Serializable userId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId);
        List<UserRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(UserRoleMap::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<? extends Serializable> getUserIdsByRoleId(Serializable roleId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.ROLE_ID, roleId);
        List<UserRoleMap> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }

        return list.stream().map(UserRoleMap::getUserId).collect(Collectors.toSet());
    }

}
