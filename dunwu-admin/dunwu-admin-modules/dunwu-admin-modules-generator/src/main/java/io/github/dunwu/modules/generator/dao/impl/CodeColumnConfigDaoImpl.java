package io.github.dunwu.modules.generator.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.generator.dao.CodeColumnConfigDao;
import io.github.dunwu.modules.generator.dao.mapper.CodeColumnConfigMapper;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-26
 */
@Dao
public class CodeColumnConfigDaoImpl extends BaseExtDaoImpl<CodeColumnConfigMapper, CodeColumnConfig> implements CodeColumnConfigDao {

    @Override
    public void exportDtoList(Collection<CodeColumnConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeColumnConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("数据库Schema", item.getTableSchema());
            map.put("数据库Table", item.getTableName());
            map.put("字段名称", item.getName());
            map.put("字段类型", item.getColumnType());
            map.put("字段KEY类型", item.getColumnKey());
            map.put("字段备注", item.getComment());
            map.put("字典名称", item.getDictName());
            map.put("扩展属性", item.getExtra());
            // map.put("是否出现在表单", item.getFormShow());
            // map.put("表单类型", item.getFormType());
            // map.put("是否出现在列表", item.getListShow());
            // map.put("不允许为空", item.getNotNull());
            // map.put("查询类型", item.getQueryType());
            map.put("日期表达式", item.getDateExpression());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(mapList, response);
    }

}
