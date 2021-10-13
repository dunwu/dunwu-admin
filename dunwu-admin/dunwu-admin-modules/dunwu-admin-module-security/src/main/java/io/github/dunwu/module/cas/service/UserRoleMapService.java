package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.module.cas.entity.dto.UserRoleMapDto;
import io.github.dunwu.module.cas.entity.query.UserRoleMapQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户角色关联表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
public interface UserRoleMapService extends IService {

    /**
     * 添加一条 {@link UserRoleMap} 记录
     *
     * @param entity {@link UserRoleMap} 数据实体
     * @return true / false
     */
    boolean insert(UserRoleMap entity);

    /**
     * 批量添加 {@link UserRoleMap} 记录
     *
     * @param list {@link UserRoleMap} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<UserRoleMap> list);

    /**
     * 根据 ID 更新一条 {@link UserRoleMap} 记录
     *
     * @param entity {@link UserRoleMap} 数据实体
     * @return true / false
     */
    boolean updateById(UserRoleMap entity);

    /**
     * 根据 ID 批量更新 {@link UserRoleMap} 记录
     *
     * @param list {@link UserRoleMap} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<UserRoleMap> list);

    /**
     * 添加或更新一条 {@link UserRoleMap} 记录
     *
     * @param entity {@link UserRoleMap} 数据实体
     * @return true / false
     */
    boolean save(UserRoleMap entity);

    /**
     * 批量添加或更新 {@link UserRoleMap} 记录
     *
     * @param list {@link UserRoleMap} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<UserRoleMap> list);

    /**
     * 根据 ID 删除一条 {@link UserRoleMap} 记录
     *
     * @param id {@link UserRoleMap} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link UserRoleMap} 记录
     *
     * @param ids {@link UserRoleMap} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link UserRoleMapDto} 全量数据列表
     *
     * @return {@link List<UserRoleMapDto>}
     */
    List<UserRoleMapDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link UserRoleMapDto} 列表
     *
     * @param ids {@link UserRoleMap} 主键列表
     * @return {@link List<UserRoleMapDto>}
     */
    List<UserRoleMapDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link UserRoleMapQuery} 查询 {@link UserRoleMapDto} 列表
     *
     * @param query 查询条件，根据 {@link UserRoleMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<UserRoleMapDto>}
     */
    List<UserRoleMapDto> pojoListByQuery(UserRoleMapQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link UserRoleMapQuery} 分页查询 {@link UserRoleMapDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link UserRoleMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<UserRoleMapDto>}
     */
    Page<UserRoleMapDto> pojoSpringPageByQuery(Pageable pageable, UserRoleMapQuery query);

    /**
     * 根据 id 查询 {@link UserRoleMapDto}
     *
     * @param id {@link UserRoleMap} 主键
     * @return {@link UserRoleMapDto}
     */
    UserRoleMapDto pojoById(Serializable id);

    /**
     * 根据 {@link UserRoleMapQuery} 查询 {@link UserRoleMapDto} 列表
     *
     * @param query 查询条件，根据 {@link UserRoleMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link UserRoleMapDto}
     */
    UserRoleMapDto pojoByQuery(UserRoleMapQuery query);

    /**
     * 根据 {@link UserRoleMapQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link UserRoleMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(UserRoleMapQuery query);

    /**
     * 根据 id 列表查询 {@link UserRoleMapDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link UserRoleMapQuery} 分页查询 {@link UserRoleMapDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link UserRoleMapQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, UserRoleMapQuery query, HttpServletResponse response);

    /**
     * 将 {@link UserRoleMap} 转为 {@link UserRoleMapDto}
     *
     * @param entity 数据实体
     * @return /
     */
    UserRoleMapDto doToDto(UserRoleMap entity);

    /**
     * 将 {@link UserRoleMapDto} 转为 {@link UserRoleMap}
     *
     * @param dto Dto 实体
     * @return /
     */
    UserRoleMap dtoToDo(UserRoleMapDto dto);

    boolean insertBatchByRoleIds(Long userId, Collection<Long> roleIds);

    boolean deleteBatchByRoleIds(Long userId, Collection<Long> roleIds);

    boolean deleteByUserId(Serializable userId);

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
