package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.cas.dao.*;
import io.github.dunwu.module.cas.entity.*;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl extends ServiceImpl implements RoleService {

    private final SecurityService securityService;

    private final RoleDao roleDao;
    private final DeptDao deptDao;
    private final MenuDao menuDao;
    private final UserRoleMapDao userRoleDao;
    private final JobRoleMapDao jobRoleDao;
    private final RoleMenuMapDao roleMenuDao;
    private final DeptRoleMapDao roleDeptDao;

    @Override
    public boolean save(Role entity) {
        checkRoleLevel(entity.getLevel());
        if (entity.getEnabled() == null) {
            entity.setEnabled(true);
        }
        return roleDao.insert(entity);
    }

    @Override
    public boolean updateById(Role entity) {
        checkRoleLevel(entity.getLevel());
        return roleDao.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        Role role = roleDao.getById(id);
        if (role == null) { return true; }
        checkRoleLevel(role.getLevel());
        return roleDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return true;
        }

        return roleDao.deleteBatchByIds(ids);
    }

    @Override
    public Page<RoleDto> pojoSpringPageByQuery(Object query, Pageable pageable) {
        return roleDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<RoleDto> pojoListByQuery(Object query) {
        return roleDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public RoleDto pojoById(Serializable id) {
        return roleDao.pojoById(id, this::doToDto);
    }

    @Override
    public RoleDto pojoByQuery(Object query) {
        return roleDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return roleDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) {
        List<RoleDto> list = roleDao.pojoListByIds(ids, RoleDto.class);
        export(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) {
        Page<RoleDto> page = roleDao.pojoSpringPageByQuery(query, pageable, RoleDto.class);
        export(page.getContent(), response);
    }

    /**
     * 根据传入的 SysRoleDto 列表，导出 excel 表单
     *
     * @param list     {@link RoleDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void export(Collection<RoleDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (RoleDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("角色ID", item.getId());
            map.put("角色名称", item.getName());
            map.put("数据范围", item.getDataScope());
            map.put("角色级别", item.getLevel());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public List<RoleDto> pojoListByUserId(Long userId) {
        List<RoleDto> sysRoleVos = new ArrayList<>();
        UserRoleMap userRole = new UserRoleMap();
        userRole.setUserId(userId);

        List<UserRoleMap> usersRoles = userRoleDao.list(userRole);
        if (CollectionUtil.isEmpty(usersRoles)) {
            return sysRoleVos;
        }

        Set<Long> roleIds = usersRoles.stream().map(UserRoleMap::getRoleId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return sysRoleVos;
        }

        return roleDao.pojoListByIds(roleIds, this::doToDto);
    }

    @Override
    public List<RoleDto> pojoListByJobId(Long jobId) {
        List<RoleDto> dtoList = new ArrayList<>();
        List<JobRoleMap> list = jobRoleDao.list(new JobRoleMap().setJobId(jobId));
        if (CollectionUtil.isEmpty(list)) {
            return dtoList;
        }
        Set<Long> roleIds = list.stream().map(JobRoleMap::getRoleId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return dtoList;
        }
        return roleDao.pojoListByIds(roleIds, RoleDto.class);
    }

    @Override
    public List<RoleDto> pojoListByMenuIds(Collection<Long> menuIds) {
        LambdaQueryWrapper<RoleMenuMap> wrapper =
            new QueryWrapper<RoleMenuMap>().lambda().in(RoleMenuMap::getMenuId, menuIds);
        List<RoleMenuMap> roleMenus = roleMenuDao.list(wrapper);
        List<Long> roleIds = roleMenus.stream()
                                      .filter(Objects::nonNull)
                                      .map(RoleMenuMap::getRoleId)
                                      .collect(Collectors.toList());
        return roleDao.pojoListByIds(roleIds, RoleDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenusByRoleId(Long roleId, List<MenuDto> menus) {

        Role entity = roleDao.getById(roleId);
        checkRoleLevel(entity.getLevel());

        List<RoleMenuMap> roleMenus = roleMenuDao.list(new RoleMenuMap().setRoleId(roleId));
        Set<Long> oldIds = roleMenus.stream().map(RoleMenuMap::getId).collect(Collectors.toSet());
        Set<Long> newIds = menus.stream().map(MenuDto::getId).collect(Collectors.toSet());
        boolean isEquals = Arrays.equals(oldIds.toArray(new Long[0]), newIds.toArray(new Long[0]));
        if (isEquals) {
            return true;
        }

        roleMenuDao.deleteBatchByIds(oldIds);
        Set<RoleMenuMap> roleMenuSet = newIds.stream().map(menuId -> new RoleMenuMap(roleId, menuId))
                                             .collect(Collectors.toSet());
        return roleMenuDao.insertBatch(roleMenuSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRolesByJobId(Long jobId, Collection<Long> roleIds) {
        Set<Long> oldRoleIds = new HashSet<>();
        List<JobRoleMap> oldJobRoles = jobRoleDao.list(new JobRoleMap().setJobId(jobId));
        if (CollectionUtil.isNotEmpty(oldJobRoles)) {
            oldRoleIds.addAll(oldJobRoles.stream().map(JobRoleMap::getId).collect(Collectors.toSet()));
        }

        boolean isEquals = Arrays.equals(oldRoleIds.toArray(new Long[0]), roleIds.toArray(new Long[0]));
        if (isEquals) {
            return true;
        }

        jobRoleDao.deleteBatchByIds(oldRoleIds);
        Set<JobRoleMap> jobRoleSet = roleIds.stream().map(roleId -> new JobRoleMap(jobId, roleId))
                                            .collect(Collectors.toSet());
        return jobRoleDao.insertBatch(jobRoleSet);
    }

    /**
     * 获取用户的角色级别
     */
    @Override
    public void checkRoleLevel(Integer level) throws AuthException {
        Integer roleLevel = getRoleLevel();
        if (level != null) {
            if (level < roleLevel) {
                throw new AuthException("权限不足，你的角色级别：" + roleLevel + "，低于操作的角色级别：" + level);
            }
        }
    }

    @Override
    public Integer getRoleLevel() {
        Long userId = securityService.getCurrentUserId();
        List<RoleDto> roles = pojoListByUserId(userId);
        if (CollectionUtil.isEmpty(roles)) {
            return null;
        }
        List<Integer> levels = roles.stream().map(RoleDto::getLevel).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(levels)) {
            return null;
        }
        return Collections.min(levels);
    }

    @Override
    @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        }

        List<RoleDto> roles = pojoListByUserId(user.getId());
        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                           .filter(menu -> StrUtil.isNotBlank(menu.getPermission()))
                           .map(MenuDto::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    private RoleDto doToDto(Role obj) {
        RoleDto role = BeanUtil.toBean(obj, RoleDto.class);
        fillDeptInfo(role);
        fillMenuInfo(role);
        return role;
    }

    private void fillDeptInfo(RoleDto role) {
        List<DeptDto> sysDeptVos = new ArrayList<>();
        QueryWrapper<DeptRoleMap> roleDeptQueryWrapper = Wrappers.query(new DeptRoleMap().setRoleId(role.getId()));
        List<DeptRoleMap> deptRoleMaps = roleDeptDao.list(roleDeptQueryWrapper);
        if (CollectionUtil.isNotEmpty(deptRoleMaps)) {
            Set<Long> deptIds = deptRoleMaps.stream().map(DeptRoleMap::getDeptId).collect(Collectors.toSet());
            List<Dept> depts = deptDao.listByIds(deptIds);
            if (CollectionUtil.isNotEmpty(depts)) {
                sysDeptVos.addAll(BeanUtil.toBeanList(depts, DeptDto.class));
            }
        }
        role.setDepts(sysDeptVos);
    }

    private void fillMenuInfo(RoleDto role) {
        List<MenuDto> menuDtos = new ArrayList<>();
        QueryWrapper<RoleMenuMap> roleMenuQueryWrapper = Wrappers.query(new RoleMenuMap().setRoleId(role.getId()));
        List<RoleMenuMap> sysUserRoles = roleMenuDao.list(roleMenuQueryWrapper);
        if (CollectionUtil.isNotEmpty(sysUserRoles)) {
            Set<Long> menuIds = sysUserRoles.stream().map(RoleMenuMap::getMenuId).collect(Collectors.toSet());
            List<Menu> menus = menuDao.listByIds(menuIds);
            if (CollectionUtil.isNotEmpty(menus)) {
                menuDtos.addAll(BeanUtil.toBeanList(menus, MenuDto.class));
            }
        }
        role.setMenus(menuDtos);
    }

}
