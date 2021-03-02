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
 * @since 2021-03-02
 */
@Dao
public class CodeTableConfigDaoImpl extends BaseExtDaoImpl<CodeTableConfigMapper, CodeTableConfig> implements CodeTableConfigDao {

    @Override
    public void exportDtoList(Collection<CodeTableConfigDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (CodeTableConfigDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("Schema名称", item.getSchemaName());
            map.put("Table名称", item.getName());
            map.put("Table注释", item.getComment());
            map.put("开启权限校验", item.getEnablePermission());
            map.put("开启搜索", item.getEnableQuery());
            map.put("开启列表", item.getEnableList());
            map.put("开启表单", item.getEnableForm());
            map.put("开启校验", item.getEnableValidate());
            map.put("作者", item.getAuthor());
            map.put("允许覆盖", item.getEnableCover());
            map.put("开启Swagger", item.getEnableSwagger());
            map.put("模块名称", item.getModuleName());
            map.put("包路径", item.getPackagePath());
            map.put("输出路径", item.getOutputPath());
            map.put("后端代码路径", item.getBackendPath());
            map.put("前端代码路径", item.getFrontendPath());
            map.put("表前缀", item.getPrefix());
            map.put("REST接口名称", item.getApiUrl());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(mapList, response);
    }

}
