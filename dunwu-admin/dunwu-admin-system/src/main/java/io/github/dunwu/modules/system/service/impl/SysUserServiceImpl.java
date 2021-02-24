package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.config.RsaProperties;
import io.github.dunwu.data.core.DataException;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.exception.BadRequestException;
import io.github.dunwu.exception.EntityExistException;
import io.github.dunwu.modules.security.service.UserCacheClean;
import io.github.dunwu.modules.system.dao.*;
import io.github.dunwu.modules.system.entity.SysRoleMenu;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.SysUserRole;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysJobDto;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.entity.vo.UserPassVo;
import io.github.dunwu.modules.system.service.SysUserService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.util.CacheKey;
import io.github.dunwu.util.RedisUtils;
import io.github.dunwu.util.RsaUtils;
import io.github.dunwu.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl implements SysUserService {

    public static final String INIT_PASSWORD = "123456";
    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    private final SysUserDao userDao;
    private final SysRoleDao roleDao;
    private final SysDeptDao deptDao;
    private final SysJobDao jobDao;
    private final SysUserRoleDao userRoleDao;
    private final SysRoleMenuDao roleMenuDao;
    private final PasswordEncoder passwordEncoder;

    private final RedisUtils redisUtils;
    private final UserCacheClean userCacheClean;

    @Override
    public boolean save(SysUser entity) {
        return userDao.save(entity);
    }

    @Override
    public boolean updateById(SysUser entity) {
        return userDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        SysUser user = userDao.getById(id);
        if (user == null) {
            return true;
        }

        SysUserRole sysUserRole = new SysUserRole().setUserId(user.getId());
        if (userRoleDao.remove(Wrappers.query(sysUserRole))) {
            return userDao.removeById(user.getId());
        }
        log.error("试图删除用户 id = {} 及其关联数据失败", id);
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        for (Serializable id : ids) {
            if (!removeById(id)) {
                throw new DataException("数据库删除操作异常");
            }
        }
        return true;
    }

    @Override
    public Page<SysUserDto> pojoPageByQuery(Object query, Pageable pageable) {
        return userDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<SysUserDto> pojoListByQuery(Object query) {
        return userDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public SysUserDto pojoById(Serializable id) {
        return userDao.pojoById(id, this::doToDto);
    }

    @Override
    public SysUserDto pojoByQuery(Object query) {
        return userDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return userDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysUserDto> list = userDao.pojoListByIds(ids, this::doToDto);
        userDao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysUserDto> page = userDao.pojoPageByQuery(query, pageable, this::doToDto);
        userDao.exportDtoList(page.getContent(), response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveUserRelatedRecords(SysUserDto dto) {
        SysUser user = dtoToDo(dto);
        user.setPassword(passwordEncoder.encode(INIT_PASSWORD));
        if (userDao.save(user)) {
            List<SysUserRole> newRecords = new ArrayList<>();
            dto.getRoles().forEach(i -> {
                newRecords.add(new SysUserRole(user.getId(), i.getId()));
            });
            userRoleDao.saveBatch(newRecords);
            return user.getId();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserRelatedRecords(SysUserDto dto) {
        SysUser entity = dtoToDo(dto);
        if (userDao.updateById(entity)) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(entity.getId());
            List<SysUserRole> sysUserRoles = userRoleDao.list(sysUserRole);
            Set<Long> oldIds = sysUserRoles.stream().map(SysUserRole::getId).collect(Collectors.toSet());
            List<SysUserRole> newRecords = new ArrayList<>();
            dto.getRoles().forEach(i -> {
                newRecords.add(new SysUserRole(dto.getId(), i.getId()));
            });
            userRoleDao.removeByIds(oldIds);
            userRoleDao.saveBatch(newRecords);
            return true;
        }
        return false;
    }

    @Override
    public SysUserDto pojoByUsername(String username) {
        SysUser userQuery = new SysUser().setUsername(username);
        SysUser user = userDao.getOne(userQuery);
        return doToDto(user);
    }

    public SysUserDto doToDto(SysUser entity) {
        SysUserDto dto = BeanUtil.toBean(entity, SysUserDto.class);
        dto.setName(entity.getUsername());

        SysJobDto job = jobDao.pojoById(entity.getJobId(), SysJobDto.class);
        if (job != null) {
            dto.setJob(job);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到职位信息", dto.getUsername());
            }
        }

        SysDeptDto dept = deptDao.pojoById(entity.getDeptId(), SysDeptDto.class);
        if (dept != null) {
            dto.setDept(dept);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到部门信息", dto.getUsername());
            }
        }

        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(entity.getId());
        List<SysUserRole> sysUserRoles = userRoleDao.list(Wrappers.query(sysUserRole));
        if (CollectionUtil.isNotEmpty(sysUserRoles)) {
            Set<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
            List<SysRoleDto> sysRoleVos = roleDao.pojoListByIds(roleIds, SysRoleDto.class);
            if (CollectionUtil.isNotEmpty(sysRoleVos)) {
                dto.setRoles(sysRoleVos);
            } else {
                dto.setRoles(new ArrayList<>());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("用户 {} 未查询到角色信息", dto.getUsername());
            }
        }

        return dto;
    }

    public SysUser dtoToDo(SysUserDto dto) {
        return BeanUtil.toBean(dto, SysUser.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(SysUserDto entity) {
        SysUser user = userDao.getById(entity.getId());
        SysUser query = new SysUser();
        query.setPhone(entity.getPhone());
        SysUser user1 = userDao.getOne(query);
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(SysUser.class, "phone", entity.getPhone());
        }
        user.setNickname(entity.getNickname());
        user.setPhone(entity.getPhone());
        user.setGender(entity.getGender());
        userDao.updateById(user);
        // 清理缓存
        delCaches(user.getId(), user.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        SysUserDto sysUserDto = pojoByUsername(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, sysUserDto.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, sysUserDto.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        SysUser user = new SysUser();
        user.setId(sysUserDto.getId());
        user.setPassword(passwordEncoder.encode(newPass));
        updateById(user);
        flushCache(sysUserDto.getUsername());
    }

    @Override
    public List<SysUser> findByMenuId(Long menuId) {
        SysRoleMenu query1 = new SysRoleMenu();
        List<SysRoleMenu> roleMenus = roleMenuDao.list(query1);
        if (CollectionUtil.isEmpty(roleMenus)) {
            return null;
        }
        return null;
    }

    @Override
    public List<SysUser> findByRoleId(Long roleId) {
        return null;
    }

    @Override
    public int countByRoles(Set<Long> roleIds) {
        return 0;
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisUtils.del(CacheKey.USER_ID + id);
        flushCache(username);
    }

    /**
     * 清理 登陆时 用户缓存信息
     *
     * @param username /
     */
    private void flushCache(String username) {
        userCacheClean.cleanUserCache(username);
    }

}
