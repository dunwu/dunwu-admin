package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.cas.dao.DeptDao;
import io.github.dunwu.module.cas.dao.DeptJobMapDao;
import io.github.dunwu.module.cas.dao.DeptRoleMapDao;
import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.dao.UserDao;
import io.github.dunwu.module.cas.entity.Dept;
import io.github.dunwu.module.cas.entity.DeptRoleMap;
import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.DeptRelationDto;
import io.github.dunwu.module.cas.entity.query.DeptQuery;
import io.github.dunwu.module.cas.service.DeptService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.util.SerializableUtil;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl implements DeptService {

    private final DeptDao deptDao;
    private final JobDao jobDao;
    private final UserDao userDao;
    private final DeptRoleMapDao roleDeptDao;
    private final DeptJobMapDao deptJobDao;

    @Override
    public boolean insert(Dept entity) {
        autoRefreshLevelAndChildrenNum(entity);
        return deptDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Dept> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        for (Dept entity : list) {
            insert(entity);
        }
        return true;
    }

    @Override
    public boolean updateById(Dept entity) {
        autoRefreshLevelAndChildrenNum(entity);
        return deptDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Dept> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        for (Dept entity : list) {
            updateById(entity);
        }
        return true;
    }

    @Override
    public boolean save(Dept entity) {
        autoRefreshLevelAndChildrenNum(entity);
        return deptDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Dept> list) {
        if (CollectionUtil.isEmpty(list)) {
            return false;
        }
        for (Dept entity : list) {
            save(entity);
        }
        return true;
    }

    private void autoRefreshLevelAndChildrenNum(Dept entity) {

        if (entity.getId() != null) {
            Dept oldRecord = deptDao.getById(entity.getId());
            if (!oldRecord.getPid().equals(entity.getPid())) {
                if (oldRecord.getPid() != 0) {
                    Dept oldParentRecord = deptDao.getById(oldRecord.getPid());
                    int num = oldParentRecord.getChildrenNum() - 1;
                    int max = Math.max(num, 0);
                    oldParentRecord.setChildrenNum(max);
                    deptDao.updateById(oldParentRecord);
                }
            }
        }

        if (entity.getPid() != 0) {
            Dept newParentRecord = deptDao.getById(entity.getPid());
            if (newParentRecord == null) {
                String msg = StrUtil.format("上级部门 id = {} 不存在", entity.getPid());
                throw new DataException(msg);
            }

            newParentRecord.setChildrenNum(newParentRecord.getChildrenNum() + 1);
            deptDao.updateById(newParentRecord);

            entity.setLevel(newParentRecord.getLevel() + 1);
        } else {
            entity.setLevel(1);
        }

        check(entity);
    }

    private void check(Dept entity) {
        if (entity.getId() != null && entity.getPid() != null && entity.getId().equals(entity.getPid())) {
            throw new IllegalArgumentException("上级部门不能设为自身");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        // 删除部门职务关联记录
        deptJobDao.deleteByDeptId(id);

        Dept entity = deptDao.getById(id);
        if (entity == null) {
            return false;
        }

        if (entity.getPid() != 0) {
            Dept superiorDept = deptDao.getById(entity.getPid());
            if (superiorDept == null) {
                String msg = StrUtil.format("上级部门 id = {} 不存在", entity.getPid());
                throw new DataException(msg);
            }

            superiorDept.setChildrenNum(superiorDept.getChildrenNum() - 1);
            deptDao.updateById(superiorDept);
        }

        Set<Long> ids = new HashSet<>();
        ids.add(entity.getId());
        Set<Long> childrenIds = getChildrenDeptIds(SerializableUtil.getLong(id));
        if (CollectionUtil.isNotEmpty(childrenIds)) {
            ids.addAll(childrenIds);
        }
        return deptDao.deleteBatchByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return false;
        }
        for (Serializable id : ids) {
            deleteById(id);
        }
        return true;
    }

    @Override
    public List<DeptDto> pojoList() {
        return deptDao.pojoList(this::doToDto);
    }

    @Override
    public List<DeptDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return deptDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DeptDto> pojoListByQuery(DeptQuery query) {
        return deptDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DeptDto> pojoSpringPageByQuery(Pageable pageable, DeptQuery query) {
        return deptDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public DeptDto pojoById(Serializable id) {
        return deptDao.pojoById(id, this::doToDto);
    }

    @Override
    public DeptDto pojoByQuery(DeptQuery query) {
        return deptDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DeptQuery query) {
        return deptDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DeptDto> list = deptDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, DeptQuery query, HttpServletResponse response) {
        Page<DeptDto> page = deptDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DeptDto 列表，导出 excel 表单
     *
     * @param list     {@link DeptDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DeptDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeptDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级部门ID", item.getPid());
            map.put("部门名称", item.getName());
            map.put("部门等级", item.getLevel());
            map.put("部门顺序", item.getSequence());
            map.put("子部门数量", item.getChildrenNum());
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
    public DeptDto doToDto(Dept entity) {
        if (entity == null) {
            return null;
        }

        DeptDto dto = BeanUtil.toBean(entity, DeptDto.class);
        dto.setLabel(entity.getName());
        dto.setHasChildren(entity.getChildrenNum() > 0);
        return dto;
    }

    @Override
    public Dept dtoToDo(DeptDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Dept.class);
    }

    @Override
    public List<DeptDto> pojoListByPid(Serializable pid) {
        DeptQuery query = new DeptQuery();
        query.setPid(SerializableUtil.getLong(pid));
        return pojoListByQuery(query);
    }

    @Override
    public List<DeptDto> pojoListByRoleId(Long roleId) {
        List<DeptDto> deptList = new ArrayList<>();
        List<DeptRoleMap> list = roleDeptDao.list(new DeptRoleMap().setRoleId(roleId));
        if (CollUtil.isEmpty(list)) {
            return deptList;
        }

        Set<Long> ids = list.stream().map(DeptRoleMap::getId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(ids)) {
            return deptList;
        }
        return deptDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DeptDto> pojoIncompleteTreeListByQuery(DeptQuery query) {
        Collection<DeptDto> list = pojoListByQuery(query);
        return buildTreeList(list);
    }

    @Override
    public List<DeptDto> pojoTreeListById(Serializable id) {
        List<DeptDto> list = getOwnAndChildrenDeptList(id);
        return buildTreeList(list);
    }

    @Override
    public List<DeptDto> pojoTreeListByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<DeptDto> totalList = new ArrayList<>();
        for (Serializable id : ids) {
            List<DeptDto> list = pojoTreeListById(id);
            if (CollectionUtil.isNotEmpty(list)) {
                totalList.addAll(list);
            }
        }
        return totalList;
    }

    @Override
    public List<DeptDto> pojoSuperiorListById(Serializable id) {
        return getSuperiorDeptList(id);
    }

    @Override
    public List<DeptDto> pojoSuperiorListByIds(Collection<? extends Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }

        List<DeptDto> totalList = new ArrayList<>();
        for (Serializable id : ids) {
            List<DeptDto> list = pojoSuperiorListById(id);
            if (CollectionUtil.isNotEmpty(list)) {
                totalList.addAll(list);
            }
        }
        return totalList;
    }

    private List<DeptDto> getSuperiorDeptList(Serializable id) {
        List<DeptDto> total = new ArrayList<>();
        DeptDto current = pojoById(id);
        if (current != null) {
            total.add(current);
            if (current.getPid() != 0) {
                List<DeptDto> list = getSuperiorDeptList(current.getPid());
                if (CollectionUtil.isNotEmpty(list)) {
                    total.addAll(list);
                }
            }
        }
        return total;
    }

    @Override
    public Set<Long> getOwnAndChildrenDeptIds(Serializable id) {
        Set<Long> set = getChildrenDeptIds(id);
        set.add(SerializableUtil.getLong(id));
        return set;
    }

    public Set<Long> getChildrenDeptIds(Serializable id) {
        List<DeptDto> list = getChildrenDeptList(id);
        if (CollectionUtil.isNotEmpty(list)) {
            return list.stream()
                       .filter(Objects::nonNull)
                       .map(DeptDto::getId)
                       .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    /**
     * 根据ID获取当前部门及所有子部门列表
     */
    private List<DeptDto> getOwnAndChildrenDeptList(Serializable id) {
        List<DeptDto> list = new ArrayList<>();
        DeptDto root = pojoById(id);
        if (root == null) {
            return list;
        }
        list.add(root);

        List<DeptDto> children = getChildrenDeptList(id);
        if (CollectionUtil.isNotEmpty(children)) {
            list.addAll(children);
        }
        return list;
    }

    /**
     * 根据ID获取当前部门所有子部门列表
     */
    private List<DeptDto> getChildrenDeptList(Serializable id) {
        List<DeptDto> list = new ArrayList<>();
        List<DeptDto> children = pojoListByPid(id);
        if (CollectionUtil.isNotEmpty(children)) {
            list.addAll(children);
            for (DeptDto child : children) {
                List<DeptDto> subChildren = getChildrenDeptList(child.getId());
                if (CollectionUtil.isNotEmpty(subChildren)) {
                    list.addAll(subChildren);
                }
            }
        }
        return list;
    }

    /**
     * 根据 list 构建树形结构的列表
     */
    private List<DeptDto> buildTreeList(Collection<DeptDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("id");
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setSort(Node.SORT.ASC);
        treeNodeConfig.setDeep(10);
        list = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return TreeUtil.build(list, 0L, treeNodeConfig, DeptDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRelations(DeptRelationDto dto) {
        if (dto.getType().equalsIgnoreCase("jobs") && CollectionUtil.isNotEmpty(dto.getJobIds())) {
            List<Job> jobs = new ArrayList<>();
            dto.getJobIds().forEach(i -> {
                Job job = jobDao.getById(i);
                // job.setDeptId(dto.getId());
                jobs.add(job);
            });
            return jobDao.updateBatchById(jobs);
        }

        if (dto.getType().equalsIgnoreCase("users") && CollectionUtil.isNotEmpty(dto.getUserIds())) {
            List<User> users = new ArrayList<>();
            dto.getUserIds().forEach(i -> {
                User user = userDao.getById(i);
                user.setDeptId(dto.getId());
                users.add(user);
            });
            return userDao.updateBatchById(users);
        }

        return true;
    }

}
