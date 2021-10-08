package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * 用户部门关联表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-07
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_user_dept_map")
@ApiModel(value = "UserDeptMap", description = "用户部门关联表")
public class UserDeptMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    @TableField("`user_id`")
    private Long userId;

    @ApiModelProperty(value = "部门ID")
    @TableField("`dept_id`")
    private Long deptId;

    public UserDeptMap() { }

    public UserDeptMap(Long userId, Long deptId) {
        this.userId = userId;
        this.deptId = deptId;
    }

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String DEPT_ID = "dept_id";

}
