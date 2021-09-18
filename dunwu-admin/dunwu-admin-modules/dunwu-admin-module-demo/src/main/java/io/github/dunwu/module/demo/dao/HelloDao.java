package io.github.dunwu.module.demo.dao;

import io.github.dunwu.tool.data.mybatis.IExtDao;
import io.github.dunwu.module.demo.entity.Hello;
import io.github.dunwu.module.demo.entity.dto.HelloDto;

import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Dao 接口
 *
 * @author zp
 * @since 2021-09-17
 */
public interface HelloDao extends IExtDao<Hello> {

    /**
     * 根据传入的 HelloDto 列表，导出 excel 表单
     *
     * @param list     {@link HelloDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<HelloDto> list, HttpServletResponse response);

}
