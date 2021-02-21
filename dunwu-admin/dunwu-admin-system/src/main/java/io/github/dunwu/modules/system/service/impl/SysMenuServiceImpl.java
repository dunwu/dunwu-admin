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
        return menuDao.pojoPageByQuery(query, pageable, SysMenuDto.class);
    }

    @Override
    public List<SysMenuDto> pojoListByQuery(Object query) {
        return menuDao.pojoListByQuery(query, SysMenuDto.class);
    }

    @Override
    public SysMenuDto pojoById(Serializable id) {
        return menuDao.pojoById(id, SysMenuDto.class);
    }

    @Override
    public SysMenuDto pojoByQuery(Object query) {
        return menuDao.pojoByQuery(query, SysMenuDto.class);
    }

    @Override
    public Integer countByQuery(Object query) {
        return menuDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysMenuDto> list = menuDao.pojoListByIds(ids, SysMenuDto.class);
        menuDao.exportDtoList(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysMenuDto> page = menuDao.pojoPageByQuery(query, pageable, SysMenuDto.class);
        menuDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public Object treeObject() {
        return menuDao.treeObject(menuDao.listByPid(0L));
    }

    @Override
    public Map<String, Object> treeListMap(Object query) {
        Collection<SysMenuDto> list = pojoListByQuery(query);
        Collection<SysMenuDto> trees = menuDao.buildTreeList(list);
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", trees);
        map.put("totalElements", trees.size());
        return map;
    }

    @Override
    public List<SysMenuDto> pojoListByRoleIds(Collection<Long> roleIds) {
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            List<SysRoleMenu> rolesMenus = roleMenuDao.list(Wrappers.query(roleMenu));
            menuIds.addAll(rolesMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet()));
        }

        List<SysMenu> list = menuDao.listByIds(menuIds);
        List<SysMenu> filterList = list.stream().filter(i -> StrUtil.isNotBlank(i.getPath()))
            .collect(Collectors.toList());
        return BeanUtil.toBeanList(filterList, SysMenuDto.class);
    }

    @Override
    public List<MenuVo> buildMenuListForCurrentUser() {
        List<MenuVo> finalMenuList = new ArrayList<>();
        Long userId = SecurityUtil.getCurrentUserId();

        List<SysRoleDto> roles = roleService.pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return finalMenuList;
        }

        Set<Long> roleIds = roles.stream().map(SysRoleDto::getId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return finalMenuList;
        }

        List<SysMenuDto> menuList = pojoListByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(menuList)) {
            return finalMenuList;
        }

        Collection<SysMenuDto> menuDtos = menuDao.buildTreeList(menuList);
        return menuDao.buildFrontMenus(menuDtos);
    }

}
