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
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.AppException;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据字典 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl implements DictService {

    private final DictDao dictDao;
    private final DictOptionDao dictOptionDao;
    private final DictOptionService dictOptionService;

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.ADD)
    public boolean insert(Dict entity) {
        return dictDao.insert(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.BATCH_ADD)
    public boolean insertBatch(Collection<Dict> list) {
        return dictDao.insertBatch(list);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    public boolean updateById(Dict entity) {
        return dictDao.updateById(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.BATCH_EDIT)
    public boolean updateBatchById(Collection<Dict> list) {
        return dictDao.updateBatchById(list);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    public boolean save(Dict entity) {
        return dictDao.save(entity);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.BATCH_SAVE)
    public boolean saveBatch(Collection<Dict> list) {
        return dictDao.saveBatch(list);
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.DEL, bizNo = "{{#id}}")
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
    @OperationLog(bizType = "数据字典", operation = OperationType.BATCH_DEL, bizNo = "{{#ids}}")
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
    public Page<DictDto> pojoSpringPageByQuery(Pageable pageable, DictQuery query) {
        return dictDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
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
    @Transactional(rollbackFor = { Exception.class })
    @OperationLog(bizType = "数据字典", operation = OperationType.IMPORT_EXCEL,
        success = "导入数据字典(Excel文件：{{#file.getOriginalFilename()}})『成功』",
        fail = "导入数据字典(Excel文件：{{#file.getOriginalFilename()}})『失败』"
    )
    public void importList(MultipartFile file) {
        try {
            ExcelUtil.saveExcelData(file.getInputStream(), Dict.class, dictDao);
        } catch (IOException e) {
            log.error("【数据字典】【导入失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【数据字典】【导入失败】");
        }
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.EXPORT_EXCEL, bizNo = "{{#ids}}")
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<DictDto> list = dictDao.pojoListByIds(ids, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, list, DictDto.class);
        } catch (IOException e) {
            log.error("【数据字典】【导出失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【数据字典】【导出失败】");
        }
    }

    @Override
    @OperationLog(bizType = "数据字典", operation = OperationType.EXPORT_EXCEL,
        success = "分页查询导出数据字典(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『成功』",
        fail = "分页查询导出数据字典(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『失败』"
    )
    public void exportPage(Pageable pageable, DictQuery query, HttpServletResponse response) {
        Page<DictDto> page = dictDao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), DictDto.class);
        } catch (IOException e) {
            log.error("【数据字典】【导出失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【数据字典】【导出失败】");
        }
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
