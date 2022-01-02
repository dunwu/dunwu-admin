package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.OperationLogDao;
import io.github.dunwu.module.sys.dao.mapper.OperationLogMapper;
import io.github.dunwu.module.sys.entity.OperationLog;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 操作日志表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-31
 */
@Dao
public class OperationLogDaoImpl extends BaseExtDaoImpl<OperationLogMapper, OperationLog> implements OperationLogDao {

}
