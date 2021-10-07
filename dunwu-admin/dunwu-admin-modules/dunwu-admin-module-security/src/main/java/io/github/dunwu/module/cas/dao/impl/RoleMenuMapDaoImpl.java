package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.RoleMenuMapDao;
import io.github.dunwu.module.cas.dao.mapper.RoleMenuMapMapper;
import io.github.dunwu.module.cas.entity.RoleMenuMap;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import org.springframework.stereotype.Service;

/**
 * 系统角色菜单关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-14
 */
@Service
public class RoleMenuMapDaoImpl extends BaseExtDaoImpl<RoleMenuMapMapper, RoleMenuMap> implements RoleMenuMapDao {

}
