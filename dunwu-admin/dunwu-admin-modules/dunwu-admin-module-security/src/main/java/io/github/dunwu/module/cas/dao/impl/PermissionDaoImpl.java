package io.github.dunwu.module.cas.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.PermissionDao;
import io.github.dunwu.module.cas.dao.mapper.PermissionMapper;
import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Dao
public class PermissionDaoImpl extends BaseExtDaoImpl<PermissionMapper, Permission> implements PermissionDao {

    @Override
    public boolean saveByResourceId(Permission entity) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq(Permission.RESOURCE_ID, entity.getResourceId());

        Permission record = getOne(wrapper);
        if (record == null) {
            return insert(entity);
        } else {
            entity.setId(record.getId());
            return updateById(entity);
        }
    }

    @Override
    public boolean deleteByResourceId(Serializable resourceId) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq(Permission.RESOURCE_ID, resourceId);
        return delete(wrapper);
    }

    @Override
    public boolean deleteBatchByResourceIds(Collection<? extends Serializable> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return false;
        }
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.in(Permission.RESOURCE_ID, resourceIds);
        List<Permission> list = list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        Set<Long> ids = list.stream().map(Permission::getId).collect(Collectors.toSet());
        return deleteBatchByIds(ids);
    }

    @Override
    public Permission getByResourceId(Serializable resourceId) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq(Permission.RESOURCE_ID, resourceId);
        return getOne(wrapper);
    }

    @Override
    public List<Permission> listByResourceIds(Collection<? extends Serializable> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.in(Permission.RESOURCE_ID, resourceIds);
        return list(wrapper);
    }

    @Override
    public List<Long> getIdListByResourceIds(Collection<? extends Serializable> resourceIds) {
        List<Permission> permissions = listByResourceIds(resourceIds);
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }
        return permissions.stream().map(Permission::getId).distinct().collect(Collectors.toList());
    }

}
