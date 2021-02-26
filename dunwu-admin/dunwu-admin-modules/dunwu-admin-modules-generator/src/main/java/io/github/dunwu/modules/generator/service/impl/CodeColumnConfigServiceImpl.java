package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.generator.dao.CodeColumnConfigDao;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.service.CodeColumnConfigService;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Service
@RequiredArgsConstructor
public class CodeColumnConfigServiceImpl extends ServiceImpl implements CodeColumnConfigService {

    private final CodeColumnConfigDao dao;
    private final TableService tableService;

    @Override
    public boolean save(CodeColumnConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeColumnConfig> list) {
        return dao.saveBatch(list);
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
    public boolean removeById(Serializable id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public Page<CodeColumnConfigDto> pojoPageByQuery(Object query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<CodeColumnConfigDto> pojoListByQuery(Object query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeColumnConfigDto pojoByQuery(Object query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeColumnConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<CodeColumnConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public void sync(Collection<String> tables) {
        for (String table : tables) {
            CodeColumnConfigQuery query = new CodeColumnConfigQuery();
            query.setTableName(table);
            List<CodeColumnConfig> oldList = dao.listByQuery(query);
            List<CodeColumnConfigDto> dtos = tableService.getColumns(table);
            List<CodeColumnConfig> newList = dtos.stream().map(this::dtoToDo).collect(Collectors.toList());

            // 第一种情况，数据库类字段改变或者新增字段
            for (CodeColumnConfig columnInfo : newList) {
                // 根据字段名称查找
                List<CodeColumnConfig> columns = oldList.stream()
                                                        .filter(Objects::nonNull)
                                                        .filter(
                                                            c -> c.getColumnName().equals(columnInfo.getColumnName()))
                                                        .collect(Collectors.toList());

                // 如果能找到，就修改部分可能被字段
                if (CollectionUtil.isNotEmpty(columns)) {
                    CodeColumnConfig column = columns.get(0);
                    column.setColumnType(columnInfo.getColumnType());
                    column.setExtra(columnInfo.getExtra());
                    column.setColumnKey(columnInfo.getColumnKey());
                    if (StringUtils.isBlank(column.getNote())) {
                        column.setNote(columnInfo.getNote());
                    }
                    save(column);
                } else {
                    // 如果找不到，则保存新字段信息
                    save(columnInfo);
                }
            }

            // 第二种情况，数据库字段删除了
            for (CodeColumnConfig column : oldList) {
                // 根据字段名称查找
                List<CodeColumnConfig> columns = oldList.stream()
                                                        .filter(Objects::nonNull)
                                                        .filter(
                                                            c -> c.getColumnName().equals(column.getColumnName()))
                                                        .collect(Collectors.toList());

                // 如果找不到，就代表字段被删除了，则需要删除该字段
                if (CollectionUtil.isEmpty(columns)) {
                    removeById(column.getId());
                }
            }
        }
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
