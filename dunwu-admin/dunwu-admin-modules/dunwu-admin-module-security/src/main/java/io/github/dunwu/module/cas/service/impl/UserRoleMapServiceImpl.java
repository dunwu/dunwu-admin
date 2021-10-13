package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.UserRoleMapDao;
import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.module.cas.entity.dto.UserRoleMapDto;
import io.github.dunwu.module.cas.entity.query.UserRoleMapQuery;
import io.github.dunwu.module.cas.service.UserRoleMapService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户角色关联表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Service
public class UserRoleMapServiceImpl extends ServiceImpl implements UserRoleMapService {

    private final UserRoleMapDao dao;

    public UserRoleMapServiceImpl(UserRoleMapDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(UserRoleMap entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<UserRoleMap> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(UserRoleMap entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<UserRoleMap> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(UserRoleMap entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<UserRoleMap> list) {
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
    public List<UserRoleMapDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<UserRoleMapDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<UserRoleMapDto> pojoListByQuery(UserRoleMapQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<UserRoleMapDto> pojoSpringPageByQuery(Pageable pageable, UserRoleMapQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public UserRoleMapDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public UserRoleMapDto pojoByQuery(UserRoleMapQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(UserRoleMapQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<UserRoleMapDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, UserRoleMapQuery query, HttpServletResponse response) {
        Page<UserRoleMapDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 UserRoleMapDto 列表，导出 excel 表单
     *
     * @param list     {@link UserRoleMapDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<UserRoleMapDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (UserRoleMapDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("用户ID", item.getUserId());
            map.put("角色ID", item.getRoleId());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public UserRoleMapDto doToDto(UserRoleMap entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, UserRoleMapDto.class);
    }

    @Override
    public UserRoleMap dtoToDo(UserRoleMapDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, UserRoleMap.class);
    }

    @Override
    public boolean insertBatchByRoleIds(Long userId, Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return false;
        }

        Set<? extends Serializable> oldRecords = getRoleIdsByUserId(userId);
        roleIds.removeIf(oldRecords::contains);
        if (CollectionUtil.isEmpty(roleIds)) {
            return true;
        }
        List<UserRoleMap> list = roleIds.stream().map(i -> new UserRoleMap(userId, i))
                                        .collect(Collectors.toList());
        return dao.insertBatch(list);
    }

    @Override
    public boolean deleteBatchByRoleIds(Long userId, Collection<Long> roleIds) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId)
               .in(UserRoleMap.ROLE_ID, roleIds);
        return dao.delete(wrapper);
    }

    @Override
    public boolean deleteByUserId(Serializable userId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId);
        return dao.delete(wrapper);
    }

    @Override
    public boolean deleteByRoleId(Serializable roleId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.ROLE_ID, roleId);
        return dao.delete(wrapper);
    }

    @Override
    public Set<? extends Serializable> getRoleIdsByUserId(Serializable userId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.USER_ID, userId);
        List<UserRoleMap> usersRoles = dao.list(wrapper);
        if (CollectionUtil.isEmpty(usersRoles)) {
            return Collections.emptySet();
        }

        return usersRoles.stream().map(UserRoleMap::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<? extends Serializable> getUserIdsByRoleId(Serializable roleId) {
        QueryWrapper<UserRoleMap> wrapper = new QueryWrapper<>();
        wrapper.eq(UserRoleMap.ROLE_ID, roleId);
        List<UserRoleMap> usersRoles = dao.list(wrapper);
        if (CollectionUtil.isEmpty(usersRoles)) {
            return Collections.emptySet();
        }

        return usersRoles.stream().map(UserRoleMap::getUserId).collect(Collectors.toSet());
    }

}
