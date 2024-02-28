package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.TableColumnConfig;
import io.github.dunwu.module.sys.entity.dto.TableColumnConfigDto;
import io.github.dunwu.module.sys.entity.dto.TableConfigDto;
import io.github.dunwu.module.sys.entity.query.TableColumnConfigQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.entity.TableInfo;
import io.github.dunwu.tool.data.mybatis.IService;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.response.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 表字段配置表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
public interface TableColumnConfigService extends IService {

    /**
     * 添加一条 {@link TableColumnConfig} 记录
     *
     * @param entity {@link TableColumnConfig} 数据实体
     * @return true / false
     */
    boolean insert(TableColumnConfig entity);

    /**
     * 批量添加 {@link TableColumnConfig} 记录
     *
     * @param list {@link TableColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<TableColumnConfig> list);

    /**
     * 根据 ID 更新一条 {@link TableColumnConfig} 记录
     *
     * @param entity {@link TableColumnConfig} 数据实体
     * @return true / false
     */
    boolean updateById(TableColumnConfig entity);

    /**
     * 根据 ID 批量更新 {@link TableColumnConfig} 记录
     *
     * @param list {@link TableColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<TableColumnConfig> list);

    /**
     * 添加或更新一条 {@link TableColumnConfig} 记录
     *
     * @param entity {@link TableColumnConfig} 数据实体
     * @return true / false
     */
    boolean save(TableColumnConfig entity);

    /**
     * 批量添加或更新 {@link TableColumnConfig} 记录
     *
     * @param list {@link TableColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<TableColumnConfig> list);

    /**
     * 根据 ID 删除一条 {@link TableColumnConfig} 记录
     *
     * @param id {@link TableColumnConfig} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link TableColumnConfig} 记录
     *
     * @param ids {@link TableColumnConfig} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link TableColumnConfigDto} 全量数据列表
     *
     * @return {@link List<TableColumnConfigDto>}
     */
    List<TableColumnConfigDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link TableColumnConfigDto} 列表
     *
     * @param ids {@link TableColumnConfig} 主键列表
     * @return {@link List<TableColumnConfigDto>}
     */
    List<TableColumnConfigDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link TableColumnConfigQuery} 查询 {@link TableColumnConfigDto} 列表
     *
     * @param query 查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<TableColumnConfigDto>}
     */
    List<TableColumnConfigDto> pojoListByQuery(TableColumnConfigQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnConfigQuery} 分页查询 {@link TableColumnConfigDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<TableColumnConfigDto>}
     */
    Page<TableColumnConfigDto> pojoSpringPageByQuery(Pageable pageable, TableColumnConfigQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnConfigQuery} 分页查询 {@link TableColumnConfigDto} 列表
     *
     * @param pageQuery 分页查询条件
     * @param query     查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<TableColumnConfigDto>}
     */
    Page<TableColumnConfigDto> pojoSpringPageByQuery(PageQuery pageQuery, TableColumnConfigQuery query);

    /**
     * 根据 id 查询 {@link TableColumnConfigDto}
     *
     * @param id {@link TableColumnConfig} 主键
     * @return {@link TableColumnConfigDto}
     */
    TableColumnConfigDto pojoById(Serializable id);

    /**
     * 根据 {@link TableColumnConfigQuery} 查询 {@link TableColumnConfigDto} 列表
     *
     * @param query 查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link TableColumnConfigDto}
     */
    TableColumnConfigDto pojoByQuery(TableColumnConfigQuery query);

    /**
     * 根据 {@link TableColumnConfigQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(TableColumnConfigQuery query);

    /**
     * 导入 excel 表单，将表单数据转为数据实体并存储
     *
     * @param file {@link MultipartFile} 实体
     */
    void importList(MultipartFile file);

    /**
     * 根据 id 列表查询 {@link TableColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link TableColumnConfigQuery} 分页查询 {@link TableColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, TableColumnConfigQuery query, HttpServletResponse response);

    /**
     * 根据 {@link PageQuery} 和 {@link TableColumnConfigQuery} 分页查询 {@link TableColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param pageQuery 分页查询条件
     * @param query     查询条件，根据 {@link TableColumnConfigQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response  {@link HttpServletResponse} 实体
     */
    void exportPage(PageQuery pageQuery, TableColumnConfigQuery query, HttpServletResponse response);

    /**
     * 将 {@link TableColumnConfig} 转为 {@link TableColumnConfigDto}
     *
     * @param entity 数据实体
     * @return /
     */
    TableColumnConfigDto doToDto(TableColumnConfig entity);

    /**
     * 将 {@link TableColumnConfigDto} 转为 {@link TableColumnConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    TableColumnConfig dtoToDo(TableColumnConfigDto dto);

    PageImpl<TableInfo> syncTablePage(String tableName, Pageable pageable);

    TableConfigDto syncTableConfig(TableColumnConfigQuery query);

    @Transactional(rollbackFor = Exception.class)
    boolean saveTableConfig(TableConfigDto dto);

}
