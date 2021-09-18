package io.github.dunwu.module.code.dao;

import io.github.dunwu.tool.data.mybatis.IExtDao;
import io.github.dunwu.module.code.entity.dto.CodeTableConfigDto;
import io.github.dunwu.module.code.entity.CodeTableConfig;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-02-28
 */
public interface CodeTableConfigDao extends IExtDao<CodeTableConfig> {

    /**
     * 根据传入的 CodeTableConfigDto 列表，导出 excel 表单
     *
     * @param list     {@link CodeTableConfigDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<CodeTableConfigDto> list, HttpServletResponse response) throws IOException;

}
