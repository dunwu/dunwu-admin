package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.cas.dao.DeptDao;
import io.github.dunwu.module.cas.dao.JobDao;
import io.github.dunwu.module.cas.entity.Job;
import io.github.dunwu.module.cas.entity.dto.DeptDto;
import io.github.dunwu.module.cas.entity.dto.JobDto;
import io.github.dunwu.module.cas.entity.dto.RoleDto;
import io.github.dunwu.module.cas.entity.query.JobQuery;
import io.github.dunwu.module.cas.service.JobService;
import io.github.dunwu.module.cas.service.RoleService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 岗位 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl extends ServiceImpl implements JobService {

    private final JobDao jobDao;
    private final DeptDao deptDao;
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
    public boolean deleteById(Serializable id) {
        return jobDao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return jobDao.deleteBatchByIds(ids);
    }

    @Override
    public List<JobDto> pojoList() {
        return jobDao.pojoList(this::doToDto);
    }

    @Override
    public List<JobDto> pojoListByQuery(JobQuery query) {
        return jobDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<JobDto> pojoSpringPageByQuery(JobQuery query, Pageable pageable) {
        return jobDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public JobDto pojoById(Serializable id) {
        return jobDao.pojoById(id, this::doToDto);
    }

    @Override
    public JobDto pojoByQuery(JobQuery query) {
        return jobDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(JobQuery query) {
        return jobDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<JobDto> list = jobDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(JobQuery query, Pageable pageable, HttpServletResponse response) {
        Page<JobDto> page = jobDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
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
            map.put("岗位名称", item.getName());
            map.put("排序", item.getSequence());
            map.put("部门ID", item.getDeptId());
            map.put("岗位状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public JobDto doToDto(Job entity) {
        if (entity == null) {
            return null;
        }

        DeptDto deptDto = deptDao.pojoById(entity.getDeptId(), DeptDto.class);
        List<RoleDto> roles = roleService.pojoListByJobId(entity.getId());
        JobDto jobDto = BeanUtil.toBean(entity, JobDto.class);
        jobDto.setDept(deptDto);
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

}
