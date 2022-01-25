package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.module.cas.dao.DeptJobMapDao;
import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.module.cas.service.JobService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.data.Pagination;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.exception.DataException;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 职务表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl extends ServiceImpl implements JobService {

    private final JobDao jobDao;
    private final DeptJobMapDao deptJobMapDao;
    private final RoleService roleService;

    @Override
    public boolean insert(Job entity) {
        return jobDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Job> list) {
        return jobDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Job entity) {
        return jobDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Job> list) {
        return jobDao.updateBatchById(list);
    }

    @Override
    public boolean save(Job entity) {
        return jobDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Job> list) {
        return jobDao.saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Serializable id) {
        // 删除部门职务关联记录
        boolean isOk = deptJobMapDao.deleteByJobId(id);
        if (!isOk) {
            String msg = StrUtil.format("删除 deptId = {} 的部门职务关联记录失败", id);
            throw new DataException(msg);
        }

        return jobDao.deleteById(id);
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
    public List<JobDto> pojoList() {
        return jobDao.pojoList(this::doToDto);
    }

    @Override
    public List<JobDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return jobDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<JobDto> pojoListByQuery(JobQuery query) {
        if (query.getDeptId() != null) {
            Set<? extends Serializable> jobIds = deptJobMapDao.getJobIdsByDeptId(query.getDeptId());
            if (CollectionUtil.isNotEmpty(jobIds)) {
                query.setIds(jobIds);
            } else {
                return Collections.emptyList();
            }
        }
        return jobDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<JobDto> pojoSpringPageByQuery(Pageable pageable, JobQuery query) {
        if (query.getDeptId() != null) {
            Set<? extends Serializable> jobIds = deptJobMapDao.getJobIdsByDeptId(query.getDeptId());
            if (CollectionUtil.isNotEmpty(jobIds)) {
                query.setIds(jobIds);
            } else {
                return new Pagination<>(Collections.emptyList(), pageable, 0L);
            }
        }
        return jobDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public JobDto pojoById(Serializable id) {
        return jobDao.pojoById(id, this::doToDto);
    }

    @Override
    public JobDto pojoByQuery(JobQuery query) {
        if (query.getDeptId() != null) {
            Set<? extends Serializable> jobIds = deptJobMapDao.getJobIdsByDeptId(query.getDeptId());
            if (CollectionUtil.isNotEmpty(jobIds)) {
                query.setIds(jobIds);
            } else {
                return null;
            }
        }
        return jobDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(JobQuery query) {
        if (query.getDeptId() != null) {
            Set<? extends Serializable> jobIds = deptJobMapDao.getJobIdsByDeptId(query.getDeptId());
            if (CollectionUtil.isNotEmpty(jobIds)) {
                query.setIds(jobIds);
            } else {
                return 0;
            }
        }
        return jobDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<JobDto> list = jobDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, JobQuery query, HttpServletResponse response) {
        if (query.getDeptId() != null) {
            Set<? extends Serializable> jobIds = deptJobMapDao.getJobIdsByDeptId(query.getDeptId());
            if (CollectionUtil.isNotEmpty(jobIds)) {
                query.setIds(jobIds);
            } else {
                return;
            }
        }
        Page<JobDto> page = jobDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 JobDto 列表，导出 excel 表单
     *
     * @param list     {@link JobDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<JobDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (JobDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("职务名称", item.getName());
            map.put("职务类型", item.getType());
            map.put("职级", item.getLevel());
            map.put("职务顺序", item.getSequence());
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
    public JobDto doToDto(Job entity) {
        if (entity == null) {
            return null;
        }

        JobDto jobDto = BeanUtil.toBean(entity, JobDto.class);

        // DeptDto deptDto = deptDao.pojoById(entity.getDeptId(), DeptDto.class);
        // jobDto.setDept(deptDto);
        List<RoleDto> roles = roleService.pojoListByJobId(entity.getId());
        jobDto.setRoles(roles);
        return jobDto;
    }

    @Override
    public Job dtoToDo(JobDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Job.class);
    }

    @Override
    public boolean bindDept(Long deptId, Collection<Long> jobIds) {
        return deptJobMapDao.insertBatchByJobIds(deptId, jobIds);
    }

    @Override
    public boolean unbindDept(Long deptId, Collection<Long> jobIds) {
        return deptJobMapDao.deleteBatchByJobIds(deptId, jobIds);
    }

}
