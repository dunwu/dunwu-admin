package io.github.dunwu.module.mnt.service;

import io.github.dunwu.module.mnt.entity.Deploy;
import io.github.dunwu.module.mnt.entity.dto.DeployDto;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.query.DeployQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 部署管理 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public interface DeployService extends IService {

    /**
     * 添加一条 {@link DeployDto} 记录
     *
     * @param dto {@link DeployDto} 数据实体
     * @return true / false
     */
    boolean insert(DeployDto dto);

    /**
     * 批量添加 {@link Deploy} 记录
     *
     * @param list {@link Deploy} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Deploy> list);

    /**
     * 根据 ID 更新一条 {@link DeployDto} 记录
     *
     * @param dto {@link DeployDto} 数据实体
     * @return true / false
     */
    boolean updateById(DeployDto dto);

    /**
     * 根据 ID 批量更新 {@link Deploy} 记录
     *
     * @param list {@link Deploy} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Deploy> list);

    /**
     * 添加或更新一条 {@link Deploy} 记录
     *
     * @param entity {@link Deploy} 数据实体
     * @return true / false
     */
    boolean save(Deploy entity);

    /**
     * 批量添加或更新 {@link Deploy} 记录
     *
     * @param list {@link Deploy} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Deploy> list);

    /**
     * 根据 ID 删除一条 {@link Deploy} 记录
     *
     * @param id {@link Deploy} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Deploy} 记录
     *
     * @param ids {@link Deploy} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link DeployDto} 全量数据列表
     *
     * @return {@link List<DeployDto>}
     */
    List<DeployDto> pojoList();

    /**
     * 根据 {@link DeployQuery} 查询 {@link DeployDto} 列表
     *
     * @param query 查询条件，根据 {@link DeployQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<DeployDto>}
     */
    List<DeployDto> pojoListByQuery(DeployQuery query);

    /**
     * 根据 {@link DeployQuery} 和 {@link Pageable} 分页查询 {@link DeployDto} 列表
     *
     * @param query    查询条件，根据 {@link DeployQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<DeployDto>}
     */
    Page<DeployDto> pojoSpringPageByQuery(DeployQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link DeployDto}
     *
     * @param id {@link Deploy} 主键
     * @return {@link DeployDto}
     */
    DeployDto pojoById(Serializable id);

    /**
     * 根据 {@link DeployQuery} 查询 {@link DeployDto} 列表
     *
     * @param query 查询条件，根据 {@link DeployQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link DeployDto}
     */
    DeployDto pojoByQuery(DeployQuery query);

    /**
     * 根据 {@link DeployQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link DeployQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(DeployQuery query);

    /**
     * 根据 id 列表查询 {@link DeployDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link DeployQuery} 和 {@link Pageable} 分页查询 {@link DeployDto} 列表，并导出 excel 表单
     *
     * @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link DeployQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, DeployQuery query, HttpServletResponse response);

    /**
     * 将 {@link Deploy} 转为 {@link DeployDto}
     *
     * @param entity 数据实体
     * @return /
     */
    DeployDto doToDto(Deploy entity);

    /**
     * 将 {@link DeployDto} 转为 {@link Deploy}
     *
     * @param dto Dto 实体
     * @return /
     */
    Deploy dtoToDo(DeployDto dto);

    /**
     * 部署文件到服务器
     *
     * @param fileSavePath 文件路径
     * @param appId        应用ID
     */
    void deployApp(String fileSavePath, Long appId);

    /**
     * 查询部署状态
     *
     * @param dto /
     * @return /
     */
    String getServerStatus(DeployDto dto);

    /**
     * 启动服务
     *
     * @param dto /
     * @return /
     */
    String startServer(DeployDto dto);

    /**
     * 停止服务
     *
     * @param dto /
     * @return /
     */
    String stopServer(DeployDto dto);

    /**
     * 停止服务
     *
     * @param dto /
     * @return /
     */
    String rollbackServer(DeployHistoryDto dto);

}
