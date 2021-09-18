package io.github.dunwu.module.system.dao.impl;

import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.module.system.dao.SysRoleMenuDao;
import io.github.dunwu.module.system.dao.mapper.SysRoleMenuMapper;
import io.github.dunwu.module.system.entity.SysRoleMenu;
import org.springframework.stereotype.Service;

/**
 * 系统角色菜单关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-14
 */
@Service
public class SysRoleMenuDaoImpl extends BaseExtDaoImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuDao {

}
