package io.github.dunwu.module.sys.dao.impl;

import io.github.dunwu.module.sys.dao.DictOptionDao;
import io.github.dunwu.module.sys.dao.mapper.DictOptionMapper;
import io.github.dunwu.module.sys.entity.DictOption;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 系统数据字典项 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class DictOptionDaoImpl extends BaseExtDaoImpl<DictOptionMapper, DictOption>
    implements DictOptionDao {

}
