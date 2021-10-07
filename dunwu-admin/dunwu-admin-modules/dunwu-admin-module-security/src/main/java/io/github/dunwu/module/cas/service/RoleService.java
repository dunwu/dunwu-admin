package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface RoleService extends IService {

    /**
     * 添加一条 {@link Role} 记录
     *
     * @param entity {@link Role} 数据实体
     * @return true / false
     */
    boolean save(Role entity);

    /**
     * 根据 ID 更新一条 {@link Role} 记录
     *
     * @param entity {@link Role} 数据实体
     * @return true / false
     */
    boolean updateById(Role entity);

    /**
     * 根据 ID 删除一条 {@link Role} 记录
     *
     * @param id {@link Role} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Role} 记录
     *
     * @param ids {@link Role} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link RoleDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysRoleDto>}
     */
    Page<RoleDto> pojoSpringPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link RoleDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List <  RoleDto  >}
     */
    List<RoleDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link RoleDto}
     *
     * @param id {@link Role} 主键
     * @return {@link List <  RoleDto  >}
     */
    RoleDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link RoleDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List <  RoleDto  >}
     */
    RoleDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link RoleDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportByIds(Collection<Serializable> ids, HttpServletResponse response);

    /**
     * 根据 query 和 pageable 查询 {@link RoleDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPageData(Object query, Pageable pageable, HttpServletResponse response);

    List<RoleDto> pojoListByUserId(Long userId);

    List<RoleDto> pojoListByJobId(Long jobId);

    List<RoleDto> pojoListByMenuIds(Collection<Long> menuIds);

    boolean updateMenusByRoleId(Long roleId, List<MenuDto> menus);

    boolean updateRolesByJobId(Long jobId, Collection<Long> roleIds);

    void checkRoleLevel(Integer level);

    Integer getRoleLevel();

    @Cacheable(key = "'auth:' + #p0.id")
    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

}
