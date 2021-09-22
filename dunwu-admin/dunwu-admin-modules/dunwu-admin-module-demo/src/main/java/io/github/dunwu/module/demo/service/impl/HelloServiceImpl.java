package io.github.dunwu.module.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.demo.dao.HelloDao;
import io.github.dunwu.module.demo.entity.Hello;
import io.github.dunwu.module.demo.entity.dto.HelloDto;
import io.github.dunwu.module.demo.entity.query.HelloQuery;
import io.github.dunwu.module.demo.service.HelloService;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-22
 */
@Service
public class HelloServiceImpl extends ServiceImpl implements HelloService {

    private final HelloDao dao;

    public HelloServiceImpl(HelloDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(Hello entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<Hello> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(Hello entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Hello> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(Hello entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Hello> list) {
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
    public List<HelloDto> pojoListByQuery(HelloQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<HelloDto> pojoPageByQuery(HelloQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public HelloDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public HelloDto pojoByQuery(HelloQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(HelloQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<HelloDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response) {
        Page<HelloDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public HelloDto doToDto(Hello model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, HelloDto.class);
    }

    @Override
    public Hello dtoToDo(HelloDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Hello.class);
    }

}
