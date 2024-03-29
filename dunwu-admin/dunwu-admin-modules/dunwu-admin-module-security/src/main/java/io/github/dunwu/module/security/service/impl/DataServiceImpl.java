package io.github.dunwu.module.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.module.security.constant.enums.DataScopeEnum;
import io.github.dunwu.module.security.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Zheng Jie
 * @website https://el-admin.vip
 * @description 数据权限服务实现
 * @date 2020-05-07
 **/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "data")
public class DataServiceImpl implements DataService {

    private final RoleService roleService;
    private final DeptService deptService;

    /**
     * 用户角色改变时需清理缓存
     *
     * @param user /
     * @return /
     */
    @Override
    @Cacheable(key = "'user:' + #user.id")
    public List<Long> getDeptIds(UserDto user) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 查询用户角色
        List<RoleDto> roles = roleService.pojoListByUserId(user.getId());
        // 获取对应的部门ID
        for (RoleDto role : roles) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(role.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(user.getDept().getId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, role));
                    break;
                default:
                    return new ArrayList<>(deptIds);
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获取自定义的数据权限
     *
     * @param deptIds 部门ID
     * @param role    角色
     * @return 数据权限ID
     */
    public Set<Long> getCustomize(Set<Long> deptIds, RoleDto role) {
        List<DeptDto> deptDtos = deptService.pojoListByRoleId(role.getId());
        for (DeptDto dept : deptDtos) {
            deptIds.add(dept.getId());
            Set<Long> childrenDeptIds = deptService.getOwnAndChildrenDeptIds(dept.getId());
            if (CollectionUtil.isNotEmpty(childrenDeptIds)) {
                deptIds.addAll(childrenDeptIds);
            }
        }
        return deptIds;
    }

}
