package io.github.dunwu.module.mnt.dao.impl;

import io.github.dunwu.module.mnt.dao.DeployDao;
import io.github.dunwu.module.mnt.dao.mapper.DeployMapper;
import io.github.dunwu.module.mnt.entity.Deploy;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 部署管理 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Dao
public class DeployDaoImpl extends BaseExtDaoImpl<DeployMapper, Deploy> implements DeployDao {

}
