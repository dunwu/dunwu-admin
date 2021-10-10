package io.github.dunwu.module.storage.service;

import io.github.dunwu.module.storage.entity.dto.FileContentDto;
import io.github.dunwu.module.storage.entity.FileContent;
import io.github.dunwu.module.storage.entity.query.FileContentQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件内容表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
public interface FileContentService extends IService {

    /**
     * 添加一条 {@link FileContent} 记录
     *
     * @param entity {@link FileContent} 数据实体
     * @return true / false
     */
    boolean insert(FileContent entity);

    /**
     * 批量添加 {@link FileContent} 记录
     *
     * @param list {@link FileContent} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<FileContent> list);

    /**
     * 根据 ID 更新一条 {@link FileContent} 记录
     *
     * @param entity {@link FileContent} 数据实体
     * @return true / false
     */
    boolean updateById(FileContent entity);

    /**
     * 根据 ID 批量更新 {@link FileContent} 记录
     *
     * @param list {@link FileContent} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<FileContent> list);

    /**
     * 添加或更新一条 {@link FileContent} 记录
     *
     * @param entity {@link FileContent} 数据实体
     * @return true / false
     */
    boolean save(FileContent entity);

    /**
     * 批量添加或更新 {@link FileContent} 记录
     *
     * @param list {@link FileContent} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<FileContent> list);

    /**
     * 根据 ID 删除一条 {@link FileContent} 记录
     *
     * @param id {@link FileContent} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link FileContent} 记录
     *
     * @param ids {@link FileContent} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link FileContentDto} 全量数据列表
     *
     * @return {@link List<FileContentDto>}
     */
    List<FileContentDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link FileContentDto} 列表
     *
     * @param {@link FileContent} 主键列表
     * @return {@link List<FileContentDto>}
     */
    List<FileContentDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link FileContentQuery} 查询 {@link FileContentDto} 列表
     *
     * @param query 查询条件，根据 {@link FileContentQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<FileContentDto>}
     */
    List<FileContentDto> pojoListByQuery(FileContentQuery query);

    /**
     * 根据 {@link FileContentQuery} 和 {@link Pageable} 分页查询 {@link FileContentDto} 列表
     *
     * @param query    查询条件，根据 {@link FileContentQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<FileContentDto>}
     */
    Page<FileContentDto> pojoSpringPageByQuery(FileContentQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link FileContentDto}
     *
     * @param id {@link FileContent} 主键
     * @return {@link FileContentDto}
     */
    FileContentDto pojoById(Serializable id);

    /**
     * 根据 {@link FileContentQuery} 查询 {@link FileContentDto} 列表
     *
     * @param query 查询条件，根据 {@link FileContentQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link FileContentDto}
     */
    FileContentDto pojoByQuery(FileContentQuery query);

    /**
     * 根据 {@link FileContentQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link FileContentQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(FileContentQuery query);

    /**
     * 根据 id 列表查询 {@link FileContentDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link FileContentQuery} 和 {@link Pageable} 分页查询 {@link FileContentDto} 列表，并导出 excel 表单
     *  @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link FileContentQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, FileContentQuery query, HttpServletResponse response);

    /**
     * 将 {@link FileContent} 转为 {@link FileContentDto}
     *
     * @param entity 数据实体
     * @return /
     */
    FileContentDto doToDto(FileContent entity);

    /**
     * 将 {@link FileContentDto} 转为 {@link FileContent}
     *
     * @param dto Dto 实体
     * @return /
     */
    FileContent dtoToDo(FileContentDto dto);

}
