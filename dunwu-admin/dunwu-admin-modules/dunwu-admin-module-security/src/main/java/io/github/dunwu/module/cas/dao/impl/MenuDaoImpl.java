package io.github.dunwu.module.cas.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.module.cas.dao.MenuDao;
import io.github.dunwu.module.cas.dao.mapper.MenuMapper;
import io.github.dunwu.module.cas.entity.Menu;
import io.github.dunwu.tool.data.annotation.Dao;
import io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl;

import java.util.List;

/**
 * 菜单表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-12
 */
@Dao
public class MenuDaoImpl extends BaseExtDaoImpl<MenuMapper, Menu> implements MenuDao {

    @Override
    public List<Menu> listByPid(Long pid) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        return list(wrapper);
    }

}
