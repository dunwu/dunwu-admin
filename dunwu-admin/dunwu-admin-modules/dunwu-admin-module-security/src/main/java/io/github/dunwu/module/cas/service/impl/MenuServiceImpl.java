package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.cas.constant.enums.PermissionType;
import io.github.dunwu.module.cas.dao.MenuDao;
import io.github.dunwu.module.cas.dao.PermissionDao;
import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.Permission;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.MenuQuery;
import io.github.dunwu.module.cas.entity.vo.VueElementMenuVo;
import io.github.dunwu.module.cas.service.MenuService;
import io.github.dunwu.module.cas.service.PermissionService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.data.exception.DataException;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 菜单表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-16
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl implements MenuService {

    private final MenuDao menuDao;
    private final PermissionDao permissionDao;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final SecurityService securityService;
    private static final CopyOptions options;

    static {
        options = new CopyOptions();
        options.setIgnoreProperties(Menu.ID, Menu.PID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Menu entity) {
        autoRefreshLevelAndChildrenNum(entity);
        boolean isOk = menuDao.insert(entity);
        if (!isOk) {
            return false;
        }

        Permission permission = BeanUtil.toBean(entity, Permission.class, options);
        permission.setResourceId(entity.getId());
        permission.setType(PermissionType.MENU.getCode());
        return permissionDao.insert(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<Menu> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }

        list.forEach(this::insert);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Menu entity) {
        autoRefreshLevelAndChildrenNum(entity);
        boolean isOk = menuDao.updateById(entity);
        if (!isOk) {
            return false;
        }

        Permission permission = BeanUtil.toBean(entity, Permission.class, options);
        permission.setResourceId(entity.getId());
        permission.setType(PermissionType.MENU.getCode());
        return permissionDao.saveByResourceId(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<Menu> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }

        list.forEach(this::updateById);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Menu entity) {
        autoRefreshLevelAndChildrenNum(entity);
        boolean isOk = menuDao.save(entity);
        if (!isOk) {
            return false;
        }

        Permission permission = BeanUtil.toBean(entity, Permission.class, options);
        permission.setResourceId(entity.getId());
        permission.setType(PermissionType.MENU.getCode());
        return permissionDao.saveByResourceId(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatch(Collection<Menu> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }

        list.forEach(this::save);
        return true;
    }

    private void autoRefreshLevelAndChildrenNum(Menu entity) {

        if (entity.getId() != null) {
            Menu oldRecord = menuDao.getById(entity.getId());
            if (!oldRecord.getPid().equals(entity.getPid())) {
                if (oldRecord.getPid() != 0) {
                    Menu oldParentRecord = menuDao.getById(oldRecord.getPid());
                    int num = oldParentRecord.getChildrenNum() - 1;
                    int max = Math.max(num, 0);
                    oldParentRecord.setChildrenNum(max);
                    menuDao.updateById(oldParentRecord);
                }
            }
        }

        if (entity.getPid() != 0) {
            Menu newParentRecord = menuDao.getById(entity.getPid());
            if (newParentRecord == null) {
                String msg = StrUtil.format("上级菜单 id = {} 不存在", entity.getPid());
                throw new DataException(msg);
            }

            newParentRecord.setChildrenNum(newParentRecord.getChildrenNum() + 1);
            menuDao.updateById(newParentRecord);

            entity.setLevel(newParentRecord.getLevel() + 1);
        } else {
            entity.setLevel(1);
        }
    }

    @Override
    public boolean deleteById(Serializable id) {
        boolean isOk = menuDao.deleteById(id);
        if (!isOk) {
            return false;
        }

        return permissionDao.deleteByResourceId(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }

        boolean isOk = menuDao.deleteBatchByIds(ids);
        if (!isOk) {
            return false;
        }

        return permissionDao.deleteBatchByResourceIds(ids);
    }

    @Override
    public List<MenuDto> pojoList() {
        return menuDao.pojoList(this::doToDto);
    }

    @Override
    public List<MenuDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return menuDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<MenuDto> pojoListByQuery(MenuQuery query) {
        return menuDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<MenuDto> pojoSpringPageByQuery(Pageable pageable, MenuQuery query) {
        return menuDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
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
    public void exportPage(Pageable pageable, MenuQuery query, HttpServletResponse response) {
        Page<MenuDto> page = menuDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
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
            map.put("菜单编码", item.getCode());
            map.put("菜单名称", item.getName());
            map.put("权限表达式", item.getExpression());
            map.put("菜单类型", item.getMenuType());
            map.put("组件", item.getComponent());
            map.put("菜单顺序", item.getSequence());
            map.put("图标", item.getIcon());
            map.put("链接地址", item.getPath());
            map.put("是否外链", item.getFrame());
            map.put("缓存", item.getCached());
            map.put("隐藏", item.getHidden());
            map.put("菜单级别", item.getLevel());
            map.put("子菜单数目", item.getChildrenNum());
            map.put("备注", item.getNote());
            map.put("是否禁用：1 表示禁用；0 表示启用", item.getDisabled());
            map.put("创建者ID", item.getCreatorId());
            map.put("更新者ID", item.getUpdaterId());
            map.put("创建者名称", item.getCreatorName());
            map.put("更新者名称", item.getUpdaterName());
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
        dto.setLabel(dto.getName());
        dto.setHasChildren(dto.getChildrenNum() > 0);
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
    public List<MenuDto> treeListByPid(Long pid) {
        if (pid == null) {
            return Collections.emptyList();
        }

        List<MenuDto> list = pojoListByPid(pid);
        if (CollectionUtil.isNotEmpty(list)) {
            List<MenuDto> validList = list.stream()
                                          .filter(Objects::nonNull)
                                          .distinct()
                                          .collect(Collectors.toList());
            return buildTreeList(validList);
        }
        return Collections.emptyList();
    }

    private List<MenuDto> pojoListByPid(Long pid) {
        if (pid == null) {
            return Collections.emptyList();
        }

        List<MenuDto> list = new ArrayList<>();

        // 查找父节点
        MenuDto parentNode = pojoById(pid);
        if (parentNode == null) {
            return Collections.emptyList();
        }
        parentNode.setIsDefaultExpanded(true);
        list.add(parentNode);

        MenuQuery query = new MenuQuery();
        query.setPid(pid);
        List<MenuDto> children = menuDao.pojoListByQuery(query, this::doToDto);
        if (CollectionUtil.isNotEmpty(children)) {
            list.addAll(children);
        }

        if (pid != 0) {
            List<MenuDto> brothers = pojoListByPid(parentNode.getPid());
            if (CollectionUtil.isNotEmpty(brothers)) {
                list.addAll(brothers);
            }
        }
        return list;
    }

    @Override
    public List<MenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        // 查找所有角色绑定的菜单
        Set<Long> menuIds = permissionService.getMenuIdsByRoleIds(roleIds);
        if (CollectionUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }

        // 根据菜单 ID 查询菜单详细信息
        List<Menu> list = menuDao.listByIds(menuIds);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        // 过滤菜单项
        Set<MenuDto> set = list.stream()
                               .filter(i -> StrUtil.isNotBlank(i.getPath()))
                               .map(this::doToDto)
                               .collect(Collectors.toSet());
        return buildTreeList(set);
    }

    private List<MenuDto> buildTreeList(Collection<MenuDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setDeep(10);
        treeNodeConfig.setWeightKey("sequence");
        treeNodeConfig.setSort(Node.SORT.ASC);
        List<MenuDto> menuList = TreeUtil.build(list, 0L, treeNodeConfig, MenuDto.class);
        menuList.forEach(i -> {
            i.setHasChildren(i.getChildrenNum() > 0);
            i.setLeaf(!i.isHasChildren());
        });
        return menuList;
    }

    @Override
    public List<VueElementMenuVo> buildMineMenuList() {
        Long userId = securityService.getCurrentUserId();
        List<RoleDto> roles = roleService.pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }

        Set<Long> roleIds = roles.stream()
                                 .filter(Objects::nonNull)
                                 .map(RoleDto::getId)
                                 .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        List<MenuDto> treeList = pojoTreeListByRoleIds(roleIds);
        return buildFrontMenus(treeList);
    }

    private List<VueElementMenuVo> buildFrontMenus(Collection<MenuDto> list) {
        List<VueElementMenuVo> finalVoList = new LinkedList<>();
        for (MenuDto dto : list) {
            if (dto == null) {
                continue;
            }

            Collection<MenuDto> children = dto.getChildren();
            VueElementMenuVo menuVo = new VueElementMenuVo();
            menuVo.setName(ObjectUtil.isNotEmpty(dto.getComponent()) ? dto.getComponent() : dto.getName());
            // 一级目录需要加斜杠，不然会报警告
            menuVo.setPath(dto.getPid() == 0 ? "/" + dto.getPath() : dto.getPath());
            menuVo.setHidden(dto.getHidden());
            // 如果不是外链
            if (!dto.getFrame()) {
                if (dto.getPid() == 0) {
                    menuVo.setComponent(StrUtil.isEmpty(dto.getComponent()) ? "Layout" : dto.getComponent());
                } else if (!StrUtil.isEmpty(dto.getComponent())) {
                    menuVo.setComponent(dto.getComponent());
                }
            }
            menuVo.setMeta(new VueElementMenuVo.MenuMetaVo(dto.getName(), dto.getIcon(), !dto.getCached()));
            if (CollectionUtil.isNotEmpty(children)) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
                menuVo.setChildren(buildFrontMenus(children));
                // 处理是一级菜单并且没有子菜单的情况
            } else if (dto.getPid() == 0) {
                VueElementMenuVo menuVo1 = new VueElementMenuVo();
                menuVo1.setMeta(menuVo.getMeta());
                // 非外链
                if (!dto.getFrame()) {
                    menuVo1.setPath("index");
                    menuVo1.setName(menuVo.getName());
                    menuVo1.setComponent(menuVo.getComponent());
                } else {
                    menuVo1.setPath(dto.getPath());
                }
                menuVo.setName(null);
                menuVo.setMeta(null);
                menuVo.setComponent("Layout");
                List<VueElementMenuVo> list1 = new ArrayList<>();
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

}
