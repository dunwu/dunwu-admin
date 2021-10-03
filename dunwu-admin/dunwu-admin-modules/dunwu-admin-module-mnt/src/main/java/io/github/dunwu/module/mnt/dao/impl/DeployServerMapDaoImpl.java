package io.github.dunwu.module.mnt.dao.impl;

import io.github.dunwu.module.mnt.dao.DeployServerMapDao;
import io.github.dunwu.module.mnt.dao.mapper.DeployServerMapMapper;
import io.github.dunwu.module.mnt.entity.DeployServerMap;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 应用和服务关联表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Dao
public class DeployServerMapDaoImpl extends BaseExtDaoImpl<DeployServerMapMapper, DeployServerMap>
    implements DeployServerMapDao {

}
