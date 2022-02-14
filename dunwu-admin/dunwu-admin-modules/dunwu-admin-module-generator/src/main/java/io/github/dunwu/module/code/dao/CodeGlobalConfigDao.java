package io.github.dunwu.module.code.dao;

import io.github.dunwu.module.code.entity.CodeGlobalConfig;
import io.github.dunwu.module.code.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.tool.data.mybatis.IExtDao;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-全局配置 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
public interface CodeGlobalConfigDao extends IExtDao<CodeGlobalConfig> {

    /**
     * 根据传入的 CodeGlobalConfigDto 列表，导出 excel 表单
     *
     * @param list     {@link CodeGlobalConfigDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<CodeGlobalConfigDto> list, HttpServletResponse response) throws IOException;

}
