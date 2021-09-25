package io.github.dunwu.module.system.dao.impl;

import io.github.dunwu.module.system.dao.SysRoleDao;
import io.github.dunwu.module.system.dao.mapper.SysRoleMapper;
import io.github.dunwu.module.system.entity.SysRole;
import io.github.dunwu.module.system.entity.dto.SysRoleDto;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.tool.web.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysRoleDaoImpl extends BaseExtDaoImpl<SysRoleMapper, SysRole> implements SysRoleDao {

    @Override
    public void exportDtoList(Collection<SysRoleDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysRoleDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("角色ID", item.getId());
            map.put("角色名称", item.getName());
            map.put("数据范围", item.getDataScope());
            map.put("角色级别", item.getLevel());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
