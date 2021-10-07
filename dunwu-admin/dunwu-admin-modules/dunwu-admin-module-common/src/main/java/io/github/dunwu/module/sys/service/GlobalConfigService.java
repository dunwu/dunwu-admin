package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.GlobalConfig;
import io.github.dunwu.module.sys.entity.dto.GlobalConfigDto;
import io.github.dunwu.module.sys.entity.query.GlobalConfigQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统全局配置表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
public interface GlobalConfigService extends IService {

    /**
     * 添加一条 {@link GlobalConfig} 记录
     *
     * @param entity {@link GlobalConfig} 数据实体
     * @return true / false
     */
    boolean insert(GlobalConfig entity);

    /**
     * 批量添加 {@link GlobalConfig} 记录
     *
     * @param list {@link GlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<GlobalConfig> list);

    /**
     * 根据 ID 更新一条 {@link GlobalConfig} 记录
     *
     * @param entity {@link GlobalConfig} 数据实体
     * @return true / false
     */
    boolean updateById(GlobalConfig entity);

    /**
     * 根据 ID 批量更新 {@link GlobalConfig} 记录
     *
     * @param list {@link GlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<GlobalConfig> list);

    /**
     * 添加或更新一条 {@link GlobalConfig} 记录
     *
     * @param entity {@link GlobalConfig} 数据实体
     * @return true / false
     */
    boolean save(GlobalConfig entity);

    /**
     * 批量添加或更新 {@link GlobalConfig} 记录
     *
     * @param list {@link GlobalConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<GlobalConfig> list);

    /**
     * 根据 ID 删除一条 {@link GlobalConfig} 记录
     *
     * @param id {@link GlobalConfig} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link GlobalConfig} 记录
     *
     * @param ids {@link GlobalConfig} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link GlobalConfigDto} 全量数据列表
     *
     * @return {@link List<GlobalConfigDto>}
     */
    List<GlobalConfigDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link GlobalConfigDto} 列表
     *
     * @param {@link GlobalConfig} 主键列表
     * @return {@link List<GlobalConfigDto>}
     */
    List<GlobalConfigDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link GlobalConfigQuery} 查询 {@link GlobalConfigDto} 列表
     *
     * @param query 查询条件，根据 {@link GlobalConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<GlobalConfigDto>}
     */
    List<GlobalConfigDto> pojoListByQuery(GlobalConfigQuery query);

    /**
     * 根据 {@link GlobalConfigQuery} 和 {@link Pageable} 分页查询 {@link GlobalConfigDto} 列表
     *
     * @param query    查询条件，根据 {@link GlobalConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<GlobalConfigDto>}
     */
    Page<GlobalConfigDto> pojoSpringPageByQuery(GlobalConfigQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link GlobalConfigDto}
     *
     * @param id {@link GlobalConfig} 主键
     * @return {@link GlobalConfigDto}
     */
    GlobalConfigDto pojoById(Serializable id);

    /**
     * 根据 {@link GlobalConfigQuery} 查询 {@link GlobalConfigDto} 列表
     *
     * @param query 查询条件，根据 {@link GlobalConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link GlobalConfigDto}
     */
    GlobalConfigDto pojoByQuery(GlobalConfigQuery query);

    /**
     * 根据 {@link GlobalConfigQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link GlobalConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(GlobalConfigQuery query);

    /**
     * 根据 id 列表查询 {@link GlobalConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link GlobalConfigQuery} 和 {@link Pageable} 分页查询 {@link GlobalConfigDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link GlobalConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(GlobalConfigQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link GlobalConfig} 转为 {@link GlobalConfigDto}
     *
     * @param entity 数据实体
     * @return /
     */
    GlobalConfigDto doToDto(GlobalConfig entity);

    /**
     * 将 {@link GlobalConfigDto} 转为 {@link GlobalConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    GlobalConfig dtoToDo(GlobalConfigDto dto);

    List<GlobalConfigDto> pojoListByKeys(String appCode, String moduleCode, String namespace);

    GlobalConfigDto pojoOneByKeys(String appCode, String moduleCode, String namespace, String code);

    boolean setConfigOptionValue(String appCode, String moduleCode, String namespace, String code,
        String value);

    boolean setConfigOptionValues(String appCode, String moduleCode, String namespace, Map<String, String> map);

}
