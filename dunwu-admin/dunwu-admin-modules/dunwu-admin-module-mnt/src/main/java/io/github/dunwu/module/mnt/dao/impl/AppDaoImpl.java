package io.github.dunwu.module.mnt.dao.impl;

import io.github.dunwu.module.mnt.dao.AppDao;
import io.github.dunwu.module.mnt.dao.mapper.AppMapper;
import io.github.dunwu.module.mnt.entity.App;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 应用配置 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Dao
public class AppDaoImpl extends BaseExtDaoImpl<AppMapper, App> implements AppDao {

}
