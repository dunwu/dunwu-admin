package io.github.dunwu.module.code.dao.impl;

import io.github.dunwu.tool.data.core.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.module.code.dao.CodeDatabaseDao;
import io.github.dunwu.module.code.entity.CodeDatabase;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;
import io.github.dunwu.module.code.dao.mapper.CodeDatabaseMapper;
import io.github.dunwu.tool.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据库管理 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
@Dao
public class CodeDatabaseDaoImpl extends BaseExtDaoImpl<CodeDatabaseMapper, CodeDatabase> implements CodeDatabaseDao {

    @Override
    public void exportDtoList(Collection<CodeDatabaseDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeDatabaseDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("数据库名称", item.getName());
            map.put("Host", item.getHost());
            map.put("端口号", item.getPort());
            map.put("jdbc地址", item.getJdbcUrl());
            map.put("账号", item.getUsername());
            map.put("密码", item.getPassword());
            map.put("Schema名称", item.getSchemaName());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
