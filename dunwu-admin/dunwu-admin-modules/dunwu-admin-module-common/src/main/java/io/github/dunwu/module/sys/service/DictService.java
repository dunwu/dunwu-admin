package io.github.dunwu.module.sys.service;

import io.github.dunwu.module.sys.entity.Dict;
import io.github.dunwu.module.sys.entity.dto.DictDto;
import io.github.dunwu.module.sys.entity.dto.EnumInfoDto;
import io.github.dunwu.module.sys.entity.query.DictQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
public interface DictService extends IService {

    /**
     * 添加一条 {@link Dict} 记录
     *
     * @param entity {@link Dict} 数据实体
     * @return true / false
     */
    boolean insert(Dict entity);

    /**
     * 批量添加 {@link Dict} 记录
     *
     * @param list {@link Dict} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Dict> list);

    /**
     * 根据 ID 更新一条 {@link Dict} 记录
     *
     * @param entity {@link Dict} 数据实体
     * @return true / false
     */
    boolean updateById(Dict entity);

    /**
     * 根据 ID 批量更新 {@link Dict} 记录
     *
     * @param list {@link Dict} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Dict> list);

    /**
     * 添加或更新一条 {@link Dict} 记录
     *
     * @param entity {@link Dict} 数据实体
     * @return true / false
     */
    boolean save(Dict entity);

    /**
     * 批量添加或更新 {@link Dict} 记录
     *
     * @param list {@link Dict} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Dict> list);

    /**
     * 根据 ID 删除一条 {@link Dict} 记录
     *
     * @param id {@link Dict} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Dict} 记录
     *
     * @param ids {@link Dict} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DictDto} 全量数据列表
     *
     * @return {@link List<DictDto>}
     */
    List<DictDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link DictDto} 列表
     *
     * @param ids {@link Dict} 主键列表
     * @return {@link List<DictDto>}
     */
    List<DictDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link DictQuery} 查询 {@link DictDto} 列表
     *
     * @param query 查询条件，根据 {@link DictQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DictDto>}
     */
    List<DictDto> pojoListByQuery(DictQuery query);

    /**
     * 根据 {@link Pageable} 和 {@link DictQuery} 分页查询 {@link DictDto} 列表
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DictQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Page<DictDto>}
     */
    Page<DictDto> pojoSpringPageByQuery(Pageable pageable, DictQuery query);

    /**
     * 根据 id 查询 {@link DictDto}
     *
     * @param id {@link Dict} 主键
     * @return {@link DictDto}
     */
    DictDto pojoById(Serializable id);

    /**
     * 根据 {@link DictQuery} 查询 {@link DictDto} 列表
     *
     * @param query 查询条件，根据 {@link DictQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DictDto}
     */
    DictDto pojoByQuery(DictQuery query);

    /**
     * 根据 {@link DictQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DictQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DictQuery query);

    /**
     * 导入 excel 表单，将表单数据转为数据实体并存储
     *
     * @param file {@link MultipartFile} 实体
     */
    void importList(MultipartFile file);

    /**
     * 根据 id 列表查询 {@link DictDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link Pageable} 和 {@link DictQuery} 分页查询 {@link DictDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DictQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, DictQuery query, HttpServletResponse response);

    /**
     * 将 {@link Dict} 转为 {@link DictDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DictDto doToDto(Dict entity);

    /**
     * 将 {@link DictDto} 转为 {@link Dict}
     *
     * @param dto Dto 实体
     * @return /
     */
    Dict dtoToDo(DictDto dto);

    EnumInfoDto parseJavaEnumFile(MultipartFile file);

    boolean saveDictWithOptions(DictDto dto);

    void downloadDictEnum(Serializable id, HttpServletRequest request, HttpServletResponse response);

}
