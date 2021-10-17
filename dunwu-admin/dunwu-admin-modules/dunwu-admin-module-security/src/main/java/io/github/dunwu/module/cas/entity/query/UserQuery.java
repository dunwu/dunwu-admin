package io.github.dunwu.module.cas.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserQuery", description = "用户表")
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @QueryField(value = "`id`")
    private Long id;

    @ApiModelProperty(value = "部门ID")
    @QueryField(value = "`dept_id`")
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    @QueryField(value = "`job_id`")
    private Long jobId;

    @ApiModelProperty(value = "用户名")
    @QueryField(value = "`username`")
    private String username;

    @ApiModelProperty(value = "昵称")
    @QueryField(value = "`nickname`")
    private String nickname;

    @ApiModelProperty(value = "性别")
    @QueryField(value = "`gender`")
    private String gender;

    @ApiModelProperty(value = "手机号码")
    @QueryField(value = "`phone`")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @QueryField(value = "`email`")
    private String email;

    @ApiModelProperty(value = "是否禁用：1 表示禁用；0 表示启用")
    @QueryField(value = "`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField(value = "`update_time`", type = QueryField.QueryType.BETWEEN)
    private Collection<LocalDateTime> updateTimeRange;

    @ApiModelProperty(value = "ID、用户名、昵称混合查询")
    @QueryField(blurry = { "id", "username", "nickname" }, type = QueryField.QueryType.LIKE)
    private String blurry;

}
