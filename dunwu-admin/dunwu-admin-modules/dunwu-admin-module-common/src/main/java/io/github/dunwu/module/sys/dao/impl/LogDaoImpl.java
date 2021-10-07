package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.LogDao;
import io.github.dunwu.module.sys.dao.mapper.LogMapper;
import io.github.dunwu.module.sys.entity.Log;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统日志记录 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-28
 */
@Dao
public class LogDaoImpl extends BaseExtDaoImpl<LogMapper, Log> implements LogDao {

}
