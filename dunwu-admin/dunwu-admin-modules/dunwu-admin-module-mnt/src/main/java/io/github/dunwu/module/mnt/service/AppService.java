package io.github.dunwu.module.mnt.service;

import io.github.dunwu.module.mnt.entity.App;
import io.github.dunwu.module.mnt.entity.dto.AppDto;
import io.github.dunwu.module.mnt.entity.query.AppQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用配置 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public interface AppService extends IService {

    /**
     * 添加一条 {@link App} 记录
     *
     * @param entity {@link App} 数据实体
     * @return true / false
     */
    boolean insert(App entity);

    /**
     * 批量添加 {@link App} 记录
     *
     * @param list {@link App} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<App> list);

    /**
     * 根据 ID 更新一条 {@link App} 记录
     *
     * @param entity {@link App} 数据实体
     * @return true / false
     */
    boolean updateById(App entity);

    /**
     * 根据 ID 批量更新 {@link App} 记录
     *
     * @param list {@link App} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<App> list);

    /**
     * 添加或更新一条 {@link App} 记录
     *
     * @param entity {@link App} 数据实体
     * @return true / false
     */
    boolean save(App entity);

    /**
     * 批量添加或更新 {@link App} 记录
     *
     * @param list {@link App} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<App> list);

    /**
     * 根据 ID 删除一条 {@link App} 记录
     *
     * @param id {@link App} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link App} 记录
     *
     * @param ids {@link App} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link AppDto} 全量数据列表
     *
     * @return {@link List<AppDto>}
     */
    List<AppDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link AppDto} 列表
     *
     * @param ids {@link App} 主键列表
     * @return {@link List<AppDto>}
     */
    List<AppDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link AppQuery} 查询 {@link AppDto} 列表
     *
     * @param query 查询条件，根据 {@link AppQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<AppDto>}
     */
    List<AppDto> pojoListByQuery(AppQuery query);

    /**
     * 根据 {@link AppQuery} 和 {@link Pageable} 分页查询 {@link AppDto} 列表
     *
     * @param query    查询条件，根据 {@link AppQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<AppDto>}
     */
    Page<AppDto> pojoSpringPageByQuery(AppQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link AppDto}
     *
     * @param id {@link App} 主键
     * @return {@link AppDto}
     */
    AppDto pojoById(Serializable id);

    /**
     * 根据 {@link AppQuery} 查询 {@link AppDto} 列表
     *
     * @param query 查询条件，根据 {@link AppQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link AppDto}
     */
    AppDto pojoByQuery(AppQuery query);

    /**
     * 根据 {@link AppQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link AppQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(AppQuery query);

    /**
     * 根据 id 列表查询 {@link AppDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link AppQuery} 和 {@link Pageable} 分页查询 {@link AppDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link AppQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(AppQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link App} 转为 {@link AppDto}
     *
     * @param entity 数据实体
     * @return /
     */
    AppDto doToDto(App entity);

    /**
     * 将 {@link AppDto} 转为 {@link App}
     *
     * @param dto Dto 实体
     * @return /
     */
    App dtoToDo(AppDto dto);

}
