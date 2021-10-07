package io.github.dunwu.module.cas.dao.impl;

import io.github.dunwu.module.cas.dao.UserDao;
import io.github.dunwu.module.cas.dao.mapper.UserMapper;
import io.github.dunwu.module.cas.entity.User;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统用户 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@Dao
public class UserDaoImpl extends BaseExtDaoImpl<UserMapper, User> implements UserDao {

}
