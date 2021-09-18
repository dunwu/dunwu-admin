package io.github.dunwu.module.code.dao;

import io.github.dunwu.tool.data.mybatis.IExtDao;
import io.github.dunwu.module.code.entity.CodeDatabase;
import io.github.dunwu.module.code.entity.dto.CodeDatabaseDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 数据库管理 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-17
 */
public interface CodeDatabaseDao extends IExtDao<CodeDatabase> {

    /**
     * 根据传入的 CodeDatabaseDto 列表，导出 excel 表单
     *
     * @param list     {@link CodeDatabaseDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<CodeDatabaseDto> list, HttpServletResponse response) throws IOException;

}
