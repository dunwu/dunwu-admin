package io.github.dunwu.module.demo.dao.impl;

import io.github.dunwu.tool.data.core.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.module.demo.dao.HelloDao;
import io.github.dunwu.module.demo.dao.mapper.HelloMapper;
import io.github.dunwu.module.demo.entity.Hello;
import io.github.dunwu.module.demo.entity.dto.HelloDto;
import io.github.dunwu.tool.web.util.ServletUtil;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Dao 类
 *
 * @author zp
 * @since 2021-09-17
 */
@Dao
public class HelloDaoImpl extends BaseExtDaoImpl<HelloMapper, Hello> implements HelloDao {

    @Override
    public void exportDtoList(Collection<HelloDto> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (HelloDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("名字", item.getName());
            map.put("年龄", item.getAge());
            map.put("头像", item.getAvatar());
            map.put("创建时间", item.getCreateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
