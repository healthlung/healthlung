package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="健康贴士的点赞")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthTipLike implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "点赞ID")
    @TableId(value = "health_tip_like_id", type = IdType.AUTO)
    private Integer healthTipLikeId;

    @ApiModelProperty(value = "健康贴士ID")
    private Integer healthTipId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "点赞日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableLogic
    private Boolean flag;


}
