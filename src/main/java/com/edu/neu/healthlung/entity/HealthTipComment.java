package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.edu.neu.healthlung.validate.InsertGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(value="健康贴士的评论")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthTipComment implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "health_tip_comment_id", type = IdType.AUTO)
    @Null(message = "禁止传入评论ID字段", groups = InsertGroup.class)
    private Integer healthTipCommentId;

    @ApiModelProperty(value = "健康贴士ID")
    private Integer healthTipId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "内容")
    @Length(min = 1, max = 255, message = "评论内容长度非法", groups = InsertGroup.class)
    private String content;

    @ApiModelProperty(value = "评论日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "被回复评论ID")
    private Integer lastCommentId;

    @TableField(exist = false)
    private User user;

    @TableLogic
    @JsonIgnore
    private Boolean flag;


}
