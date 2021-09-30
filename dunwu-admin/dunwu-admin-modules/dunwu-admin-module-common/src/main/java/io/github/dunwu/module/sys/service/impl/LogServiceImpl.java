package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.sys.dao.LogDao;
import io.github.dunwu.module.sys.entity.Log;
import io.github.dunwu.module.sys.entity.dto.LogDto;
import io.github.dunwu.module.sys.entity.query.LogQuery;
import io.github.dunwu.module.sys.service.LogService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.log.AppLogInfo;
import io.github.dunwu.tool.web.log.LogStorage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志记录 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
@Service
public class LogServiceImpl extends ServiceImpl implements LogService, LogStorage {

    private final LogDao dao;

    public LogServiceImpl(LogDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Log entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Log> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(Log entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Log> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(Log entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Log> list) {
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
    public List<LogDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<LogDto> pojoListByQuery(LogQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<LogDto> pojoSpringPageByQuery(LogQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public LogDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public LogDto pojoByQuery(LogQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(LogQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<LogDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(LogQuery query, Pageable pageable, HttpServletResponse response) {
        Page<LogDto> page = dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 LogDto 列表，导出 excel 表单
     *
     * @param list     {@link LogDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<LogDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (LogDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("日志级别", item.getLevel());
            map.put("业务类型", item.getBizType());
            map.put("日志消息", item.getMessage());
            map.put("异常信息，只有日志级别为ERROR时才有值", item.getExceptionMessage());
            map.put("操作的类名", item.getClassName());
            map.put("操作的方法名", item.getMethodName());
            map.put("被调用方法的参数", item.getParams());
            map.put("操作类型", item.getOperateType());
            map.put("操作者ID", item.getOperatorId());
            map.put("操作者用户名", item.getOperatorName());
            map.put("服务端IP地址", item.getServerIp());
            map.put("客户端IP地址", item.getClientIp());
            map.put("客户端地理位置", item.getClientLocation());
            map.put("客户端设备", item.getClientDevice());
            map.put("HTTP请求的耗时", item.getRequestTime());
            map.put("日志记录时间", item.getCreateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public LogDto doToDto(Log entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, LogDto.class);
    }

    @Override
    public Log dtoToDo(LogDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Log.class);
    }

    @Override
    public boolean store(AppLogInfo logInfo) {
        if (logInfo == null) {
            return false;
        }
        Log entity = BeanUtil.toBean(logInfo, Log.class);
        return dao.insert(entity);
    }

}
