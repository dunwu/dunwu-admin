package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.security.service.SecurityUtil;
import io.github.dunwu.modules.system.dao.SysMenuDao;
import io.github.dunwu.modules.system.dao.SysRoleMenuDao;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.SysRoleMenu;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.query.SysMenuQuery;
import io.github.dunwu.modules.system.entity.vo.MenuVo;
import io.github.dunwu.modules.system.service.SysMenuService;
import io.github.dunwu.modules.system.service.SysRoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl implements SysMenuService {

    private final SysMenuDao menuDao;
    private final SysRoleMenuDao roleMenuDao;
    private final SysRoleService roleService;

    @Override
    public boolean save(SysMenu entity) {
        return menuDao.save(entity);
    }

    @Override
    public boolean updateById(SysMenu entity) {
        return menuDao.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return menuDao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return menuDao.removeByIds(ids);
    }

    @Override
    public Page<SysMenuDto> pojoPageByQuery(Object query, Pageable pageable) {
        return menuDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<SysMenuDto> pojoListByQuery(Object query) {
        return menuDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public SysMenuDto pojoById(Serializable id) {
        return menuDao.pojoById(id, this::doToDto);
    }

    @Override
    public SysMenuDto pojoByQuery(Object query) {
        return menuDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return menuDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysMenuDto> list = menuDao.pojoListByIds(ids, this::doToDto);
        menuDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysMenuDto> page = menuDao.pojoPageByQuery(query, pageable, this::doToDto);
        menuDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public Collection<SysMenuDto> treeList(Object query) {
        Collection<SysMenuDto> list = pojoListByQuery(query);
        return menuDao.buildTreeList(list);
    }

    @Override
    public Map<String, Object> buildTreeList(Collection<SysMenuDto> list) {
        Collection<SysMenuDto> trees = menuDao.buildTreeList(list);
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", trees);
        map.put("totalElements", trees.size());
        return map;
    }

    @Override
    public List<MenuVo> buildMenuListForCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<SysRoleDto> roles = roleService.pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }

        Set<Long> roleIds = roles.stream()
                                 .filter(Objects::nonNull)
                                 .map(SysRoleDto::getId)
                                 .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }

        List<SysMenuDto> menuList = pojoListByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(menuList)) {
            return new ArrayList<>();
        }

        Collection<SysMenuDto> menuDtos = menuDao.buildTreeList(menuList);
        return menuDao.buildFrontMenus(menuDtos);
    }

    @Override
    public List<Long> childrenIds(Long id) {
        SysMenuQuery query = new SysMenuQuery();
        query.setPid(id);
        List<SysMenu> menus = menuDao.listByQuery(query);
        if (CollectionUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }

        List<Long> ids = new ArrayList<>();
        for (SysMenu i : menus) {
            if (i == null) {
                continue;
            }

            ids.add(i.getId());
            // 递归获取子节点 ID
            List<Long> childrenIds = childrenIds(i.getId());
            ids.addAll(childrenIds);
        }

        return ids;
    }

    private SysMenuDto doToDto(SysMenu model) {
        if (model == null) {
            return null;
        }

        SysMenuDto dto = BeanUtil.toBean(model, SysMenuDto.class);
        dto.setLabel(dto.getTitle());
        return dto;
    }

    private SysMenu dtoToDo(SysMenuDto entity) {
        return BeanUtil.toBean(entity, SysMenu.class);
    }

    private List<SysMenuDto> pojoListByRoleIds(Collection<Long> roleIds) {
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            List<SysRoleMenu> rolesMenus = roleMenuDao.list(Wrappers.query(roleMenu));
            menuIds.addAll(rolesMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        }

        List<SysMenu> list = menuDao.listByIds(menuIds);
        return list.stream()
                   .filter(i -> StrUtil.isNotBlank(i.getPath()))
                   .map(this::doToDto)
                   .collect(Collectors.toList());
    }

}
