package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.GlobalConfigDao;
import io.github.dunwu.module.sys.dao.mapper.GlobalConfigMapper;
import io.github.dunwu.module.sys.entity.GlobalConfig;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统全局配置表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-03
 */
@Dao
public class GlobalConfigDaoImpl extends BaseExtDaoImpl<GlobalConfigMapper, GlobalConfig> implements GlobalConfigDao {

}
