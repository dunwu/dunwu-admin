package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.module.sys.entity.dto.DictOptionDto;
import io.github.dunwu.module.sys.entity.query.DictOptionQuery;
import io.github.dunwu.module.sys.service.DictOptionService;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.DefaultException;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典选项 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-01-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictOptionServiceImpl extends ServiceImpl implements DictOptionService {

    private final DictOptionDao dao;

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.ADD)
    public boolean insert(DictOption entity) {
        checkEntity(entity);
        return dao.insert(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_ADD)
    public boolean insertBatch(Collection<DictOption> list) {
        checkEntityList(list);
        return dao.insertBatch(list);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    public boolean updateById(DictOption entity) {
        checkEntity(entity);
        return dao.updateById(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_EDIT)
    public boolean updateBatchById(Collection<DictOption> list) {
        checkEntityList(list);
        return dao.updateBatchById(list);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    public boolean save(DictOption entity) {
        checkEntity(entity);
        return dao.save(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.BATCH_SAVE)
    public boolean saveBatch(Collection<DictOption> list) {
        checkEntityList(list);
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
    @Transactional(rollbackFor = { Exception.class })
    @OperationLog(bizType = "数据字典选项", operation = OperationType.IMPORT_EXCEL,
        success = "导入数据字典选项(Excel文件：{{#file.getOriginalFilename()}})『成功』",
        fail = "导入数据字典选项(Excel文件：{{#file.getOriginalFilename()}})『失败』"
    )
    public void importList(MultipartFile file) {
        try {
            ExcelUtil.saveExcelData(file.getInputStream(), DictOption.class, dao);
        } catch (IOException e) {
            log.error("【数据字典选项】【导入失败】", e);
        }
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EXPORT_EXCEL, bizNo = "{{#ids}}")
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DictOptionDto> list = dao.pojoListByIds(ids, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, list, DictOptionDto.class);
        } catch (IOException e) {
            log.error("【数据字典选项】【导出失败】", e);
        }
    }

    @Override
    @OperationLog(bizType = "数据字典选项", operation = OperationType.EXPORT_EXCEL,
        success = "分页查询导出数据字典选项(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toJsonStr()}})『成功』",
        fail = "分页查询导出数据字典选项(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toJsonStr()}})『失败』"
    )
    public void exportPage(Pageable pageable, DictOptionQuery query, HttpServletResponse response) {
        Page<DictOptionDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), DictOptionDto.class);
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
        LambdaQueryWrapper<DictOption> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DictOption::getDictId, dictId).orderByAsc(DictOption::getValue);
        return dao.pojoList(wrapper, this::doToDto);
    }

    private void checkEntityList(Collection<DictOption> list) {
        if (CollectionUtil.isEmpty(list)) {
            throw new DefaultException(ResultCode.DATA_ERROR.getCode(), "【数据字典选项】保存的列表不能为空！");
        }

        Set<String> codes = list.stream().map(DictOption::getCode).collect(Collectors.toSet());
        if (codes.size() != list.size()) {
            throw new DefaultException(ResultCode.DATA_ERROR.getCode(), "【数据字典选项】保存的数据字典项 code 不能重复！");
        }

        Set<String> values = list.stream().map(DictOption::getValue).collect(Collectors.toSet());
        if (values.size() != list.size()) {
            throw new DefaultException(ResultCode.DATA_ERROR.getCode(), "【数据字典选项】保存的数据字典项 value 不能重复！");
        }

        list.forEach(this::checkEntity);
    }

    private void checkEntity(DictOption entity) {
        if (entity.getDictId() != null && StrUtil.isNotBlank(entity.getCode())) {
            LambdaQueryWrapper<DictOption> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DictOption::getDictId, entity.getDictId());
            List<DictOption> list = dao.list(wrapper);
            if (CollectionUtil.isEmpty(list)) {
                return;
            }

            for (DictOption item : list) {
                if (entity.getId() != null && entity.getId().equals(item.getId())) {
                    continue;
                }
                if (item.getCode().equals(entity.getCode())) {
                    String msg = StrUtil.format("【数据字典选项】dictId = {}, code = {} 存在重复记录",
                                                entity.getDictId(), entity.getCode());
                    throw new DefaultException(ResultCode.DATA_ERROR.getCode(), msg);
                }
                if (item.getValue().equals(entity.getValue())) {
                    String msg = StrUtil.format("【数据字典选项】dictId = {}, value = {} 存在重复记录",
                                                entity.getDictId(), entity.getValue());
                    throw new DefaultException(ResultCode.DATA_ERROR.getCode(), msg);
                }
            }
        }
    }

}
