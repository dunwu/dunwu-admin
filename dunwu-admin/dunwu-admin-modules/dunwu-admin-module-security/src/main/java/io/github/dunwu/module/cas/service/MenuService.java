package io.github.dunwu.module.cas.service;

import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.query.MenuQuery;
import io.github.dunwu.module.cas.entity.vo.MenuVo;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 菜单 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
public interface MenuService extends IService {

    /**
     * 添加一条 {@link Menu} 记录
     *
     * @param entity {@link Menu} 数据实体
     * @return true / false
     */
    boolean insert(Menu entity);

    /**
     * 批量添加 {@link Menu} 记录
     *
     * @param list {@link Menu} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Menu> list);

    /**
     * 根据 ID 更新一条 {@link Menu} 记录
     *
     * @param entity {@link Menu} 数据实体
     * @return true / false
     */
    boolean updateById(Menu entity);

    /**
     * 根据 ID 批量更新 {@link Menu} 记录
     *
     * @param list {@link Menu} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Menu> list);

    /**
     * 添加或更新一条 {@link Menu} 记录
     *
     * @param entity {@link Menu} 数据实体
     * @return true / false
     */
    boolean save(Menu entity);

    /**
     * 批量添加或更新 {@link Menu} 记录
     *
     * @param list {@link Menu} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Menu> list);

    /**
     * 根据 ID 删除一条 {@link Menu} 记录
     *
     * @param id {@link Menu} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Menu} 记录
     *
     * @param ids {@link Menu} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link MenuDto} 全量数据列表
     *
     * @return {@link List < MenuDto >}
     */
    List<MenuDto> pojoList();

    /**
     * 根据 {@link MenuQuery} 查询 {@link MenuDto} 列表
     *
     * @param query 查询条件，根据 MenuQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List < MenuDto >}
     */
    List<MenuDto> pojoListByQuery(MenuQuery query);

    /**
     * 根据 {@link MenuQuery} 和 {@link Pageable} 分页查询 {@link MenuDto} 列表
     *
     * @param query    查询条件，根据 MenuQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page < MenuDto >}
     */
    Page<MenuDto> pojoSpringPageByQuery(MenuQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link MenuDto}
     *
     * @param id {@link Menu} 主键
     * @return {@link MenuDto}
     */
    MenuDto pojoById(Serializable id);

    /**
     * 根据 {@link MenuQuery} 查询 {@link MenuDto} 列表
     *
     * @param query 查询条件，根据 MenuQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link MenuDto}
     */
    MenuDto pojoByQuery(MenuQuery query);

    /**
     * 根据 {@link MenuQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 MenuQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(MenuQuery query);

    /**
     * 根据 id 列表查询 {@link MenuDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link MenuQuery} 和 {@link Pageable} 分页查询 {@link MenuDto} 列表，并导出 excel 表单
     *  @param pageable 分页查询条件
     * @param query    查询条件，根据 MenuQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, MenuQuery query, HttpServletResponse response);

    /**
     * 将 {@link Menu} 转为 {@link MenuDto}
     *
     * @param entity 数据实体
     * @return /
     */
    MenuDto doToDto(Menu entity);

    /**
     * 将 {@link MenuDto} 转为 {@link Menu}
     *
     * @param dto Dto 实体
     * @return /
     */
    Menu dtoToDo(MenuDto dto);

    /**
     * 根据 query 和 pageable 查询 {@link MenuDto} 树形列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return /
     */
    List<MenuDto> treeList(MenuQuery query);

    List<MenuDto> treeListByIds(Collection<Serializable> list);

    List<MenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds);

    List<MenuVo> buildMenuListForCurrentUser();

    List<Long> childrenIds(Long id);

}
