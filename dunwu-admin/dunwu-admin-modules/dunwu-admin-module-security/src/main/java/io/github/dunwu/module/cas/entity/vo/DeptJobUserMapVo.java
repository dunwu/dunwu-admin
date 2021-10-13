package io.github.dunwu.module.cas.entity.vo;

import lombok.Data;

import java.util.Collection;

/**
 * 部门/职务/用户列表 关联映射实体
 *
 * @author peng.zhang
 * @date 2021-10-11
 */
@Data
public class DeptJobUserMapVo {

    private Long deptId;
    private Long jobId;
    private Collection<Long> userIds;

}
