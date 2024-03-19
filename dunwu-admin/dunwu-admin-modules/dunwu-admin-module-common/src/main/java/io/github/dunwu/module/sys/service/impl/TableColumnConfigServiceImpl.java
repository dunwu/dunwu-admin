package io.github.dunwu.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.dunwu.module.sys.dao.TableColumnConfigDao;
import io.github.dunwu.module.sys.entity.TableColumnConfig;
import io.github.dunwu.module.sys.entity.dto.TableColumnConfigDto;
import io.github.dunwu.module.sys.entity.dto.TableConfigDto;
import io.github.dunwu.module.sys.entity.query.TableColumnConfigQuery;
import io.github.dunwu.module.sys.service.TableColumnConfigService;
import io.github.dunwu.tool.core.constant.enums.ResultCode;
import io.github.dunwu.tool.core.exception.DefaultException;
import io.github.dunwu.tool.data.entity.TableColumnInfo;
import io.github.dunwu.tool.data.entity.TableInfo;
import io.github.dunwu.tool.data.excel.ExcelUtil;
import io.github.dunwu.tool.data.mybatis.ServiceImpl;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.response.PageImpl;
import io.github.dunwu.tool.data.util.DatabaseUtil;
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 表字段配置表 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TableColumnConfigServiceImpl extends ServiceImpl
    implements TableColumnConfigService, io.github.dunwu.tool.web.log.service.TableColumnConfigService {

    private final JdbcTemplate jdbcTemplate;
    private final TableColumnConfigDao dao;

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.ADD)
    public boolean insert(TableColumnConfig entity) {
        return dao.insert(entity);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.BATCH_ADD)
    public boolean insertBatch(Collection<TableColumnConfig> list) {
        return dao.insertBatch(list);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    public boolean updateById(TableColumnConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.BATCH_EDIT)
    public boolean updateBatchById(Collection<TableColumnConfig> list) {
        return dao.updateBatchById(list);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    public boolean save(TableColumnConfig entity) {
        return dao.save(entity);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.BATCH_SAVE)
    public boolean saveBatch(Collection<TableColumnConfig> list) {
        return dao.saveBatch(list);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.DEL, bizNo = "{{#id}}")
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.BATCH_DEL, bizNo = "{{#ids}}")
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<TableColumnConfigDto> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<TableColumnConfigDto> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<TableColumnConfigDto> pojoListByQuery(TableColumnConfigQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<TableColumnConfigDto> pojoSpringPageByQuery(Pageable pageable, TableColumnConfigQuery query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
    }

    @Override
    public Page<TableColumnConfigDto> pojoSpringPageByQuery(PageQuery pageQuery, TableColumnConfigQuery query) {
        return dao.pojoSpringPageByQuery(pageQuery, query, this::doToDto);
    }

    @Override
    public TableColumnConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public TableColumnConfigDto pojoByQuery(TableColumnConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(TableColumnConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    @OperationLog(bizType = "表字段配置表", operation = OperationType.IMPORT_EXCEL, success = "导入表字段配置表(Excel文件：{{#file.getOriginalFilename()}})『成功』", fail = "导入表字段配置表(Excel文件：{{#file.getOriginalFilename()}})『失败』")
    public void importList(MultipartFile file) {
        try {
            ExcelUtil.saveExcelData(file.getInputStream(), TableColumnConfig.class, dao);
        } catch (IOException e) {
            log.error("【表字段配置表】【导入失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段配置表】【导入失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.EXPORT_EXCEL, bizNo = "{{#ids}}")
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<TableColumnConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, list, TableColumnConfigDto.class);
        } catch (IOException e) {
            log.error("【表字段配置表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段配置表】【导出失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.EXPORT_EXCEL, success = "分页查询导出表字段配置表(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『成功』", fail = "分页查询导出表字段配置表(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『失败』")
    public void exportPage(Pageable pageable, TableColumnConfigQuery query, HttpServletResponse response) {
        Page<TableColumnConfigDto> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), TableColumnConfigDto.class);
        } catch (IOException e) {
            log.error("【表字段配置表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段配置表】【导出失败】");
        }
    }

    @Override
    @OperationLog(bizType = "表字段配置表", operation = OperationType.EXPORT_EXCEL, success = "分页查询导出表字段配置表(page={{#pageQuery.getPage()}}, size={{#pageQuery.getSize()}}, query={{#query.toString()}})『成功』", fail = "分页查询导出表字段配置表(page={{#pageQuery.getPage()}}, size={{#pageQuery.getSize()}}, query={{#query.toString()}})『失败』")
    public void exportPage(PageQuery pageQuery, TableColumnConfigQuery query, HttpServletResponse response) {
        Page<TableColumnConfigDto> page = dao.pojoSpringPageByQuery(pageQuery, query, this::doToDto);
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), TableColumnConfigDto.class);
        } catch (IOException e) {
            log.error("【表字段配置表】【导出失败】", e);
            throw new DefaultException(ResultCode.IO_ERROR.getCode(), "【表字段配置表】【导出失败】");
        }
    }

    @Override
    public TableColumnConfigDto doToDto(TableColumnConfig entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, TableColumnConfigDto.class);
    }

    @Override
    public TableColumnConfig dtoToDo(TableColumnConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, TableColumnConfig.class);
    }

    @Override
    public PageImpl<TableInfo> syncTablePage(String tableName, Pageable pageable) {
        Connection connection;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            return DatabaseUtil.getTables(connection, "dunwu_admin", tableName, pageable.getPageNumber(),
                pageable.getPageSize());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TableConfigDto syncTableConfig(TableColumnConfigQuery query) {

        Connection connection;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Map<String, TableColumnInfo> columnMap =
            DatabaseUtil.getColumnMap(connection, query.getSchemaName(), query.getTableName());
        if (MapUtil.isEmpty(columnMap)) {
            String msg = StrUtil.format("schema: {}, table: {} 未找到！", query.getSchemaName(), query.getTableName());
            throw new DefaultException(ResultCode.DATA_ERROR, msg);
        }

        TableConfigDto tableConfigDto = new TableConfigDto();
        tableConfigDto.setSchemaName(query.getSchemaName());
        tableConfigDto.setTableName(query.getTableName());

        // 如果已经存在指定的数据表的列属性配置，则直接返回
        List<TableColumnConfigDto> columnList = new ArrayList<>();
        List<TableColumnConfigDto> columnConfigList = pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(columnConfigList)) {
            for (TableColumnConfigDto column : columnConfigList) {
                TableColumnInfo tableColumnDto = columnMap.get(column.getColumnName());
                if (tableColumnDto == null) {
                    continue;
                }

                column.setColumnComment(tableColumnDto.getColumnComment());
                column.setDataType(tableColumnDto.getDataType());
                columnList.add(column);
            }
        } else {
            columnList = columnMap.values()
                                  .stream()
                                  .map(column -> {
                                      TableColumnConfigDto dto = new TableColumnConfigDto();
                                      dto.setSchemaName(column.getTableSchema());
                                      dto.setTableName(column.getTableName());
                                      dto.setColumnName(column.getColumnName());
                                      dto.setColumnComment(column.getColumnComment());
                                      dto.setDataType(column.getDataType());
                                      dto.setLocked(false);
                                      return dto;
                                  })
                                  .collect(Collectors.toList());
        }
        tableConfigDto.setColumns(columnList);
        return tableConfigDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTableConfig(TableConfigDto dto) {
        if (dto == null) {
            throw new DefaultException(ResultCode.PARAMS_ERROR, "请求参数不能为空！");
        }

        TableColumnConfigQuery query = new TableColumnConfigQuery();
        query.setSchemaName(dto.getSchemaName());
        query.setTableName(dto.getTableName());
        List<TableColumnConfigDto> oldColumns = pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(oldColumns)) {
            Set<Long> ids = oldColumns.stream()
                                      .map(TableColumnConfigDto::getId)
                                      .collect(Collectors.toSet());
            deleteBatchByIds(ids);
        }

        if (CollectionUtil.isNotEmpty(dto.getColumns())) {
            List<TableColumnConfig> newColumns =
                dto.getColumns().stream().map(this::dtoToDo).collect(Collectors.toList());
            return saveBatch(newColumns);
        }
        return true;
    }

    @Override
    public Set<String> getLockedColumns(String tableName) {
        LambdaQueryWrapper<TableColumnConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TableColumnConfig::getTableName, tableName);
        List<TableColumnConfig> list = dao.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return new HashSet<>(0);
        }

        return list.stream()
                   .filter(TableColumnConfig::getLocked)
                   .map(TableColumnConfig::getColumnName)
                   .collect(Collectors.toSet());
    }

}
