package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * 用户角色关联表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
public interface UserRoleMapDao extends IExtDao<UserRoleMap> {

    /**
     * 批量插入 roleIds 和 userId 关联的记录
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return true / false
     */
    boolean insertBatchByRoleIds(Long userId, Collection<Long> roleIds);

    /**
     * 批量删除 roleIds 和 userId 关联的记录
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return true / false
     */
    boolean deleteBatchByRoleIds(Long userId, Collection<Long> roleIds);

    /**
     * 删除所有 userId 相关的记录
     *
     * @param userId 用户ID
     * @return true / false
     */
    boolean deleteByUserId(Serializable userId);

    /**
     * 删除所有 roleId 相关的记录
     *
     * @param roleId 角色ID
     * @return true / false
     */
    boolean deleteByRoleId(Serializable roleId);

    /**
     * 根据 userId 获取关联的 roleId 列表
     *
     * @param userId 用户ID
     * @return /
     */
    Set<? extends Serializable> getRoleIdsByUserId(Serializable userId);

    /**
     * 根据 roleId 获取关联的 userId 列表
     *
     * @param roleId 角色ID
     * @return /
     */
    Set<? extends Serializable> getUserIdsByRoleId(Serializable roleId);

}
