package io.github.dunwu.module.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.code.dao.CodeTableConfigDao;
import io.github.dunwu.module.code.entity.CodeTableConfig;
import io.github.dunwu.module.code.entity.dto.CodeTableConfigDto;
import io.github.dunwu.module.code.entity.query.CodeTableConfigQuery;
import io.github.dunwu.module.code.service.CodeTableConfigService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@Service
public class CodeTableConfigServiceImpl extends ServiceImpl implements CodeTableConfigService {

    private final CodeTableConfigDao dao;

    public CodeTableConfigServiceImpl(CodeTableConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(CodeTableConfig entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<CodeTableConfig> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(CodeTableConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeTableConfig> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(CodeTableConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeTableConfig> list) {
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
    public List<CodeTableConfigDto> pojoListByQuery(CodeTableConfigQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<CodeTableConfigDto> pojoSpringPageByQuery(CodeTableConfigQuery query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public CodeTableConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeTableConfigDto pojoByQuery(CodeTableConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(CodeTableConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeTableConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(CodeTableConfigQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        Page<CodeTableConfigDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeTableConfigDto doToDto(CodeTableConfig model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeTableConfigDto.class);
    }

    @Override
    public CodeTableConfig dtoToDo(CodeTableConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeTableConfig.class);
    }

}
