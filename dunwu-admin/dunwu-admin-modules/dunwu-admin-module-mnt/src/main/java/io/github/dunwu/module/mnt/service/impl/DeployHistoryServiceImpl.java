package io.github.dunwu.module.mnt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.mnt.dao.DeployHistoryDao;
import io.github.dunwu.module.mnt.entity.DeployHistory;
import io.github.dunwu.module.mnt.entity.dto.DeployHistoryDto;
import io.github.dunwu.module.mnt.entity.query.DeployHistoryQuery;
import io.github.dunwu.module.mnt.service.DeployHistoryService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 部署历史管理 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Service
public class DeployHistoryServiceImpl extends ServiceImpl implements DeployHistoryService {

    private final DeployHistoryDao dao;

    public DeployHistoryServiceImpl(DeployHistoryDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(DeployHistory entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<DeployHistory> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(DeployHistory entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<DeployHistory> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(DeployHistory entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<DeployHistory> list) {
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
    public List<DeployHistoryDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<DeployHistoryDto> pojoListByQuery(DeployHistoryQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DeployHistoryDto> pojoSpringPageByQuery(DeployHistoryQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public DeployHistoryDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public DeployHistoryDto pojoByQuery(DeployHistoryQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DeployHistoryQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DeployHistoryDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, DeployHistoryQuery query, HttpServletResponse response) {
        Page<DeployHistoryDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DeployHistoryDto 列表，导出 excel 表单
     *
     * @param list     {@link DeployHistoryDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DeployHistoryDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeployHistoryDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("部署编号", item.getDeployId());
            map.put("应用名称", item.getAppName());
            map.put("部署日期", item.getDeployDate());
            map.put("部署用户", item.getDeployUser());
            map.put("服务器IP", item.getIp());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public DeployHistoryDto doToDto(DeployHistory entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, DeployHistoryDto.class);
    }

    @Override
    public DeployHistory dtoToDo(DeployHistoryDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, DeployHistory.class);
    }

}
