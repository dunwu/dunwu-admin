package io.github.dunwu.module.cas.entity.vo;

import lombok.Data;

import java.util.Collection;

/**
 * @author peng.zhang
 * @date 2021-10-11
 */
@Data
public class DeptJobUserMapVo {

    private Long deptId;
    private Long jobId;
    private Collection<Long> userIds;

}
