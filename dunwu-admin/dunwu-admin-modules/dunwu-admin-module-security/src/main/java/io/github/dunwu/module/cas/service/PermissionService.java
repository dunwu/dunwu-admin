package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.module.cas.entity.dto.PermissionDto;
import io.github.dunwu.module.cas.entity.query.PermissionQuery;
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
 * 权限表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface PermissionService extends IService {

    /**
     * 添加一条 {@link Permission} 记录
     *
     * @param entity {@link Permission} 数据实体
     * @return true / false
     */
    boolean insert(Permission entity);

    /**
     * 批量添加 {@link Permission} 记录
     *
     * @param list {@link Permission} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Permission> list);

    /**
     * 根据 ID 更新一条 {@link Permission} 记录
     *
     * @param entity {@link Permission} 数据实体
     * @return true / false
     */
    boolean updateById(Permission entity);

    /**
     * 根据 ID 批量更新 {@link Permission} 记录
     *
     * @param list {@link Permission} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Permission> list);

    /**
     * 添加或更新一条 {@link Permission} 记录
     *
     * @param entity {@link Permission} 数据实体
     * @return true / false
     */
    boolean save(Permission entity);

    /**
     * 批量添加或更新 {@link Permission} 记录
     *
     * @param list {@link Permission} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Permission> list);

    /**
     * 根据 ID 删除一条 {@link Permission} 记录
     *
     * @param id {@link Permission} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Permission} 记录
     *
     * @param ids {@link Permission} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link PermissionDto} 全量数据列表
     *
     * @return {@link List<PermissionDto>}
     */
    List<PermissionDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link PermissionDto} 列表
     *
     * @param ids {@link Permission} 主键列表
     * @return {@link List<PermissionDto>}
     */
    List<PermissionDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link PermissionQuery} 查询 {@link PermissionDto} 列表
     *
     * @param query 查询条件，根据 {@link PermissionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<PermissionDto>}
     */
    List<PermissionDto> pojoListByQuery(PermissionQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link PermissionQuery} 分页查询 {@link PermissionDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link PermissionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<PermissionDto>}
     */
    Page<PermissionDto> pojoSpringPageByQuery(Pageable pageable, PermissionQuery query);

    /**
     * 根据 id 查询 {@link PermissionDto}
     *
     * @param id {@link Permission} 主键
     * @return {@link PermissionDto}
     */
    PermissionDto pojoById(Serializable id);

    /**
     * 根据 {@link PermissionQuery} 查询 {@link PermissionDto} 列表
     *
     * @param query 查询条件，根据 {@link PermissionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link PermissionDto}
     */
    PermissionDto pojoByQuery(PermissionQuery query);

    /**
     * 根据 {@link PermissionQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link PermissionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(PermissionQuery query);

    /**
     * 根据 id 列表查询 {@link PermissionDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link PermissionQuery} 分页查询 {@link PermissionDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link PermissionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, PermissionQuery query, HttpServletResponse response);

    /**
     * 将 {@link Permission} 转为 {@link PermissionDto}
     *
     * @param entity 数据实体
     * @return /
     */
    PermissionDto doToDto(Permission entity);

    /**
     * 将 {@link PermissionDto} 转为 {@link Permission}
     *
     * @param dto Dto 实体
     * @return /
     */
    Permission dtoToDo(PermissionDto dto);

    boolean saveByResourceId(Permission entity);

    boolean deleteByResourceId(Serializable resourceId);

    boolean deleteBatchByResourceIds(Collection<? extends Serializable> resourceIds);

    Set<Long> getMenuIdsByRoleId(Long roleId);

    Set<Long> getMenuIdsByRoleIds(Collection<Long> roleIds);

}
