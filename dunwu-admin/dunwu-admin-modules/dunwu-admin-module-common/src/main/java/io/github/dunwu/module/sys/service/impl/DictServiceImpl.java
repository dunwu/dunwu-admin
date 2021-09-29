package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.module.sys.dao.DictDao;
import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.entity.Dict;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictDto;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.service.DictService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.ServletUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl implements DictService {

    private final DictDao dictDao;
    private final DictOptionDao dictOptionDao;

    @Override
    public boolean save(DictDto entity) {
        return dictDao.insert(dtoToDo(entity));
    }

    @Override
    public boolean updateById(DictDto entity) {
        return dictDao.updateById(dtoToDo(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
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
    public boolean removeByIds(Collection<Serializable> ids) {
        return dictDao.deleteBatchByIds(ids);
    }

    @Override
    public Page<DictDto> pojoSpringPageByQuery(Object query, Pageable pageable) {
        return dictDao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<DictDto> pojoListByQuery(Object query) {
        return dictDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public DictDto pojoById(Serializable id) {
        return dictDao.pojoById(id, this::doToDto);
    }

    @Override
    public DictDto pojoByQuery(Object query) {
        return dictDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dictDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) {
        List<DictDto> list = dictDao.pojoListByIds(ids, DictDto.class);
        export(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) {
        Page<DictDto> page = dictDao.pojoSpringPageByQuery(query, pageable, DictDto.class);
        export(page.getContent(), response);
    }

    /**
     * 根据传入的 SysDictDto 列表，导出 excel 表单
     *
     * @param list     {@link DictDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void export(Collection<DictDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (DictDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("字典编码", item.getCode());
            map.put("字典名称", item.getName());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    /**
     * 将数据实体转为 Dto
     *
     * @param entity {@link  Dict} 数据实体
     * @return {@link DictDto}
     */
    private DictDto doToDto(Dict entity) {
        DictDto dictDto = BeanUtil.toBean(entity, DictDto.class);
        List<DictOptionDto> options = pojoDictOptionsByDictId(dictDto.getId());
        if (CollectionUtil.isEmpty(options)) {
            dictDto.setOptions(new ArrayList<>());
        } else {
            dictDto.setOptions(options);
        }
        return dictDto;
    }

    /**
     * 将 Dto 转为数据实体
     *
     * @param dto Dto
     * @return {@link Dict}
     */
    private Dict dtoToDo(DictDto dto) {
        return BeanUtil.toBean(dto, Dict.class);
    }

    private List<DictOptionDto> pojoDictOptionsByDictId(Long dictId) {
        DictOption dictOption = new DictOption();
        dictOption.setDictId(dictId);
        List<DictOption> list = dictOptionDao.list(Wrappers.query(dictOption));
        return BeanUtil.toBeanList(list, DictOptionDto.class);
    }

}
