package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 权限表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface PermissionDao extends IExtDao<Permission> {

    boolean saveByResourceId(Permission entity);

    boolean deleteByResourceId(Serializable resourceId);

    boolean deleteBatchByResourceIds(Collection<? extends Serializable> resourceIds);

    Permission getByResourceId(Serializable resourceId);

    List<Permission> listByResourceIds(Collection<? extends Serializable> resourceIds);

    List<Long> getIdListByResourceIds(Collection<? extends Serializable> resourceIds);

}
