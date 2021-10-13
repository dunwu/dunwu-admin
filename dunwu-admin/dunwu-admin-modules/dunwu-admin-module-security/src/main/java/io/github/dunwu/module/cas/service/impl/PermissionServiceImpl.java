package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.cas.dao.PermissionDao;
import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.module.cas.entity.dto.PermissionDto;
import io.github.dunwu.module.cas.entity.query.PermissionQuery;
import io.github.dunwu.module.cas.service.PermissionService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl implements PermissionService {

    private final PermissionDao dao;

    public PermissionServiceImpl(PermissionDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Permission entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Permission> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(Permission entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Permission> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(Permission entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Permission> list) {
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
    public List<PermissionDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<PermissionDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<PermissionDto> pojoListByQuery(PermissionQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<PermissionDto> pojoSpringPageByQuery(Pageable pageable, PermissionQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public PermissionDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public PermissionDto pojoByQuery(PermissionQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(PermissionQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<PermissionDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, PermissionQuery query, HttpServletResponse response) {
        Page<PermissionDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 PermissionDto 列表，导出 excel 表单
     *
     * @param list     {@link PermissionDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<PermissionDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (PermissionDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("父权限ID", item.getPid());
            map.put("权限编码", item.getCode());
            map.put("权限名称", item.getName());
            map.put("权限类型", item.getType());
            map.put("权限表达式", item.getExpression());
            map.put("是否禁用：1 表示禁用；0 表示启用", item.getDisabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public PermissionDto doToDto(Permission entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, PermissionDto.class);
    }

    @Override
    public Permission dtoToDo(PermissionDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Permission.class);
    }

}
