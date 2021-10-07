package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.UserRoleMapDao;
import io.github.dunwu.module.cas.dao.mapper.UserRoleMapMapper;
import io.github.dunwu.module.cas.entity.UserRoleMap;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;
import org.springframework.stereotype.Service;

/**
 * 系统用户角色关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-06
 */
@Service
public class UserRoleMapDaoImpl extends BaseExtDaoImpl<UserRoleMapMapper, UserRoleMap> implements UserRoleMapDao {

}
