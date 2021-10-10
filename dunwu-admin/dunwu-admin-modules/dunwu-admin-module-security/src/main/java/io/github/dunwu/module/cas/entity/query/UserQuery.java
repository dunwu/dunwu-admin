package io.github.dunwu.module.cas.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "UserQuery", description = "系统用户")
public class UserQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "id", "username", "nickname" })
    private String blurry;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "部门ID")
    @QueryField
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    @QueryField
    private Long jobId;

    @ApiModelProperty(value = "用户名")
    @QueryField
    private String username;

    @ApiModelProperty(value = "昵称")
    @QueryField
    private String nickname;

    @ApiModelProperty(value = "性别")
    @QueryField
    private String gender;

    @ApiModelProperty(value = "手机号码")
    @QueryField
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @QueryField
    private String email;

    @ApiModelProperty(value = "头像地址")
    @QueryField
    private String avatar;

    @ApiModelProperty(value = "密码")
    @QueryField
    private String password;

    @ApiModelProperty(value = "是否禁用：1禁用、0启用")
    @QueryField
    private Boolean disabled;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新着")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "修改密码的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private LocalDateTime pwdResetTime;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTimeRange;

}
