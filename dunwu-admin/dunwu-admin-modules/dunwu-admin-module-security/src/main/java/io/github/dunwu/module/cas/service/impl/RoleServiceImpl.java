package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.cas.dao.*;
import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.module.cas.entity.Role;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.MenuDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.RoleQuery;
import io.github.dunwu.module.cas.service.PermissionService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.core.exception.AuthException;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
 * 角色表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dunwu::role")
public class RoleServiceImpl extends ServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final DeptDao deptDao;
    private final MenuDao menuDao;
    private final PermissionDao permissionDao;
    private final JobRoleMapDao jobRoleDao;
    private final DeptRoleMapDao deptRoleDao;
    private final UserRoleMapDao userRoleDao;
    private final RolePermissionMapDao rolePermissionDao;
    private final PermissionService permissionService;
    private final SecurityService securityService;

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    public boolean insert(Role entity) {
        checkRoleLevel(entity.getLevel());
        return roleDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Role> list) {
        return roleDao.insertBatch(list);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    public boolean updateById(Role entity) {
        checkRoleLevel(entity.getLevel());
        return roleDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Role> list) {
        return roleDao.updateBatchById(list);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0.id")
    public boolean save(Role entity) {
        checkRoleLevel(entity.getLevel());
        return roleDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Role> list) {
        return roleDao.saveBatch(list);
    }

    @Override
    @CacheEvict(key = "'id:' + #p0")
    public boolean deleteById(Serializable id) {
        return roleDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return roleDao.deleteBatchByIds(ids);
    }

    @Override
    public List<RoleDto> pojoList() {
        return roleDao.pojoList(this::doToDto);
    }

    @Override
    public List<RoleDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return roleDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<RoleDto> pojoListByQuery(RoleQuery query) {
        if (query.getUserId() != null) {
            Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(query.getUserId());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                query.setIds(roleIds);
            } else {
                return Collections.emptyList();
            }
        }
        return roleDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<RoleDto> pojoSpringPageByQuery(Pageable pageable, RoleQuery query) {
        if (query.getUserId() != null) {
            Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(query.getUserId());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                query.setIds(roleIds);
            } else {
                return new PageImpl<>(Collections.emptyList(), pageable, 0L);
            }
        }
        return roleDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public RoleDto pojoById(Serializable id) {
        return roleDao.pojoById(id, this::doToDto);
    }

    @Override
    public RoleDto pojoByQuery(RoleQuery query) {
        if (query.getUserId() != null) {
            Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(query.getUserId());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                query.setIds(roleIds);
            } else {
                return null;
            }
        }
        return roleDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(RoleQuery query) {
        if (query.getUserId() != null) {
            Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(query.getUserId());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                query.setIds(roleIds);
            } else {
                return 0;
            }
        }
        return roleDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<RoleDto> list = roleDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, RoleQuery query, HttpServletResponse response) {
        if (query.getUserId() != null) {
            Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(query.getUserId());
            if (CollectionUtil.isNotEmpty(roleIds)) {
                query.setIds(roleIds);
            } else {
                return;
            }
        }
        Page<RoleDto> page = roleDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 RoleDto 列表，导出 excel 表单
     *
     * @param list     {@link RoleDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<RoleDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (RoleDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("角色编码", item.getCode());
            map.put("角色名称", item.getName());
            map.put("角色级别", item.getLevel());
            map.put("数据权限", item.getDataScope());
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
    public RoleDto doToDto(Role entity) {
        if (entity == null) {
            return null;
        }
        RoleDto dto = BeanUtil.toBean(entity, RoleDto.class);
        fillDeptInfo(dto);
        fillMenuInfo(dto);
        return dto;
    }

    @Override
    public Role dtoToDo(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Role.class);
    }

    @Override
    public List<RoleDto> pojoListByUserId(Long userId) {
        Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(userId);
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }

        return roleDao.pojoListByIds(roleIds, this::doToDto);
    }

    @Override
    public List<RoleDto> pojoListByJobId(Long jobId) {
        Set<? extends Serializable> roleIds = jobRoleDao.getRoleIdsByJobId(jobId);
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleDao.pojoListByIds(roleIds, this::doToDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenusByRoleId(Long roleId, List<MenuDto> menus) {

        Role entity = roleDao.getById(roleId);
        checkRoleLevel(entity.getLevel());
        rolePermissionDao.deleteByRoleId(roleId);

        if (CollectionUtil.isEmpty(menus)) {
            return true;
        }

        Set<Long> menuIds = menus.stream().map(MenuDto::getId).collect(Collectors.toSet());
        List<Long> permissionIds = permissionDao.getIdListByResourceIds(menuIds);
        if (CollectionUtil.isEmpty(permissionIds)) {
            return false;
        }
        return rolePermissionDao.insertBatchByPermissionIds(roleId, permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRolesByJobId(Long jobId, Collection<Long> roleIds) {
        jobRoleDao.deleteByJobId(jobId);
        return jobRoleDao.insertBatchByRoleIds(jobId, roleIds);
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

        List<RoleDto> roles = pojoListByUserId(user.getId());
        if (CollectionUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }

        // 如果是管理员直接返回
        List<String> roleNames = roles.stream().map(RoleDto::getCode)
                                      .collect(Collectors.toList());
        if (roleNames.contains("admin")) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                              .collect(Collectors.toList());
        }
        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                           .filter(menu -> StrUtil.isNotBlank(menu.getExpression()))
                           .map(MenuDto::getExpression).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());
    }

    private void fillDeptInfo(RoleDto role) {
        Set<? extends Serializable> deptIds = deptRoleDao.getDeptIdsByRoleId(role.getId());
        if (CollectionUtil.isEmpty(deptIds)) {
            role.setDepts(Collections.emptyList());
            return;
        }

        List<DeptDto> list = deptDao.pojoListByIds(deptIds, DeptDto.class);
        role.setDepts(list);
    }

    private void fillMenuInfo(RoleDto role) {
        Set<Long> menuIds = permissionService.getMenuIdsByRoleId(role.getId());
        if (CollectionUtil.isEmpty(menuIds)) {
            role.setMenus(Collections.emptyList());
            return;
        }

        List<Menu> list = menuDao.listByIds(menuIds);
        if (CollectionUtil.isNotEmpty(list)) {
            role.setMenus(BeanUtil.toBeanList(list, MenuDto.class));
        }
    }

}
