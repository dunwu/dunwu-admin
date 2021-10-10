package io.github.dunwu.module.mnt.service;

import io.github.dunwu.module.mnt.entity.Server;
import io.github.dunwu.module.mnt.entity.dto.ServerDto;
import io.github.dunwu.module.mnt.entity.query.ServerQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务器配置表 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public interface ServerService extends IService {

    /**
     * 添加一条 {@link Server} 记录
     *
     * @param entity {@link Server} 数据实体
     * @return true / false
     */
    boolean insert(Server entity);

    /**
     * 批量添加 {@link Server} 记录
     *
     * @param list {@link Server} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Server> list);

    /**
     * 根据 ID 更新一条 {@link Server} 记录
     *
     * @param entity {@link Server} 数据实体
     * @return true / false
     */
    boolean updateById(Server entity);

    /**
     * 根据 ID 批量更新 {@link Server} 记录
     *
     * @param list {@link Server} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Server> list);

    /**
     * 添加或更新一条 {@link Server} 记录
     *
     * @param entity {@link Server} 数据实体
     * @return true / false
     */
    boolean save(Server entity);

    /**
     * 批量添加或更新 {@link Server} 记录
     *
     * @param list {@link Server} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Server> list);

    /**
     * 根据 ID 删除一条 {@link Server} 记录
     *
     * @param id {@link Server} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Server} 记录
     *
     * @param ids {@link Server} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link ServerDto} 全量数据列表
     *
     * @return {@link List<ServerDto>}
     */
    List<ServerDto> pojoList();

    List<ServerDto> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link ServerQuery} 查询 {@link ServerDto} 列表
     *
     * @param query 查询条件，根据 {@link ServerQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<ServerDto>}
     */
    List<ServerDto> pojoListByQuery(ServerQuery query);

    /**
     * 根据 {@link ServerQuery} 和 {@link Pageable} 分页查询 {@link ServerDto} 列表
     *
     * @param query    查询条件，根据 {@link ServerQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<ServerDto>}
     */
    Page<ServerDto> pojoSpringPageByQuery(ServerQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link ServerDto}
     *
     * @param id {@link Server} 主键
     * @return {@link ServerDto}
     */
    ServerDto pojoById(Serializable id);

    /**
     * 根据 {@link ServerQuery} 查询 {@link ServerDto} 列表
     *
     * @param query 查询条件，根据 {@link ServerQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link ServerDto}
     */
    ServerDto pojoByQuery(ServerQuery query);

    /**
     * 根据 {@link ServerQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link ServerQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(ServerQuery query);

    /**
     * 根据 id 列表查询 {@link ServerDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link ServerQuery} 和 {@link Pageable} 分页查询 {@link ServerDto} 列表，并导出 excel 表单
     *  @param pageable 分页查询条件
     * @param query    查询条件，根据 {@link ServerQuery} 中的 {@link QueryField} 注解自动组装查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Pageable pageable, ServerQuery query, HttpServletResponse response);

    /**
     * 将 {@link Server} 转为 {@link ServerDto}
     *
     * @param entity 数据实体
     * @return /
     */
    ServerDto doToDto(Server entity);

    /**
     * 将 {@link ServerDto} 转为 {@link Server}
     *
     * @param dto Dto 实体
     * @return /
     */
    Server dtoToDo(ServerDto dto);

    /**
     * 根据IP查询
     *
     * @param ip /
     * @return /
     */
    ServerDto pojoByIp(String ip);

    List<ServerDto> pojoListByDeployId(Long deployId);

    /**
     * 测试登录服务器
     *
     * @param dto Dto 实体
     * @return /
     */
    Boolean testConnect(ServerDto dto);

}
