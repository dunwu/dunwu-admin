package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.cas.dao.MenuDao;
import io.github.dunwu.module.cas.dao.RoleMenuMapDao;
import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.RoleMenuMap;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.MenuQuery;
import io.github.dunwu.module.cas.entity.vo.MenuVo;
import io.github.dunwu.module.cas.service.MenuService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 菜单 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl implements MenuService {

    private final MenuDao menuDao;
    private final RoleMenuMapDao roleMenuDao;
    private final RoleService roleService;
    private final SecurityService securityService;

    @Override
    public boolean insert(Menu entity) {
        return menuDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Menu> list) {
        return menuDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Menu entity) {
        return menuDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Menu> list) {
        return menuDao.updateBatchById(list);
    }

    @Override
    public boolean save(Menu entity) {
        return menuDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Menu> list) {
        return menuDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return menuDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return menuDao.deleteBatchByIds(ids);
    }

    @Override
    public List<MenuDto> pojoList() {
        return menuDao.pojoList(this::doToDto);
    }

    @Override
    public List<MenuDto> pojoListByQuery(MenuQuery query) {
        return menuDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<MenuDto> pojoSpringPageByQuery(MenuQuery query, Pageable pageable) {
        return menuDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public MenuDto pojoById(Serializable id) {
        return menuDao.pojoById(id, this::doToDto);
    }

    @Override
    public MenuDto pojoByQuery(MenuQuery query) {
        return menuDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(MenuQuery query) {
        return menuDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<MenuDto> list = menuDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(MenuQuery query, Pageable pageable, HttpServletResponse response) {
        Page<MenuDto> page = menuDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 MenuDto 列表，导出 excel 表单
     *
     * @param list     {@link MenuDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<MenuDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (MenuDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级菜单ID", item.getPid());
            map.put("子菜单数目", item.getSubCount());
            map.put("菜单类型", item.getType());
            map.put("菜单标题", item.getTitle());
            map.put("组件名称", item.getName());
            map.put("组件", item.getComponent());
            map.put("排序", item.getSequence());
            map.put("图标", item.getIcon());
            map.put("链接地址", item.getPath());
            map.put("是否外链", item.getIFrame());
            map.put("缓存", item.getCache());
            map.put("隐藏", item.getHidden());
            map.put("权限", item.getPermission());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public MenuDto doToDto(Menu entity) {
        if (entity == null) {
            return null;
        }

        MenuDto dto = BeanUtil.toBean(entity, MenuDto.class);
        dto.setLabel(dto.getTitle());
        return dto;
    }

    @Override
    public Menu dtoToDo(MenuDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Menu.class);
    }

    @Override
    public List<MenuDto> treeList(MenuQuery query) {
        List<MenuDto> list = pojoListByQuery(query);
        return buildTreeList(list);
    }

    @Override
    public List<MenuDto> treeListByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<MenuDto> list = new ArrayList<>();
        for (Serializable id : ids) {
            MenuDto entity = pojoById(id);
            if (entity == null) {
                continue;
            }

            if (entity.getPid() != null) {
                // 获取上级菜单
                MenuDto parent = pojoById(entity.getPid());
                list.add(parent);

                // 获取所有同级菜单
                MenuQuery query = new MenuQuery();
                query.setPid(entity.getPid());
                list.addAll(pojoListByQuery(query));
            }
        }

        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        return buildTreeList(list);
    }

    @Override
    public List<MenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        // 查找所有角色绑定的菜单
        Set<Long> menuIds = new HashSet<>();
        for (Long roleId : roleIds) {
            RoleMenuMap roleMenu = new RoleMenuMap();
            roleMenu.setRoleId(roleId);
            List<RoleMenuMap> rolesMenus = roleMenuDao.list(Wrappers.query(roleMenu));
            menuIds.addAll(rolesMenus.stream().map(RoleMenuMap::getMenuId).collect(Collectors.toSet()));
        }

        if (CollectionUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        // 根据菜单 ID 查询菜单详细信息
        List<Menu> list = menuDao.listByIds(menuIds);

        // 过滤菜单项
        Set<MenuDto> set = list.stream()
                               .filter(i -> StrUtil.isNotBlank(i.getPath()))
                               .map(this::doToDto)
                               .collect(Collectors.toSet());
        return buildTreeList(set);
    }

    private List<MenuDto> buildTreeList(Collection<MenuDto> list) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setDeep(10);
        treeNodeConfig.setWeightKey("sequence");
        treeNodeConfig.setSort(Node.SORT.ASC);
        List<MenuDto> menuList = TreeUtil.build(list, 0L, treeNodeConfig, MenuDto.class);
        menuList.forEach(i -> {
            i.setHasChildren(i.getSubCount() > 0);
            i.setLeaf(!i.isHasChildren());
        });
        return menuList;
    }

    @Override
    public List<MenuVo> buildMenuListForCurrentUser() {
        Long userId = securityService.getCurrentUserId();
        List<RoleDto> roles = roleService.pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return new ArrayList<>();
        }

        Set<Long> roleIds = roles.stream()
                                 .filter(Objects::nonNull)
                                 .map(RoleDto::getId)
                                 .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }

        List<MenuDto> treeList = pojoTreeListByRoleIds(roleIds);
        return buildFrontMenus(treeList);
    }

    private List<MenuVo> buildFrontMenus(Collection<MenuDto> list) {
        List<MenuVo> finalVoList = new LinkedList<>();
        for (MenuDto dto : list) {
            if (dto == null) {
                continue;
            }

            Collection<MenuDto> children = dto.getChildren();
            MenuVo menuVo = new MenuVo();
            menuVo.setName(
                ObjectUtil.isNotEmpty(dto.getComponentName()) ? dto.getComponentName() : dto.getName());
            // 一级目录需要加斜杠，不然会报警告
            menuVo.setPath(dto.getPid() == null ? "/" + dto.getPath() : dto.getPath());
            menuVo.setHidden(dto.getHidden());
            // 如果不是外链
            if (!dto.getIFrame()) {
                if (dto.getPid() == null) {
                    menuVo.setComponent(StrUtil.isEmpty(dto.getComponent()) ? "Layout" : dto.getComponent());
                } else if (!StrUtil.isEmpty(dto.getComponent())) {
                    menuVo.setComponent(dto.getComponent());
                }
            }
            menuVo.setMeta(new MenuVo.MenuMetaVo(dto.getTitle(), dto.getIcon(), !dto.getCache()));
            if (CollectionUtil.isNotEmpty(children)) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
                menuVo.setChildren(buildFrontMenus(children));
                // 处理是一级菜单并且没有子菜单的情况
            } else if (dto.getPid() == null) {
                MenuVo menuVo1 = new MenuVo();
                menuVo1.setMeta(menuVo.getMeta());
                // 非外链
                if (!dto.getIFrame()) {
                    menuVo1.setPath("index");
                    menuVo1.setName(menuVo.getName());
                    menuVo1.setComponent(menuVo.getComponent());
                } else {
                    menuVo1.setPath(dto.getPath());
                }
                menuVo.setName(null);
                menuVo.setMeta(null);
                menuVo.setComponent("Layout");
                List<MenuVo> list1 = new ArrayList<>();
                list1.add(menuVo1);
                menuVo.setChildren(list1);
            }

            finalVoList.add(menuVo);
        }
        return finalVoList;
    }

    @Override
    public List<Long> childrenIds(Long id) {
        MenuQuery query = new MenuQuery();
        query.setPid(id);
        List<Menu> menus = menuDao.listByQuery(query);
        if (CollectionUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }

        List<Long> ids = new ArrayList<>();
        for (Menu i : menus) {
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

    private Object treeObject(List<Menu> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                if (menu != null) {
                    List<Menu> menuList = menuDao.listByPid(menu.getId());
                    Map<String, Object> map = new HashMap<>(16);
                    map.put("id", menu.getId());
                    map.put("label", menu.getName());
                    if (menuList != null && menuList.size() != 0) {
                        map.put("children", treeObject(menuList));
                    }
                    list.add(map);
                }
            }
        );
        return list;
    }

}
