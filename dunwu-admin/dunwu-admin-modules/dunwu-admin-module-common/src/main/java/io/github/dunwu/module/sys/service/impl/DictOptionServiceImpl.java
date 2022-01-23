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
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
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
 * 数据字典选项 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-22
 */
@Slf4j
@Service
public class DictOptionServiceImpl extends ServiceImpl implements DictOptionService {

    private final DictOptionDao dao;

    public DictOptionServiceImpl(DictOptionDao dao) {
        this.dao = dao;
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.ADD)
    public boolean insert(DictOption entity) {
        return dao.insert(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_ADD)
    public boolean insertBatch(Collection<DictOption> list) {
        return dao.insertBatch(list);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    public boolean updateById(DictOption entity) {
        return dao.updateById(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_EDIT)
    public boolean updateBatchById(Collection<DictOption> list) {
        return dao.updateBatchById(list);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    public boolean save(DictOption entity) {
        return dao.save(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_SAVE)
    public boolean saveBatch(Collection<DictOption> list) {
        return dao.saveBatch(list);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.DEL, bizNo = "{{#id}}")
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_DEL, bizNo = "{{#ids}}")
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
    public Page<DictOptionDto> pojoSpringPageByQuery(Pageable pageable, DictOptionQuery query) {
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
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EXPORT_LIST, bizNo = "{{#ids}}")
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DictOptionDto> list = dao.pojoListByIds(ids, this::doToDto);
        try {
            ServletUtil.downloadEasyExcel(response, list, DictOptionDto.class);
        } catch (IOException e) {
            log.error("【数据字典选项】【导出失败】", e);
        }
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EXPORT_PAGE,
        success = "分页查询导出数据字典选项(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toJsonStr()}})『成功』",
        fail = "分页查询导出数据字典选项(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toJsonStr()}})『失败』"
    )
    public void exportPage(Pageable pageable, DictOptionQuery query, HttpServletResponse response) {
        Page<DictOptionDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        try {
            ServletUtil.downloadEasyExcel(response, page.getContent(), DictOptionDto.class);
        } catch (IOException e) {
            log.error("【数据字典选项】【导出失败】", e);
        }
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
