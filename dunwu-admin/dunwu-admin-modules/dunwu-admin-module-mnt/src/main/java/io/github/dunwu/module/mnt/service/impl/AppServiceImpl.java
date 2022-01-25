package io.github.dunwu.module.mnt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.module.mnt.dao.AppDao;
import io.github.dunwu.module.mnt.entity.App;
import io.github.dunwu.module.mnt.entity.dto.AppDto;
import io.github.dunwu.module.mnt.entity.query.AppQuery;
import io.github.dunwu.module.mnt.service.AppService;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 应用配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Service
public class AppServiceImpl extends ServiceImpl implements AppService {

    private final AppDao dao;

    public AppServiceImpl(AppDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(App entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<App> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(App entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<App> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(App entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<App> list) {
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
    public List<AppDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<AppDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<AppDto> pojoListByQuery(AppQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<AppDto> pojoSpringPageByQuery(AppQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public AppDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public AppDto pojoByQuery(AppQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(AppQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<AppDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, AppQuery query, HttpServletResponse response) {
        Page<AppDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 AppDto 列表，导出 excel 表单
     *
     * @param list     {@link AppDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<AppDto> list, HttpServletResponse response) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (AppDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("应用名称", item.getName());
            map.put("上传路径", item.getUploadPath());
            map.put("部署路径", item.getDeployPath());
            map.put("备份路径", item.getBackupPath());
            map.put("应用端口", item.getPort());
            map.put("启动脚本", item.getStartScript());
            map.put("部署脚本", item.getDeployScript());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ExcelUtil.downloadExcel(response, mapList);
    }

    @Override
    public AppDto doToDto(App entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, AppDto.class);
    }

    @Override
    public App dtoToDo(AppDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, App.class);
    }

}
