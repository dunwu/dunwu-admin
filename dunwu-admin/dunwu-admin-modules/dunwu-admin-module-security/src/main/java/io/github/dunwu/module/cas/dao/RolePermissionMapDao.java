package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.RolePermissionMap;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 角色权限关联表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-17
 */
public interface RolePermissionMapDao extends IExtDao<RolePermissionMap> {

    /**
     * 批量插入 permissionIds 和 roleId 关联的记录
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return true / false
     */
    boolean insertBatchByPermissionIds(Long roleId, Collection<Long> permissionIds);

    /**
     * 批量删除 permissionIds 和 roleId 关联的记录
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID列表
     * @return true / false
     */
    boolean deleteBatchByRoleIds(Long roleId, Collection<? extends Serializable> permissionIds);

    /**
     * 删除所有 roleId 相关的记录
     *
     * @param roleId 角色ID
     * @return true / false
     */
    boolean deleteByRoleId(Serializable roleId);

    /**
     * 删除所有 permissionId 相关的记录
     *
     * @param permissionId 权限ID
     * @return true / false
     */
    boolean deleteByPermissionId(Serializable permissionId);

    /**
     * 根据 permissionId 获取关联的 roleId 列表
     *
     * @param permissionId 权限ID
     * @return /
     */
    Set<Long> getRoleIdsByPermissionId(Long permissionId);

    /**
     * 根据 roleId 获取关联的 permissionId 列表
     *
     * @param roleId 角色ID
     * @return /
     */
    Set<Long> getPermissionIdsByRoleId(Long roleId);

    Set<Long> getPermissionIdsByRoleIds(Collection<Long> roleIds);

}
