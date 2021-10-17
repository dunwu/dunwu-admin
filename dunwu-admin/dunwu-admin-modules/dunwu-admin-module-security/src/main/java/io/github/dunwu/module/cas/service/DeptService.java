package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.DeptRelationDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
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
 * 部门表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface DeptService extends IService {

    /**
     * 添加一条 {@link Dept} 记录
     *
     * @param entity {@link Dept} 数据实体
     * @return true / false
     */
    boolean insert(Dept entity);

    /**
     * 批量添加 {@link Dept} 记录
     *
     * @param list {@link Dept} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Dept> list);

    /**
     * 根据 ID 更新一条 {@link Dept} 记录
     *
     * @param entity {@link Dept} 数据实体
     * @return true / false
     */
    boolean updateById(Dept entity);

    /**
     * 根据 ID 批量更新 {@link Dept} 记录
     *
     * @param list {@link Dept} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Dept> list);

    /**
     * 添加或更新一条 {@link Dept} 记录
     *
     * @param entity {@link Dept} 数据实体
     * @return true / false
     */
    boolean save(Dept entity);

    /**
     * 批量添加或更新 {@link Dept} 记录
     *
     * @param list {@link Dept} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Dept> list);

    /**
     * 根据 ID 删除一条 {@link Dept} 记录
     *
     * @param id {@link Dept} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Dept} 记录
     *
     * @param ids {@link Dept} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DeptDto} 全量数据列表
     *
     * @return {@link List<DeptDto>}
     */
    List<DeptDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link DeptDto} 列表
     *
     * @param ids {@link Dept} 主键列表
     * @return {@link List<DeptDto>}
     */
    List<DeptDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link DeptQuery} 查询 {@link DeptDto} 列表
     *
     * @param query 查询条件，根据 {@link DeptQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DeptDto>}
     */
    List<DeptDto> pojoListByQuery(DeptQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link DeptQuery} 分页查询 {@link DeptDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DeptQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<DeptDto>}
     */
    Page<DeptDto> pojoSpringPageByQuery(Pageable pageable, DeptQuery query);

    /**
     * 根据 id 查询 {@link DeptDto}
     *
     * @param id {@link Dept} 主键
     * @return {@link DeptDto}
     */
    DeptDto pojoById(Serializable id);

    /**
     * 根据 {@link DeptQuery} 查询 {@link DeptDto} 列表
     *
     * @param query 查询条件，根据 {@link DeptQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DeptDto}
     */
    DeptDto pojoByQuery(DeptQuery query);

    /**
     * 根据 {@link DeptQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DeptQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DeptQuery query);

    /**
     * 根据 id 列表查询 {@link DeptDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link DeptQuery} 分页查询 {@link DeptDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DeptQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, DeptQuery query, HttpServletResponse response);

    /**
     * 将 {@link Dept} 转为 {@link DeptDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DeptDto doToDto(Dept entity);

    /**
     * 将 {@link DeptDto} 转为 {@link Dept}
     *
     * @param dto Dto 实体
     * @return /
     */
    Dept dtoToDo(DeptDto dto);

    /**
     * 获取所有 pid 下的子部门
     *
     * @param pid 上级部门ID
     * @return /
     */
    List<DeptDto> pojoListByPid(Serializable pid);

    /**
     * 根据 roleId 获取所有 pid 下的子部门
     *
     * @param roleId 角色ID
     * @return /
     */
    List<DeptDto> pojoListByRoleId(Long roleId);

    List<DeptDto> pojoTreeListById(Serializable id);

    /**
     * 根据 ID 列表查询 {@link DeptDto} 树形列表
     *
     * @param ids {@link Dept} 主键列表
     * @return {@link List<DeptDto>}
     */
    List<DeptDto> pojoTreeListByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 查询 {@link DeptDto} 树形列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return /
     */
    List<DeptDto> pojoIncompleteTreeListByQuery(DeptQuery query);

    boolean updateRelations(DeptRelationDto dto);

    List<DeptDto> pojoSuperiorListById(Serializable id);

    List<DeptDto> pojoSuperiorListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 ID 获取当前部门及所有子部门的 ID 列表
     *
     * @param id ID
     * @return /
     */
    Set<Long> getOwnAndChildrenDeptIds(Serializable id);

}
