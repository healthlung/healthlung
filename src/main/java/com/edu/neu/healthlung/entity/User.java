package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.edu.neu.healthlung.validate.InsertGroup;
import com.edu.neu.healthlung.validate.Password;
import com.edu.neu.healthlung.validate.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

/**
 * <p>
 * 
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    @Null(message = "用户ID无法插入和更新", groups = {UpdateGroup.class, InsertGroup.class})
    private Integer userId;

    @Email(message = "用户邮箱格式错误", groups = InsertGroup.class)
    @Null(message = "用户邮箱无法更新", groups = UpdateGroup.class)
    @ApiModelProperty(value = "用户邮箱")
    private String email;


    @Null(message = "此接口无法更新用户密码", groups = UpdateGroup.class)
    @Password(groups = InsertGroup.class)
    @ApiModelProperty(value = "密码")
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank(message = "用户昵称不能为空", groups = InsertGroup.class)
    @ApiModelProperty(value = "昵称")
    private String nickname;

}
