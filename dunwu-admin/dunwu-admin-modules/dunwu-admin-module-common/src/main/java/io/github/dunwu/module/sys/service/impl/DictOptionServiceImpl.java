package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictOptionQuery;
import io.github.dunwu.module.sys.service.DictOptionService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典详情 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Service
public class DictOptionServiceImpl extends ServiceImpl implements DictOptionService {

    private final DictOptionDao dao;

    public DictOptionServiceImpl(DictOptionDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(DictOption entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<DictOption> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(DictOption entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<DictOption> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(DictOption entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<DictOption> list) {
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
    public List<DictOptionDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<DictOptionDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DictOptionDto> pojoListByQuery(DictOptionQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DictOptionDto> pojoSpringPageByQuery(DictOptionQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public DictOptionDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public DictOptionDto pojoByQuery(DictOptionQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DictOptionQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DictOptionDto> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(Pageable pageable, DictOptionQuery query, HttpServletResponse response) {
        Page<DictOptionDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DictOptionDto 列表，导出 excel 表单
     *
     * @param list     {@link DictOptionDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DictOptionDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DictOptionDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("字典id", item.getDictId());
            map.put("字典选项编码", item.getCode());
            map.put("字典选项名称", item.getName());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public DictOptionDto doToDto(DictOption entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, DictOptionDto.class);
    }

    @Override
    public DictOption dtoToDo(DictOptionDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, DictOption.class);
    }

    @Override
    public List<DictOptionDto> pojoListByDictId(Long dictId) {
        DictOption dictOption = new DictOption();
        dictOption.setDictId(dictId);
        QueryWrapper<DictOption> wrapper = new QueryWrapper<>(dictOption);
        return dao.pojoList(wrapper, this::doToDto);
    }

}
