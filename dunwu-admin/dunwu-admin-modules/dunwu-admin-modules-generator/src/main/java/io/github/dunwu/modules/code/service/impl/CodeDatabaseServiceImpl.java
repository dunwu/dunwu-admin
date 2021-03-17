package io.github.dunwu.modules.code.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.code.dao.CodeDatabaseDao;
import io.github.dunwu.modules.code.entity.CodeDatabase;
import io.github.dunwu.modules.code.entity.dto.CodeDatabaseDto;
import io.github.dunwu.modules.code.entity.query.CodeDatabaseQuery;
import io.github.dunwu.modules.code.service.CodeDatabaseService;
import io.github.dunwu.modules.code.util.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据库管理 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
@Slf4j
@Service
public class CodeDatabaseServiceImpl extends ServiceImpl implements CodeDatabaseService {

    private final CodeDatabaseDao dao;

    public CodeDatabaseServiceImpl(CodeDatabaseDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(CodeDatabase entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<CodeDatabase> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(CodeDatabase entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeDatabase> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(CodeDatabase entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeDatabase> list) {
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
    public List<CodeDatabaseDto> pojoListByQuery(CodeDatabaseQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<CodeDatabaseDto> pojoPageByQuery(CodeDatabaseQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public CodeDatabaseDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeDatabaseDto pojoByQuery(CodeDatabaseQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(CodeDatabaseQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeDatabaseDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(CodeDatabaseQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        Page<CodeDatabaseDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeDatabaseDto doToDto(CodeDatabase model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeDatabaseDto.class);
    }

    @Override
    public CodeDatabase dtoToDo(CodeDatabaseDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeDatabase.class);
    }

    @Override
    public boolean testConnection(CodeDatabaseDto dto) {
        try {
            return SqlUtils.testConnection(dto.getJdbcUrl(), dto.getUsername(), dto.getPassword());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
