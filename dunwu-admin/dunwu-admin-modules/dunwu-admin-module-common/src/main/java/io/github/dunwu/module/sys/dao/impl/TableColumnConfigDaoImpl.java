package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.TableColumnConfigDao;
import io.github.dunwu.module.sys.dao.mapper.TableColumnConfigMapper;
import io.github.dunwu.module.sys.entity.TableColumnConfig;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 表字段配置表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
@Dao
public class TableColumnConfigDaoImpl extends BaseExtDaoImpl<TableColumnConfigMapper, TableColumnConfig> implements TableColumnConfigDao {

}
