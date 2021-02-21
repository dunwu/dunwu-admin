package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.DbRuntimeException;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.system.dao.SysDeptDao;
import io.github.dunwu.modules.system.dao.SysJobDao;
import io.github.dunwu.modules.system.dao.SysRoleDeptDao;
import io.github.dunwu.modules.system.dao.SysUserDao;
import io.github.dunwu.modules.system.entity.SysDept;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.SysRoleDept;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysDeptRelationDto;
import io.github.dunwu.modules.system.entity.query.SysDeptQuery;
import io.github.dunwu.modules.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl implements SysDeptService {

    private final SysDeptDao deptDao;
    private final SysJobDao jobDao;
    private final SysUserDao userDao;
    private final SysRoleDeptDao roleDeptDao;

    @Override
    public boolean save(SysDept entity) {
        return deptDao.save(entity);
    }

    @Override
    public boolean updateById(SysDept entity) {
        if (entity.getPid() != null && entity.getId().equals(entity.getPid())) {
            throw new IllegalArgumentException("上级部门不能设为自身");
        }
        return deptDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        List<SysDeptDto> sysDeptDtos = deptDao.listByPid(id);
        Set<Serializable> ids = new HashSet<>();
        ids.add(id);
        if (CollUtil.isNotEmpty(sysDeptDtos)) {
            ids.addAll(sysDeptDtos.stream().map(SysDeptDto::getId).collect(Collectors.toSet()));
        }
        return deptDao.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollUtil.isEmpty(ids)) {
            return true;
        }

        for (Serializable id : ids) {
            if (!removeById(id)) {
                throw new DbRuntimeException("批量删除部门记录失败");
            }
        }
        return true;
    }

    @Override
    public Page<SysDeptDto> pojoPageByQuery(Object query, Pageable pageable) {
        return deptDao.pojoPageByQuery(query, pageable, this::doToVo);
    }

    @Override
    public List<SysDeptDto> pojoListByQuery(Object query) {
        return deptDao.pojoListByQuery(query, this::doToVo);
    }

    @Override
    public SysDeptDto pojoById(Serializable id) {
        SysDeptDto sysDeptDto = deptDao.pojoById(id, this::doToVo);
        if (sysDeptDto != null) {
            sysDeptDto.setLabel(sysDeptDto.getName());
        }
        return sysDeptDto;
    }

    @Override
    public SysDeptDto pojoByQuery(Object query) {
        return deptDao.pojoByQuery(query, this::doToVo);
    }

    @Override
    public Integer countByQuery(Object query) {
        return deptDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysDeptDto> list = deptDao.pojoListByIds(ids, this::doToVo);
        deptDao.exportDtoList(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysDeptDto> page = deptDao.pojoPageByQuery(query, pageable, this::doToVo);
        deptDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public List<SysDeptDto> pojoListByPid(Serializable pid) {
        return deptDao.listByPid(pid);
    }

    @Override
    public List<SysDeptDto> pojoListByRoleId(Long roleId) {
        List<SysDeptDto> deptList = new ArrayList<>();
        List<SysRoleDept> list = roleDeptDao.list(new SysRoleDept().setRoleId(roleId));
        if (CollUtil.isEmpty(list)) {
            return deptList;
        }

        Set<Long> ids = list.stream().map(SysRoleDept::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(ids)) {
            return deptList;
        }
        return deptDao.pojoListByIds(ids, this::doToVo);
    }

    @Override
    public Map<String, Object> treeListMap(Object query) {
        Collection<SysDeptDto> list = pojoListByQuery(query);
        return buildTreeList(list);
    }

    @Override
    public Map<String, Object> buildTreeList(Collection<SysDeptDto> list) {
        Collection<SysDeptDto> trees = deptDao.buildTreeList(list);
        Map<String, Object> map = new HashMap<>(2);
        map.put("content", trees);
        map.put("totalElements", list.size());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRelations(SysDeptRelationDto dto) {
        if (dto.getType().equalsIgnoreCase("jobs") && CollectionUtil.isNotEmpty(dto.getJobIds())) {
            List<SysJob> jobs = new ArrayList<>();
            dto.getJobIds().forEach(i -> {
                SysJob job = jobDao.getById(i);
                job.setDeptId(dto.getId());
                jobs.add(job);
            });
            return jobDao.updateBatchById(jobs);
        }

        if (dto.getType().equalsIgnoreCase("users") && CollectionUtil.isNotEmpty(dto.getUserIds())) {
            List<SysUser> users = new ArrayList<>();
            dto.getUserIds().forEach(i -> {
                SysUser user = userDao.getById(i);
                user.setDeptId(dto.getId());
                users.add(user);
            });
            return userDao.updateBatchById(users);
        }

        return true;
    }

    @Override
    public Set<Long> getChildrenDeptIds(Long id) {
        if (id == null) {
            return new HashSet<>();
        }

        SysDeptDto sysDeptDto = pojoById(id);
        if (sysDeptDto == null) {
            return new HashSet<>();
        }

        SysDeptQuery query = new SysDeptQuery();
        query.setPid(id);
        List<SysDeptDto> children = pojoListByQuery(query);
        if (CollectionUtil.isEmpty(children)) {
            return new HashSet<>();
        }
        return children.stream()
                       .filter(Objects::nonNull)
                       .map(SysDeptDto::getId)
                       .collect(Collectors.toSet());
    }

    public SysDeptDto doToVo(SysDept model) {
        if (model == null) {
            return null;
        }

        SysDeptDto dto = BeanUtil.toBean(model, SysDeptDto.class);
        dto.setLabel(dto.getName());
        return dto;
    }

}
