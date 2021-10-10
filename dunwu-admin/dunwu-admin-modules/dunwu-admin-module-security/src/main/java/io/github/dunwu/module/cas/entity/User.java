package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 系统用户
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_user")
@ApiModel(value = "User", description = "系统用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "部门ID")
    @TableField(value = "`dept_id`", updateStrategy = FieldStrategy.IGNORED)
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    @TableField("`job_id`")
    private Long jobId;

    @ApiModelProperty(value = "用户名")
    @TableField("`username`")
    private String username;

    @ApiModelProperty(value = "昵称")
    @TableField("`nickname`")
    private String nickname;

    @ApiModelProperty(value = "性别")
    @TableField("`gender`")
    private String gender;

    @ApiModelProperty(value = "手机号码")
    @TableField("`phone`")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("`email`")
    private String email;

    @ApiModelProperty(value = "头像地址")
    @TableField("`avatar`")
    private String avatar;

    @ApiModelProperty(value = "密码")
    @TableField("`password`")
    private String password;

    @ApiModelProperty(value = "是否禁用：1禁用、0启用")
    @TableField("`is_disabled`")
    private Boolean disabled;

    @ApiModelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @ApiModelProperty(value = "更新着")
    @TableField("`update_by`")
    private String updateBy;

    @ApiModelProperty(value = "修改密码的时间")
    @TableField("`pwd_reset_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime pwdResetTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String DEPT_ID = "dept_id";
    public static final String JOB_ID = "job_id";
    public static final String USERNAME = "username";
    public static final String NICKNAME = "nickname";
    public static final String GENDER = "gender";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String AVATAR = "avatar";
    public static final String PASSWORD = "password";
    public static final String ENABLED = "enabled";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String PWD_RESET_TIME = "pwd_reset_time";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}