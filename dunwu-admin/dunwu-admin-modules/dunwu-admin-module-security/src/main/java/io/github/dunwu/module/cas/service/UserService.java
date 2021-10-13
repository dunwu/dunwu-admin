package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.entity.vo.DeptJobUserMapVo;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
public interface UserService extends IService {

    /**
     * 添加一条 {@link User} 记录
     *
     * @param entity {@link User} 数据实体
     * @return true / false
     */
    boolean insert(User entity);

    /**
     * 批量添加 {@link User} 记录
     *
     * @param list {@link User} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<User> list);

    /**
     * 根据 ID 更新一条 {@link User} 记录
     *
     * @param entity {@link User} 数据实体
     * @return true / false
     */
    boolean updateById(User entity);

    /**
     * 根据 ID 批量更新 {@link User} 记录
     *
     * @param list {@link User} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<User> list);

    /**
     * 添加或更新一条 {@link User} 记录
     *
     * @param entity {@link User} 数据实体
     * @return true / false
     */
    boolean save(User entity);

    /**
     * 批量添加或更新 {@link User} 记录
     *
     * @param list {@link User} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<User> list);

    /**
     * 根据 ID 删除一条 {@link User} 记录
     *
     * @param id {@link User} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link User} 记录
     *
     * @param ids {@link User} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link UserDto} 全量数据列表
     *
     * @return {@link List<UserDto>}
     */
    List<UserDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link UserDto} 列表
     *
     * @param ids {@link User} 主键列表
     * @return {@link List<UserDto>}
     */
    List<UserDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link UserQuery} 查询 {@link UserDto} 列表
     *
     * @param query 查询条件，根据 {@link UserQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<UserDto>}
     */
    List<UserDto> pojoListByQuery(UserQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link UserQuery} 分页查询 {@link UserDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link UserQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<UserDto>}
     */
    Page<UserDto> pojoSpringPageByQuery(Pageable pageable, UserQuery query);

    /**
     * 根据 id 查询 {@link UserDto}
     *
     * @param id {@link User} 主键
     * @return {@link UserDto}
     */
    UserDto pojoById(Serializable id);

    /**
     * 根据 {@link UserQuery} 查询 {@link UserDto} 列表
     *
     * @param query 查询条件，根据 {@link UserQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link UserDto}
     */
    UserDto pojoByQuery(UserQuery query);

    /**
     * 根据 {@link UserQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link UserQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(UserQuery query);

    /**
     * 根据 id 列表查询 {@link UserDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link UserQuery} 分页查询 {@link UserDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link UserQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, UserQuery query, HttpServletResponse response);

    /**
     * 将 {@link User} 转为 {@link UserDto}
     *
     * @param entity 数据实体
     * @return /
     */
    UserDto doToDto(User entity);

    /**
     * 将 {@link UserDto} 转为 {@link User}
     *
     * @param dto Dto 实体
     * @return /
     */
    User dtoToDo(UserDto dto);

    /**
     * 根据 username 查询 {@link UserDto}
     *
     * @param username 用户名
     * @return {@link UserDto}
     */
    UserDto pojoByUsername(String username);

    /**
     * 根据菜单查询用户
     *
     * @param menuId 菜单ID
     * @return /
     */
    List<User> findByMenuId(Long menuId);

    /**
     * 根据所有关联指定部门ID的用户
     *
     * @param deptId 部门ID
     * @return /
     */
    List<UserDto> pojoListByDeptId(Serializable deptId);

    /**
     * 批量将 userIds 绑定指定的部门、职务
     *
     * @param vo 部门/职务/用户列表 关联映射实体
     * @return true / false
     */
    boolean bindDept(DeptJobUserMapVo vo);

    /**
     * 批量将 userIds 和指定的部门、职务解除绑定
     *
     * @param vo 部门/职务/用户列表 关联映射实体
     * @return true / false
     */
    boolean unbindDept(DeptJobUserMapVo vo);

    /**
     * 添加一条 {@link User} 记录，并保存关联的角色列表 roles
     *
     * @param dto {@link UserDto} 数据实体
     * @return true / false
     */
    boolean insertWithRoles(UserDto dto);

    /**
     * 更新一条 {@link User} 记录，并保存关联的角色列表 roles
     *
     * @param dto {@link UserDto} 数据实体
     * @return true / false
     */
    boolean updateWithRoles(UserDto dto);

}
