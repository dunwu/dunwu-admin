package io.github.dunwu.module.cas.dao;

import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.util.List;

/**
 * 菜单表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
public interface MenuDao extends IExtDao<Menu> {

    List<Menu> listByPid(Long pid);

}
