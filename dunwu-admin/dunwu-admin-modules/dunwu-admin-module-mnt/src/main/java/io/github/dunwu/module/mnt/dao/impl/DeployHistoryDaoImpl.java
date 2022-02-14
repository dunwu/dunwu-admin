package io.github.dunwu.module.mnt.dao.impl;

import io.github.dunwu.module.mnt.dao.DeployHistoryDao;
import io.github.dunwu.module.mnt.dao.mapper.DeployHistoryMapper;
import io.github.dunwu.module.mnt.entity.DeployHistory;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

/**
 * 部署历史管理 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Dao
public class DeployHistoryDaoImpl extends BaseExtDaoImpl<DeployHistoryMapper, DeployHistory>
    implements DeployHistoryDao {

}
