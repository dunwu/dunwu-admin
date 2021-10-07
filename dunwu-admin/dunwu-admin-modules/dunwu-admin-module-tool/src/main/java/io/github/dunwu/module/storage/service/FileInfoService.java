package io.github.dunwu.module.storage.service;

import io.github.dunwu.module.storage.entity.dto.FileInfoDto;
import io.github.dunwu.module.storage.entity.FileInfo;
import io.github.dunwu.module.storage.entity.query.FileInfoQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件信息表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
public interface FileInfoService extends IService {

    /**
     * 添加一条 {@link FileInfo} 记录
     *
     * @param entity {@link FileInfo} 数据实体
     * @return true / false
     */
    boolean insert(FileInfo entity);

    /**
     * 批量添加 {@link FileInfo} 记录
     *
     * @param list {@link FileInfo} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<FileInfo> list);

    /**
     * 根据 ID 更新一条 {@link FileInfo} 记录
     *
     * @param entity {@link FileInfo} 数据实体
     * @return true / false
     */
    boolean updateById(FileInfo entity);

    /**
     * 根据 ID 批量更新 {@link FileInfo} 记录
     *
     * @param list {@link FileInfo} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<FileInfo> list);

    /**
     * 添加或更新一条 {@link FileInfo} 记录
     *
     * @param entity {@link FileInfo} 数据实体
     * @return true / false
     */
    boolean save(FileInfo entity);

    /**
     * 批量添加或更新 {@link FileInfo} 记录
     *
     * @param list {@link FileInfo} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<FileInfo> list);

    /**
     * 根据 ID 删除一条 {@link FileInfo} 记录
     *
     * @param id {@link FileInfo} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link FileInfo} 记录
     *
     * @param ids {@link FileInfo} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link FileInfoDto} 全量数据列表
     *
     * @return {@link List<FileInfoDto>}
     */
    List<FileInfoDto> pojoList();

    /**
     * 根据 ID 列表查询 {@link FileInfoDto} 列表
     *
     * @param {@link FileInfo} 主键列表
     * @return {@link List<FileInfoDto>}
     */
    List<FileInfoDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link FileInfoQuery} 查询 {@link FileInfoDto} 列表
     *
     * @param query 查询条件，根据 {@link FileInfoQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<FileInfoDto>}
     */
    List<FileInfoDto> pojoListByQuery(FileInfoQuery query);

    /**
     * 根据 {@link FileInfoQuery} 和 {@link Pageable} 分页查询 {@link FileInfoDto} 列表
     *
     * @param query    查询条件，根据 {@link FileInfoQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<FileInfoDto>}
     */
    Page<FileInfoDto> pojoSpringPageByQuery(FileInfoQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link FileInfoDto}
     *
     * @param id {@link FileInfo} 主键
     * @return {@link FileInfoDto}
     */
    FileInfoDto pojoById(Serializable id);

    /**
     * 根据 {@link FileInfoQuery} 查询 {@link FileInfoDto} 列表
     *
     * @param query 查询条件，根据 {@link FileInfoQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link FileInfoDto}
     */
    FileInfoDto pojoByQuery(FileInfoQuery query);

    /**
     * 根据 {@link FileInfoQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link FileInfoQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(FileInfoQuery query);

    /**
     * 根据 id 列表查询 {@link FileInfoDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link FileInfoQuery} 和 {@link Pageable} 分页查询 {@link FileInfoDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link FileInfoQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(FileInfoQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link FileInfo} 转为 {@link FileInfoDto}
     *
     * @param entity 数据实体
     * @return /
     */
    FileInfoDto doToDto(FileInfo entity);

    /**
     * 将 {@link FileInfoDto} 转为 {@link FileInfo}
     *
     * @param dto Dto 实体
     * @return /
     */
    FileInfo dtoToDo(FileInfoDto dto);

}
