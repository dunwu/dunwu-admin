package io.github.dunwu.module.system.dao;

import io.github.dunwu.tool.data.mybatis.IExtDao;
import io.github.dunwu.module.system.entity.SysDict;
import io.github.dunwu.module.system.entity.dto.SysDictDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysDictDao extends IExtDao<SysDict> {

    /**
     * 根据传入的 SysDictDto 列表，导出 excel 表单
     *
     * @param list     {@link SysDictDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysDictDto> list, HttpServletResponse response) throws IOException;

}
