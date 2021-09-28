package io.github.dunwu.module.demo.service;

import io.github.dunwu.module.demo.entity.Hello;
import io.github.dunwu.module.demo.entity.dto.HelloDto;
import io.github.dunwu.module.demo.entity.query.HelloQuery;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.github.dunwu.tool.data.mybatis.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-22
 */
public interface HelloService extends IService {

    /**
     * 添加一条 {@link Hello} 记录
     *
     * @param entity {@link Hello} 数据实体
     * @return true / false
     */
    boolean insert(Hello entity);

    /**
     * 批量添加 {@link Hello} 记录
     *
     * @param list {@link Hello} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<Hello> list);

    /**
     * 根据 ID 更新一条 {@link Hello} 记录
     *
     * @param entity {@link Hello} 数据实体
     * @return true / false
     */
    boolean updateById(Hello entity);

    /**
     * 根据 ID 批量更新 {@link Hello} 记录
     *
     * @param list {@link Hello} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<Hello> list);

    /**
     * 添加或更新一条 {@link Hello} 记录
     *
     * @param entity {@link Hello} 数据实体
     * @return true / false
     */
    boolean save(Hello entity);

    /**
     * 批量添加或更新 {@link Hello} 记录
     *
     * @param list {@link Hello} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<Hello> list);

    /**
     * 根据 ID 删除一条 {@link Hello} 记录
     *
     * @param id {@link Hello} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link Hello} 记录
     *
     * @param ids {@link Hello} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link HelloQuery} 查询 {@link HelloDto} 列表
     *
     * @param query 查询条件，根据 HelloQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<HelloDto>}
     */
    List<HelloDto> pojoListByQuery(HelloQuery query);

    /**
     * 根据 {@link HelloQuery} 和 {@link Pageable} 分页查询 {@link HelloDto} 列表
     *
     * @param query    查询条件，根据 HelloQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<HelloDto>}
     */
    Page<HelloDto> pojoSpringPageByQuery(HelloQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link HelloDto}
     *
     * @param id {@link Hello} 主键
     * @return {@link HelloDto}
     */
    HelloDto pojoById(Serializable id);

    /**
     * 根据 {@link HelloQuery} 查询 {@link HelloDto} 列表
     *
     * @param query 查询条件，根据 HelloQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link HelloDto}
     */
    HelloDto pojoByQuery(HelloQuery query);

    /**
     * 根据 {@link HelloQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 HelloQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(HelloQuery query);

    /**
     * 根据 id 列表查询 {@link HelloDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link HelloQuery} 和 {@link Pageable} 分页查询 {@link HelloDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 HelloQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link Hello} 转为 {@link HelloDto}
     *
     * @param model 数据实体
     * @return /
     */
    HelloDto doToDto(Hello model);

    /**
     * 将 {@link HelloDto} 转为 {@link Hello}
     *
     * @param dto Dto 实体
     * @return /
     */
    Hello dtoToDo(HelloDto dto);

}
