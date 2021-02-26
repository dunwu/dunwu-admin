package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.util.ObjectUtil;
import io.github.dunwu.modules.generator.entity.dto.TableInfoDto;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.service.TableService;
import io.github.dunwu.util.PageUtil;
import io.github.dunwu.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author peng.zhang
 * @date 2021/2/26
 */
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getTables() {
        // 使用预编译防止sql注入
        String sql = new StringBuilder("select table_name, create_time, engine, table_collation, table_comment")
            .append(" from information_schema.tables")
            .append(" where table_schema = (select database())")
            .append(" order by create_time desc")
            .toString();

        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public Object getTables(String name, int[] startEnd) {
        // 使用预编译防止sql注入
        String sql = new StringBuilder("select table_name, create_time , engine, table_collation, table_comment")
            .append(" from information_schema.tables")
            .append(" where table_schema = (select database())")
            .append(" and table_name like ? order by create_time desc")
            .toString();

        Query query = em.createNativeQuery(sql);
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1] - startEnd[0]);
        query.setParameter(1, StringUtils.isNotBlank(name) ? ("%" + name + "%") : "%%");
        List<?> result = query.getResultList();
        List<TableInfoDto> tableInfoDtos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            tableInfoDtos.add(new TableInfoDto(arr[0], arr[1], arr[2], arr[3], ObjectUtil.isNotEmpty(arr[4]) ? arr[4] : "-"));
        }
        Query query1 = em.createNativeQuery(
            "SELECT COUNT(*) FROM `information_schema`.`tables` WHERE `table_schema` = (SELECT database())");
        Object totalElements = query1.getSingleResult();
        return PageUtil.toPage(tableInfoDtos, totalElements);
    }

    @Override
    public List<CodeColumnConfigDto> getColumns(String tableName) {
        // 使用预编译防止sql注入
        String sql = new StringBuilder("SELECT column_name, is_nullable, data_type, column_comment, column_key, extra")
            .append(" FROM information_schema.columns ")
            .append(" WHERE table_name = ? AND table_schema = (SELECT database())")
            .append(" ORDER BY `ordinal_position`")
            .toString();

        Query query = em.createNativeQuery(sql);
        query.setParameter(1, tableName);
        List<?> result = query.getResultList();
        List<CodeColumnConfigDto> columnInfos = new ArrayList<>();
        for (Object obj : result) {
            Object[] arr = (Object[]) obj;
            columnInfos.add(
                new CodeColumnConfigDto(
                    tableName,
                    arr[0].toString(),
                    "NO".equals(arr[1]),
                    arr[2].toString(),
                    ObjectUtil.isNotNull(arr[3]) ? arr[3].toString() : null,
                    ObjectUtil.isNotNull(arr[4]) ? arr[4].toString() : null,
                    ObjectUtil.isNotNull(arr[5]) ? arr[5].toString() : null)
            );
        }
        return columnInfos;
    }

}
