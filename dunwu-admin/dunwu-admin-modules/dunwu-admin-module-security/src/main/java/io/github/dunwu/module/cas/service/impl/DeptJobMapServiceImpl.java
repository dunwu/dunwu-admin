package io.github.dunwu.module.cas.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.DeptJobMapDao;
import io.github.dunwu.module.cas.entity.DeptJobMap;
import io.github.dunwu.module.cas.entity.dto.DeptJobMapDto;
import io.github.dunwu.module.cas.entity.query.DeptJobMapQuery;
import io.github.dunwu.module.cas.service.DeptJobMapService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 部门职务关联表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Service
public class DeptJobMapServiceImpl extends ServiceImpl implements DeptJobMapService {

    private final DeptJobMapDao dao;

    public DeptJobMapServiceImpl(DeptJobMapDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(DeptJobMap entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<DeptJobMap> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(DeptJobMap entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<DeptJobMap> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(DeptJobMap entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<DeptJobMap> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<DeptJobMapDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<DeptJobMapDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DeptJobMapDto> pojoListByQuery(DeptJobMapQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DeptJobMapDto> pojoSpringPageByQuery(Pageable pageable, DeptJobMapQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public DeptJobMapDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public DeptJobMapDto pojoByQuery(DeptJobMapQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DeptJobMapQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DeptJobMapDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, DeptJobMapQuery query, HttpServletResponse response) {
        Page<DeptJobMapDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DeptJobMapDto 列表，导出 excel 表单
     *
     * @param list     {@link DeptJobMapDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DeptJobMapDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DeptJobMapDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("部门ID", item.getDeptId());
            map.put("职务ID", item.getJobId());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public DeptJobMapDto doToDto(DeptJobMap entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, DeptJobMapDto.class);
    }

    @Override
    public DeptJobMap dtoToDo(DeptJobMapDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, DeptJobMap.class);
    }

    @Override
    public boolean insertBatchByJobIds(Long deptId, Collection<Long> jobIds) {
        if (CollectionUtil.isEmpty(jobIds)) {
            return false;
        }

        Set<? extends Serializable> existedJobIds = getJobIdsByDeptId(deptId);
        jobIds.removeIf(existedJobIds::contains);
        if (CollectionUtil.isEmpty(jobIds)) {
            return true;
        }
        List<DeptJobMap> list = jobIds.stream().map(i -> new DeptJobMap(deptId, i))
                                      .collect(Collectors.toList());
        return dao.insertBatch(list);
    }

    @Override
    public boolean deleteBatchByJobIds(Long deptId, Collection<Long> jobIds) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId)
               .in(DeptJobMap.JOB_ID, jobIds);
        return dao.delete(wrapper);
    }

    @Override
    public boolean deleteByDeptId(Serializable deptId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId);
        return dao.delete(wrapper);
    }

    @Override
    public boolean deleteByJobId(Serializable jobId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.JOB_ID, jobId);
        return dao.delete(wrapper);
    }

    @Override
    public Set<? extends Serializable> getJobIdsByDeptId(Serializable deptId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.DEPT_ID, deptId);
        List<DeptJobMap> list = dao.list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptJobMap::getJobId).collect(Collectors.toSet());
    }

    @Override
    public Set<? extends Serializable> getDeptIdsByJobId(Serializable jobId) {
        QueryWrapper<DeptJobMap> wrapper = new QueryWrapper<>();
        wrapper.eq(DeptJobMap.JOB_ID, jobId);
        List<DeptJobMap> list = dao.list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptySet();
        }
        return list.stream().map(DeptJobMap::getDeptId).collect(Collectors.toSet());
    }

}
