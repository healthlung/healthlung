package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
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
@ApiModel(value="医保政策的收藏", description="")
public class MedicareFavorite implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "收藏ID")
    @TableId(value = "medicare_favorite_id", type = IdType.AUTO)
    @Null(message = "医保收藏ID字段禁止传入")
    private Integer medicareFavoriteId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @NotNull(message = "医保ID字段不能为空")
    @ApiModelProperty(value = "医保ID")
    private Integer medicareId;

    @ApiModelProperty(value = "收藏日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @TableLogic
    private Boolean flag;


}
