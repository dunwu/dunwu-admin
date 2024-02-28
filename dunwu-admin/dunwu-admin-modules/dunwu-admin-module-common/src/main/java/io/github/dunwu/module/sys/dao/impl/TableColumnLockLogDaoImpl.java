package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.TableColumnLockLogDao;
import io.github.dunwu.module.sys.dao.mapper.TableColumnLockLogMapper;
import io.github.dunwu.module.sys.entity.TableColumnLockLog;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 表字段锁定记录表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
@Dao
public class TableColumnLockLogDaoImpl extends BaseExtDaoImpl<TableColumnLockLogMapper, TableColumnLockLog>
    implements TableColumnLockLogDao {

}
