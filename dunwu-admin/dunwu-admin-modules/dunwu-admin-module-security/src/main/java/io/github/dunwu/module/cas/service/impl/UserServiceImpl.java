package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.DeptDao;
import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.dao.RoleDao;
import io.github.dunwu.module.cas.dao.UserDao;
import io.github.dunwu.module.cas.dao.UserRoleMapDao;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.dto.UserDto;
import io.github.dunwu.module.cas.entity.query.UserQuery;
import io.github.dunwu.module.cas.entity.vo.DeptJobUserMapVo;
import io.github.dunwu.module.cas.service.UserService;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.data.util.SwaggerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl implements UserService {

    public static final String INIT_PASSWORD = "123456";
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final DeptDao deptDao;
    private final JobDao jobDao;
    private final UserRoleMapDao userRoleDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean insert(User entity) {
        entity.setPassword(passwordEncoder.encode(INIT_PASSWORD));
        return userDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<User> list) {
        return userDao.insertBatch(list);
    }

    @Override
    public boolean updateById(User entity) {
        return userDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<User> list) {
        return userDao.updateBatchById(list);
    }

    @Override
    public boolean save(User entity) {
        return userDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<User> list) {
        return userDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        User user = userDao.getById(id);
        if (user == null) {
            return true;
        }

        userRoleDao.deleteByUserId(user.getId());
        return userDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        for (Serializable id : ids) {
            if (!deleteById(id)) {
                throw new DataException("数据库删除操作异常");
            }
        }
        return true;
    }

    @Override
    public List<UserDto> pojoList() {
        return userDao.pojoList(this::doToDto);
    }

    @Override
    public List<UserDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return userDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<UserDto> pojoListByQuery(UserQuery query) {
        return userDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<UserDto> pojoSpringPageByQuery(Pageable pageable, UserQuery query) {
        return userDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public UserDto pojoById(Serializable id) {
        return userDao.pojoById(id, this::doToDto);
    }

    @Override
    public UserDto pojoByQuery(UserQuery query) {
        return userDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(UserQuery query) {
        return userDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<UserDto> list = userDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, UserQuery query, HttpServletResponse response) {
        Page<UserDto> page = userDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 UserDto 列表，导出 excel 表单
     *
     * @param list     {@link UserDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<UserDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = SwaggerUtil.getFieldsMapList(list, UserDto.class);
        if (CollectionUtil.isNotEmpty(mapList)) {
            ExcelUtil.downloadExcel(response, mapList);
        }
    }

    @Override
    public UserDto doToDto(User entity) {
        UserDto dto = BeanUtil.toBean(entity, UserDto.class);
        dto.setUsername(entity.getUsername());

        // 查询用户关联部门信息
        DeptDto dept = deptDao.pojoById(entity.getDeptId(), DeptDto.class);
        if (dept != null) {
            dto.setDept(dept);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到部门信息", dto.getUsername());
            }
        }

        JobDto job = jobDao.pojoById(entity.getJobId(), JobDto.class);
        if (job != null) {
            dto.setJob(job);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到职位信息", dto.getUsername());
            }
        }

        Set<? extends Serializable> roleIds = userRoleDao.getRoleIdsByUserId(entity.getId());
        if (CollectionUtil.isNotEmpty(roleIds)) {
            List<RoleDto> roles = roleDao.pojoListByIds(roleIds, RoleDto.class);
            if (CollectionUtil.isNotEmpty(roles)) {
                dto.setRoles(roles);
                List<String> codes = roles.stream()
                                          .map(RoleDto::getCode).collect(Collectors.toList());
                dto.setRoleCodes(codes);
            } else {
                dto.setRoles(new ArrayList<>());
                dto.setRoleCodes(new ArrayList<>());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到角色信息", dto.getUsername());
            }
        }

        return dto;
    }

    @Override
    public User dtoToDo(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, User.class);
    }

    @Override
    public UserDto pojoByUsername(String username) {
        User userQuery = new User().setUsername(username);
        User user = userDao.getOne(userQuery);
        return doToDto(user);
    }

    @Override
    public List<UserDto> pojoListByDeptId(Serializable deptId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.DEPT_ID, deptId);
        return userDao.pojoList(wrapper, this::doToDto);
    }

    @Override
    public boolean bindDept(DeptJobUserMapVo vo) {
        List<User> users = userDao.listByIds(vo.getUserIds());
        if (CollectionUtil.isEmpty(users)) {
            return false;
        }

        for (User user : users) {
            user.setDeptId(vo.getDeptId());
            if (vo.getJobId() != null) {
                user.setJobId(vo.getJobId());
            }
        }
        return userDao.updateBatchById(users);
    }

    @Override
    public boolean unbindDept(DeptJobUserMapVo vo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(User.DEPT_ID, vo.getDeptId());
        wrapper.in(User.ID, vo.getUserIds());

        List<User> users = userDao.list(wrapper);
        if (CollectionUtil.isEmpty(users)) {
            return false;
        }

        for (User user : users) {
            user.setDeptId(null);
            user.setJobId(null);
        }
        return userDao.updateBatchById(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertWithRoles(UserDto dto) {
        User entity = dtoToDo(dto);
        if (userDao.insert(entity)) {
            if (CollectionUtil.isNotEmpty(dto.getRoles())) {
                List<Long> roleIds = dto.getRoles().stream().map(RoleDto::getId)
                                        .collect(Collectors.toList());
                userRoleDao.insertBatchByRoleIds(entity.getId(), roleIds);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWithRoles(UserDto dto) {
        User entity = dtoToDo(dto);
        if (userDao.updateById(entity)) {
            userRoleDao.deleteByUserId(entity.getId());
            if (CollectionUtil.isNotEmpty(dto.getRoles())) {
                Set<Long> roleIds = dto.getRoles().stream().map(RoleDto::getId)
                                       .collect(Collectors.toSet());
                userRoleDao.insertBatchByRoleIds(entity.getId(), roleIds);
            }
            return true;
        }
        return false;
    }

}
