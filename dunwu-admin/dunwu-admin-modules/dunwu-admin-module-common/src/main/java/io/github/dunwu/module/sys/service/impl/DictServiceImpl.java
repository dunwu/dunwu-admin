package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.module.sys.dao.DictDao;
import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.entity.Dict;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictDto;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictQuery;
import io.github.dunwu.module.sys.service.DictOptionService;
import io.github.dunwu.module.sys.service.DictService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl implements DictService {

    private final DictDao dictDao;
    private final DictOptionDao dictOptionDao;
    private final DictOptionService dictOptionService;

    @Override
    public boolean insert(Dict entity) {
        return dictDao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Dict> list) {
        return dictDao.insertBatch(list);
    }

    @Override
    public boolean updateById(Dict entity) {
        return dictDao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Dict> list) {
        return dictDao.updateBatchById(list);
    }

    @Override
    public boolean save(Dict entity) {
        return dictDao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Dict> list) {
        return dictDao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        Dict dict = dictDao.getById(id);
        if (dict == null) { return true; }

        // 删除字典记录
        dictDao.deleteById(id);

        // 查找并删除字典记录关联的字典项记录
        DictOption dictOption = new DictOption();
        dictOption.setDictId(dict.getId());
        List<DictOption> list = dictOptionDao.list(dictOption);
        if (CollectionUtil.isEmpty(list)) {
            return true;
        }
        Set<Long> sysDictOptionIds = list.stream().map(DictOption::getId).collect(Collectors.toSet());
        return dictOptionDao.deleteBatchByIds(sysDictOptionIds);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dictDao.deleteBatchByIds(ids);
    }

    @Override
    public List<DictDto> pojoList() {
        return dictDao.pojoList(this::doToDto);
    }

    @Override
    public List<DictDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dictDao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<DictDto> pojoListByQuery(DictQuery query) {
        return dictDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<DictDto> pojoSpringPageByQuery(DictQuery query, Pageable pageable) {
        return dictDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public DictDto pojoById(Serializable id) {
        return dictDao.pojoById(id, this::doToDto);
    }

    @Override
    public DictDto pojoByQuery(DictQuery query) {
        return dictDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(DictQuery query) {
        return dictDao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DictDto> list = dictDao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(DictQuery query, Pageable pageable, HttpServletResponse response) {
        Page<DictDto> page = dictDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 DictDto 列表，导出 excel 表单
     *
     * @param list     {@link DictDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<DictDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DictDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("字典编码", item.getCode());
            map.put("字典名称", item.getName());
            map.put("是否禁用：1 表示禁用；0 表示启用", item.getIsDisabled());
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
    public DictDto doToDto(Dict entity) {
        if (entity == null) {
            return null;
        }

        DictDto dictDto = BeanUtil.toBean(entity, DictDto.class);
        List<DictOptionDto> options = dictOptionService.pojoListByDictId(dictDto.getId());
        if (CollectionUtil.isEmpty(options)) {
            dictDto.setOptions(Collections.emptyList());
        } else {
            dictDto.setOptions(options);
        }
        return dictDto;
    }

    @Override
    public Dict dtoToDo(DictDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Dict.class);
    }

}
