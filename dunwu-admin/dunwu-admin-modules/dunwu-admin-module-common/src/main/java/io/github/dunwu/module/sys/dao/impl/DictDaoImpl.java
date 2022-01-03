package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.DictDao;
import io.github.dunwu.module.sys.dao.mapper.DictMapper;
import io.github.dunwu.module.sys.entity.Dict;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 数据字典 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class DictDaoImpl extends BaseExtDaoImpl<DictMapper, Dict> implements DictDao {

}
