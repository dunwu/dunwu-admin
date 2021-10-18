package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.RoleQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 角色表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface RoleService extends IService {

    /**
     * 添加一条 {@link Role} 记录
     *
     * @param entity {@link Role} 数据实体
     * @return true / false
     */
    boolean insert(Role entity);

    /**
     * 批量添加 {@link Role} 记录
     *
     * @param list {@link Role} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Role> list);

    /**
     * 根据 ID 更新一条 {@link Role} 记录
     *
     * @param entity {@link Role} 数据实体
     * @return true / false
     */
    boolean updateById(Role entity);

    /**
     * 根据 ID 批量更新 {@link Role} 记录
     *
     * @param list {@link Role} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Role> list);

    /**
     * 添加或更新一条 {@link Role} 记录
     *
     * @param entity {@link Role} 数据实体
     * @return true / false
     */
    boolean save(Role entity);

    /**
     * 批量添加或更新 {@link Role} 记录
     *
     * @param list {@link Role} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Role> list);

    /**
     * 根据 ID 删除一条 {@link Role} 记录
     *
     * @param id {@link Role} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Role} 记录
     *
     * @param ids {@link Role} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link RoleDto} 全量数据列表
     *
     * @return {@link List<RoleDto>}
     */
    List<RoleDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link RoleDto} 列表
     *
     * @param ids {@link Role} 主键列表
     * @return {@link List<RoleDto>}
     */
    List<RoleDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link RoleQuery} 查询 {@link RoleDto} 列表
     *
     * @param query 查询条件，根据 {@link RoleQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<RoleDto>}
     */
    List<RoleDto> pojoListByQuery(RoleQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link RoleQuery} 分页查询 {@link RoleDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link RoleQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<RoleDto>}
     */
    Page<RoleDto> pojoSpringPageByQuery(Pageable pageable, RoleQuery query);

    /**
     * 根据 id 查询 {@link RoleDto}
     *
     * @param id {@link Role} 主键
     * @return {@link RoleDto}
     */
    RoleDto pojoById(Serializable id);

    /**
     * 根据 {@link RoleQuery} 查询 {@link RoleDto} 列表
     *
     * @param query 查询条件，根据 {@link RoleQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link RoleDto}
     */
    RoleDto pojoByQuery(RoleQuery query);

    /**
     * 根据 {@link RoleQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link RoleQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(RoleQuery query);

    /**
     * 根据 id 列表查询 {@link RoleDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link RoleQuery} 分页查询 {@link RoleDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link RoleQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, RoleQuery query, HttpServletResponse response);

    /**
     * 将 {@link Role} 转为 {@link RoleDto}
     *
     * @param entity 数据实体
     * @return /
     */
    RoleDto doToDto(Role entity);

    /**
     * 将 {@link RoleDto} 转为 {@link Role}
     *
     * @param dto Dto 实体
     * @return /
     */
    Role dtoToDo(RoleDto dto);

    List<RoleDto> pojoListByUserId(Long userId);

    List<RoleDto> pojoListByJobId(Long jobId);

    boolean updateMenusByRoleId(Long roleId, List<MenuDto> menus);

    boolean updateRolesByJobId(Long jobId, Collection<Long> roleIds);

    void checkRoleLevel(Integer level);

    Integer getRoleLevel();

    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

}
