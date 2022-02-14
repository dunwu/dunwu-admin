package io.github.dunwu.module.mnt.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.mnt.dao.ServerDao;
import io.github.dunwu.module.mnt.dao.mapper.ServerMapper;
import io.github.dunwu.module.mnt.entity.Server;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.util.List;

/**
 * 服务器配置表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Dao
public class ServerDaoImpl extends BaseExtDaoImpl<ServerMapper, Server> implements ServerDao {

    @Override
    public Server getByIp(String ip) {
        QueryWrapper<Server> wrapper = new QueryWrapper<>();
        wrapper.eq(Server.IP, ip);
        List<Server> list = list(wrapper);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
