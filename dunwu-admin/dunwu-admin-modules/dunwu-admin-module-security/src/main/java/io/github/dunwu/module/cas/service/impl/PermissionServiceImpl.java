package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.module.cas.constant.enums.PermissionType;
import io.github.dunwu.module.cas.dao.PermissionDao;
import io.github.dunwu.module.cas.dao.RolePermissionMapDao;
import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.module.cas.entity.dto.PermissionDto;
import io.github.dunwu.module.cas.entity.query.PermissionQuery;
import io.github.dunwu.module.cas.service.PermissionService;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl implements PermissionService {

    private final PermissionDao permissionDao;
    private final RolePermissionMapDao rolePermissionDao;

    @Override
    public boolean insert(Permission entity) {
        return permissionDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Permission> list) {
        return permissionDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Permission entity) {
        return permissionDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Permission> list) {
        return permissionDao.updateBatchById(list);
    }

    @Override
    public boolean save(Permission entity) {
        return permissionDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Permission> list) {
        return permissionDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return permissionDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return permissionDao.deleteBatchByIds(ids);
    }

    @Override
    public List<PermissionDto> pojoList() {
        return permissionDao.pojoList(this::doToDto);
    }

    @Override
    public List<PermissionDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return permissionDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<PermissionDto> pojoListByQuery(PermissionQuery query) {
        return permissionDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<PermissionDto> pojoSpringPageByQuery(Pageable pageable, PermissionQuery query) {
        return permissionDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public PermissionDto pojoById(Serializable id) {
        return permissionDao.pojoById(id, this::doToDto);
    }

    @Override
    public PermissionDto pojoByQuery(PermissionQuery query) {
        return permissionDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(PermissionQuery query) {
        return permissionDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<PermissionDto> list = permissionDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, PermissionQuery query, HttpServletResponse response) {
        Page<PermissionDto> page = permissionDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
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
            map.put("资源ID", item.getResourceId());
            map.put("权限编码", item.getCode());
            map.put("权限名称", item.getName());
            map.put("权限类型", item.getType());
            map.put("权限表达式", item.getExpression());
            map.put("是否禁用：1 表示禁用；0 表示启用", item.getDisabled());
            map.put("备注", item.getNote());
            map.put("创建者ID", item.getCreatorId());
            map.put("更新者ID", item.getUpdaterId());
            map.put("创建者名称", item.getCreatorName());
            map.put("更新者名称", item.getUpdaterName());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ExcelUtil.downloadExcel(response, mapList);
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

    @Override
    public boolean saveByResourceId(Permission entity) {
        return permissionDao.saveByResourceId(entity);
    }

    @Override
    public boolean deleteByResourceId(Serializable resourceId) {
        return permissionDao.deleteByResourceId(resourceId);
    }

    @Override
    public boolean deleteBatchByResourceIds(Collection<? extends Serializable> resourceIds) {
        return permissionDao.deleteBatchByResourceIds(resourceIds);
    }

    @Override
    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        Set<Long> permissionIds = rolePermissionDao.getPermissionIdsByRoleId(roleId);
        if (CollectionUtil.isEmpty(permissionIds)) {
            return null;
        }

        return getMenuIdsByPermissionIds(permissionIds);
    }

    @Override
    public Set<Long> getMenuIdsByRoleIds(Collection<Long> roleIds) {
        Set<Long> permissionIds = rolePermissionDao.getPermissionIdsByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(permissionIds)) {
            return null;
        }

        return getMenuIdsByPermissionIds(permissionIds);
    }

    private Set<Long> getMenuIdsByPermissionIds(Set<Long> permissionIds) {
        List<Permission> permissions = permissionDao.listByIds(permissionIds);
        if (CollectionUtil.isEmpty(permissions)) {
            return null;
        }

        return permissions.stream()
                          .filter(i -> i.getType() == PermissionType.MENU.getCode())
                          .map(Permission::getResourceId)
                          .collect(Collectors.toSet());
    }

}
