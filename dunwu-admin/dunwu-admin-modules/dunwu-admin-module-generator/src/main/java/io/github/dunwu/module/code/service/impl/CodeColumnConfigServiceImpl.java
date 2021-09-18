package io.github.dunwu.module.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.module.code.entity.CodeColumnConfig;
import io.github.dunwu.module.code.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.module.code.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.module.code.service.CodeColumnConfigService;
import io.github.dunwu.module.code.dao.CodeColumnConfigDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段级别配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Service
public class CodeColumnConfigServiceImpl extends ServiceImpl implements CodeColumnConfigService {

    private final CodeColumnConfigDao dao;

    public CodeColumnConfigServiceImpl(CodeColumnConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(CodeColumnConfig entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<CodeColumnConfig> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(CodeColumnConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeColumnConfig> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(CodeColumnConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeColumnConfig> list) {
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
    public List<CodeColumnConfigDto> pojoListByQuery(CodeColumnConfigQuery query) {
        List<CodeColumnConfigDto> dtos = dao.pojoListByQuery(query, this::doToDto);
        if (CollectionUtil.isEmpty(dtos)) {
            return Collections.emptyList();
        }

        return dtos.stream()
                   .sorted(Comparator.comparing(CodeColumnConfigDto::getId))
                   .collect(Collectors.toList());
    }

    @Override
    public Page<CodeColumnConfigDto> pojoPageByQuery(CodeColumnConfigQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoByQuery(CodeColumnConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(CodeColumnConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeColumnConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(CodeColumnConfigQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        Page<CodeColumnConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeColumnConfigDto doToDto(CodeColumnConfig model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeColumnConfigDto.class);
    }

    @Override
    public CodeColumnConfig dtoToDo(CodeColumnConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeColumnConfig.class);
    }

}
