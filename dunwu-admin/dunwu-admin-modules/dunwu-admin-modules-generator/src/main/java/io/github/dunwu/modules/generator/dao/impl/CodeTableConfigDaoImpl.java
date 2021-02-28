package io.github.dunwu.modules.generator.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.generator.dao.CodeTableConfigDao;
import io.github.dunwu.modules.generator.dao.mapper.CodeTableConfigMapper;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
@Dao
public class CodeTableConfigDaoImpl extends BaseExtDaoImpl<CodeTableConfigMapper, CodeTableConfig> implements CodeTableConfigDao {

    @Override
    public void exportDtoList(Collection<CodeTableConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeTableConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("数据库Schema", item.getTableSchema());
            map.put("数据库Table", item.getTableName());
            map.put("作者", item.getAuthor());
            map.put("是否覆盖", item.getCover());
            map.put("模块名称", item.getModuleName());
            map.put("至于哪个包下", item.getPack());
            map.put("前端代码生成的路径", item.getPath());
            map.put("前端Api文件路径", item.getApiPath());
            map.put("表前缀", item.getPrefix());
            map.put("接口名称", item.getApiAlias());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(mapList, response);
    }

}
