package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictOptionQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典选项 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
public interface DictOptionService extends IService {

    /**
     * 添加一条 {@link DictOption} 记录
     *
     * @param entity {@link DictOption} 数据实体
     * @return true / false
     */
    boolean insert(DictOption entity);

    /**
     * 批量添加 {@link DictOption} 记录
     *
     * @param list {@link DictOption} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<DictOption> list);

    /**
     * 根据 ID 更新一条 {@link DictOption} 记录
     *
     * @param entity {@link DictOption} 数据实体
     * @return true / false
     */
    boolean updateById(DictOption entity);

    /**
     * 根据 ID 批量更新 {@link DictOption} 记录
     *
     * @param list {@link DictOption} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<DictOption> list);

    /**
     * 添加或更新一条 {@link DictOption} 记录
     *
     * @param entity {@link DictOption} 数据实体
     * @return true / false
     */
    boolean save(DictOption entity);

    /**
     * 批量添加或更新 {@link DictOption} 记录
     *
     * @param list {@link DictOption} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<DictOption> list);

    /**
     * 根据 ID 删除一条 {@link DictOption} 记录
     *
     * @param id {@link DictOption} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link DictOption} 记录
     *
     * @param ids {@link DictOption} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DictOptionDto} 全量数据列表
     *
     * @return {@link List<DictOptionDto>}
     */
    List<DictOptionDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link DictOptionDto} 列表
     *
     * @param ids {@link DictOption} 主键列表
     * @return {@link List<DictOptionDto>}
     */
    List<DictOptionDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link DictOptionQuery} 查询 {@link DictOptionDto} 列表
     *
     * @param query 查询条件，根据 {@link DictOptionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DictOptionDto>}
     */
    List<DictOptionDto> pojoListByQuery(DictOptionQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link DictOptionQuery} 分页查询 {@link DictOptionDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DictOptionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<DictOptionDto>}
     */
    Page<DictOptionDto> pojoSpringPageByQuery(Pageable pageable, DictOptionQuery query);

    /**
     * 根据 id 查询 {@link DictOptionDto}
     *
     * @param id {@link DictOption} 主键
     * @return {@link DictOptionDto}
     */
    DictOptionDto pojoById(Serializable id);

    /**
     * 根据 {@link DictOptionQuery} 查询 {@link DictOptionDto} 列表
     *
     * @param query 查询条件，根据 {@link DictOptionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DictOptionDto}
     */
    DictOptionDto pojoByQuery(DictOptionQuery query);

    /**
     * 根据 {@link DictOptionQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DictOptionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DictOptionQuery query);

    /**
     * 根据 id 列表查询 {@link DictOptionDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link DictOptionQuery} 分页查询 {@link DictOptionDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DictOptionQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, DictOptionQuery query, HttpServletResponse response);

    /**
     * 将 {@link DictOption} 转为 {@link DictOptionDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DictOptionDto doToDto(DictOption entity);

    /**
     * 将 {@link DictOptionDto} 转为 {@link DictOption}
     *
     * @param dto Dto 实体
     * @return /
     */
    DictOption dtoToDo(DictOptionDto dto);

    List<DictOptionDto> pojoListByDictId(Long dictId);

}
