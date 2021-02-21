package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.system.dao.SysDictDao;
import io.github.dunwu.modules.system.dao.SysDictOptionDao;
import io.github.dunwu.modules.system.entity.SysDict;
import io.github.dunwu.modules.system.entity.SysDictOption;
import io.github.dunwu.modules.system.entity.dto.SysDictDto;
import io.github.dunwu.modules.system.entity.dto.SysDictOptionDto;
import io.github.dunwu.modules.system.service.SysDictService;
import io.github.dunwu.tool.bean.BeanUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * 系统数据字典 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
public class SysDictServiceImpl extends ServiceImpl implements SysDictService {

    private final SysDictDao sysDictDao;
    private final SysDictOptionDao sysDictOptionDao;

    public SysDictServiceImpl(SysDictDao sysDictDao, SysDictOptionDao sysDictOptionDao) {
        this.sysDictDao = sysDictDao;
        this.sysDictOptionDao = sysDictOptionDao;
    }

    @Override
    public boolean save(SysDict entity) {
        return sysDictDao.save(entity);
    }

    @Override
    public boolean updateById(SysDict entity) {
        return sysDictDao.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        SysDict sysDict = sysDictDao.getById(id);
        if (sysDict == null) { return true; }

        // 删除字典记录
        sysDictDao.removeById(id);

        // 查找并删除字典记录关联的字典项记录
        SysDictOption sysDictOption = new SysDictOption();
        sysDictOption.setDictId(sysDict.getId());
        List<SysDictOption> list = sysDictOptionDao.list(sysDictOption);
        if (CollectionUtil.isEmpty(list)) {
            return true;
        }
        Set<Long> sysDictOptionIds = list.stream().map(SysDictOption::getId).collect(Collectors.toSet());
        return sysDictOptionDao.removeByIds(sysDictOptionIds);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return sysDictDao.removeByIds(ids);
    }

    @Override
    public Page<SysDictDto> pojoPageByQuery(Object query, Pageable pageable) {
        return sysDictDao.pojoPageByQuery(query, pageable, SysDictDto.class);
    }

    @Override
    public List<SysDictDto> pojoListByQuery(Object query) {
        return sysDictDao.pojoListByQuery(query, this::toDto);
    }

    @Override
    public SysDictDto pojoById(Serializable id) {
        return sysDictDao.pojoById(id, this::toDto);
    }

    @Override
    public SysDictDto pojoByQuery(Object query) {
        return sysDictDao.pojoByQuery(query, this::toDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return sysDictDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysDictDto> list = sysDictDao.pojoListByIds(ids, SysDictDto.class);
        sysDictDao.exportDtoList(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysDictDto> page = sysDictDao.pojoPageByQuery(query, pageable, SysDictDto.class);
        sysDictDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public SysDictDto toDto(SysDict sysDict) {
        SysDictDto sysDictDto = BeanUtil.toBean(sysDict, SysDictDto.class);
        List<SysDictOptionDto> options = sysDictOptionDao.pojoDictOptionsByDictId(sysDictDto.getId());
        if (CollectionUtil.isEmpty(options)) {
            sysDictDto.setOptions(new ArrayList<>());
        } else {
            sysDictDto.setOptions(options);
        }
        return sysDictDto;
    }

}
