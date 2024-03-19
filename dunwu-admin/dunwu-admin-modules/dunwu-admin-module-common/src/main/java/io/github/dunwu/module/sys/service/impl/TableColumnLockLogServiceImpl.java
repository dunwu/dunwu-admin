package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.module.sys.dao.TableColumnLockLogDao;
import io.github.dunwu.module.sys.entity.TableColumnLockLog;
import io.github.dunwu.module.sys.entity.dto.TableColumnLockLogDto;
import io.github.dunwu.module.sys.entity.query.TableColumnLockLogQuery;
import io.github.dunwu.module.sys.service.TableColumnLockLogService;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.DefaultException;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
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
import javax.servlet.http.HttpServletResponse;

/**
 * 表字段锁定记录表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
@Slf4j
@Service
public class TableColumnLockLogServiceImpl extends ServiceImpl implements TableColumnLockLogService {

    private final TableColumnLockLogDao dao;

    public TableColumnLockLogServiceImpl(TableColumnLockLogDao dao) {
        this.dao = dao;
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.ADD)
    public boolean insert(TableColumnLockLog entity) {
        return dao.insert(entity);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.BATCH_ADD)
    public boolean insertBatch(Collection<TableColumnLockLog> list) {
        return dao.insertBatch(list);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    public boolean updateById(TableColumnLockLog entity) {
        return dao.updateById(entity);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.BATCH_EDIT)
    public boolean updateBatchById(Collection<TableColumnLockLog> list) {
        return dao.updateBatchById(list);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    public boolean save(TableColumnLockLog entity) {
        return dao.save(entity);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.BATCH_SAVE)
    public boolean saveBatch(Collection<TableColumnLockLog> list) {
        return dao.saveBatch(list);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.DEL, bizNo = "{{#id}}")
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.BATCH_DEL, bizNo = "{{#ids}}")
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<TableColumnLockLogDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<TableColumnLockLogDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<TableColumnLockLogDto> pojoListByQuery(TableColumnLockLogQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<TableColumnLockLogDto> pojoSpringPageByQuery(Pageable pageable, TableColumnLockLogQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public Page<TableColumnLockLogDto> pojoSpringPageByQuery(PageQuery pageQuery, TableColumnLockLogQuery query) {
        return dao.pojoSpringPageByQuery(pageQuery, query, this::doToDto);
    }

    @Override
    public TableColumnLockLogDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public TableColumnLockLogDto pojoByQuery(TableColumnLockLogQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(TableColumnLockLogQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.IMPORT_EXCEL,
        success = "导入表字段锁定记录表(Excel文件：{{#file.getOriginalFilename()}})『成功』",
        fail = "导入表字段锁定记录表(Excel文件：{{#file.getOriginalFilename()}})『失败』"
    )
    public void importList(MultipartFile file) {
        try {
            ExcelUtil.saveExcelData(file.getInputStream(), TableColumnLockLog.class, dao);
        } catch (IOException e) {
            log.error("【表字段锁定记录表】【导入失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段锁定记录表】【导入失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.EXPORT_EXCEL, bizNo = "{{#ids}}")
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<TableColumnLockLogDto> list = dao.pojoListByIds(ids, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, list, TableColumnLockLogDto.class);
        } catch (IOException e) {
            log.error("【表字段锁定记录表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段锁定记录表】【导出失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.EXPORT_EXCEL,
        success = "分页查询导出表字段锁定记录表(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『成功』",
        fail = "分页查询导出表字段锁定记录表(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『失败』"
    )
    public void exportPage(Pageable pageable, TableColumnLockLogQuery query, HttpServletResponse response) {
        Page<TableColumnLockLogDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), TableColumnLockLogDto.class);
        } catch (IOException e) {
            log.error("【表字段锁定记录表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段锁定记录表】【导出失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段锁定记录表", operation = OperationType.EXPORT_EXCEL,
        success = "分页查询导出表字段锁定记录表(page={{#pageQuery.getPage()}}, size={{#pageQuery.getSize()}}, query={{#query.toString()}})『成功』",
        fail = "分页查询导出表字段锁定记录表(page={{#pageQuery.getPage()}}, size={{#pageQuery.getSize()}}, query={{#query.toString()}})『失败』"
    )
    public void exportPage(PageQuery pageQuery, TableColumnLockLogQuery query, HttpServletResponse response) {
        Page<TableColumnLockLogDto> page = dao.pojoSpringPageByQuery(pageQuery, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), TableColumnLockLogDto.class);
        } catch (IOException e) {
            log.error("【表字段锁定记录表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段锁定记录表】【导出失败】");
        }
    }

    @Override
    public TableColumnLockLogDto doToDto(TableColumnLockLog entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, TableColumnLockLogDto.class);
    }

    @Override
    public TableColumnLockLog dtoToDo(TableColumnLockLogDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, TableColumnLockLog.class);
    }

}
