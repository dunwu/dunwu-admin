package io.github.dunwu.module.mnt.dao;

import io.github.dunwu.module.mnt.entity.Server;
import io.github.dunwu.tool.data.mybatis.IExtDao;

/**
 * 服务器配置表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public interface ServerDao extends IExtDao<Server> {

    Server getByIp(String ip);

}
