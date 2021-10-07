package io.github.dunwu.module.security.service;

import io.github.dunwu.module.cas.entity.dto.UserDto;

import java.util.List;

/**
 * 数据权限服务类
 *
 * @author Zheng Jie
 * @date 2020-05-07
 */
public interface DataService {

    /**
     * 获取数据权限
     *
     * @param user /
     * @return /
     */
    List<Long> getDeptIds(UserDto user);

}
