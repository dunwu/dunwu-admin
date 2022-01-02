package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.sys.dao.OperationLogDao;
import io.github.dunwu.module.sys.entity.OperationLog;
import io.github.dunwu.module.sys.entity.dto.OperationLogDto;
import io.github.dunwu.module.sys.entity.query.OperationLogQuery;
import io.github.dunwu.module.sys.service.OperationLogService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.log.entity.LogRecord;
import io.github.dunwu.tool.web.log.service.LogRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl implements OperationLogService, LogRecordService {

    private final OperationLogDao dao;

    public OperationLogServiceImpl(OperationLogDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(OperationLog entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<OperationLog> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(OperationLog entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<OperationLog> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(OperationLog entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<OperationLog> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<OperationLogDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<OperationLogDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<OperationLogDto> pojoListByQuery(OperationLogQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<OperationLogDto> pojoSpringPageByQuery(Pageable pageable, OperationLogQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public OperationLogDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public OperationLogDto pojoByQuery(OperationLogQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(OperationLogQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<OperationLogDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, OperationLogQuery query, HttpServletResponse response) {
        Page<OperationLogDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 OperationLogDto 列表，导出 excel 表单
     *
     * @param list     {@link OperationLogDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<OperationLogDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (OperationLogDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("应用名", item.getAppName());
            map.put("业务编码", item.getBizNo());
            map.put("业务类型", item.getBizType());
            map.put("是否成功：0.失败；1.成功", item.getSuccess());
            map.put("基本信息", item.getMessage());
            map.put("详情信息", item.getDetail());
            map.put("异常信息", item.getException());
            map.put("类名", item.getClassName());
            map.put("方法名", item.getMethodName());
            map.put("方法参数", item.getParams());
            map.put("操作类型", item.getOperation());
            map.put("操作者ID", item.getOperatorId());
            map.put("操作者用户名", item.getOperatorName());
            map.put("服务端IP地址", item.getServerIp());
            map.put("客户端IP地址", item.getClientIp());
            map.put("客户端地理位置", item.getClientLocation());
            map.put("客户端设备", item.getClientDevice());
            map.put("操作耗时", item.getRequestTime());
            map.put("操作时间", item.getCreateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public OperationLogDto doToDto(OperationLog entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, OperationLogDto.class);
    }

    @Override
    public OperationLog dtoToDo(OperationLogDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, OperationLog.class);
    }

    @Override
    public boolean store(LogRecord record) {
        if (record == null) {
            return false;
        }
        OperationLog entity = BeanUtil.toBean(record, OperationLog.class);
        return dao.insert(entity);
    }

}
