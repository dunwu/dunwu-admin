package io.github.dunwu.module.sys.service.impl;

import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.service.DictOptionService;
import io.github.dunwu.tool.bean.BeanUtil;
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
 * 系统数据字典项 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class DictOptionServiceImpl extends ServiceImpl implements DictOptionService {

    private final DictOptionDao dao;

    @Override
    public boolean save(DictOptionDto entity) {
        return dao.insert(dtoToDo(entity));
    }

    @Override
    public boolean updateById(DictOptionDto entity) {
        return dao.updateById(dtoToDo(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public Page<DictOptionDto> pojoSpringPageByQuery(Object query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(query, pageable, DictOptionDto.class);
    }

    @Override
    public List<DictOptionDto> pojoListByQuery(Object query) {
        return dao.pojoListByQuery(query, DictOptionDto.class);
    }

    @Override
    public DictOptionDto pojoById(Serializable id) {
        return dao.pojoById(id, DictOptionDto.class);
    }

    @Override
    public DictOptionDto pojoByQuery(Object query) {
        return dao.pojoByQuery(query, DictOptionDto.class);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) {
        List<DictOptionDto> list = dao.pojoListByIds(ids, DictOptionDto.class);
        export(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) {
        Page<DictOptionDto> page = dao.pojoSpringPageByQuery(query, pageable, DictOptionDto.class);
        export(page.getContent(), response);
    }

    /**
     * 根据传入的 SysDictOptionDto 列表，导出 excel 表单
     *
     * @param list     {@link DictOptionDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void export(Collection<DictOptionDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DictOptionDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("所属字典ID", item.getDictId());
            map.put("字典项编码", item.getCode());
            map.put("字典项名称", item.getName());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    /**
     * 将 Dto 转为数据实体
     *
     * @param dto Dto
     * @return {@link DictOption}
     */
    private DictOption dtoToDo(DictOptionDto dto) {
        return BeanUtil.toBean(dto, DictOption.class);
    }

    /**
     * 将数据实体转为 Dto
     *
     * @param entity {@link  DictOption} 数据实体
     * @return {@link DictOptionDto}
     */
    private DictOptionDto doToDto(DictOption entity) {
        return BeanUtil.toBean(entity, DictOptionDto.class);
    }

}
