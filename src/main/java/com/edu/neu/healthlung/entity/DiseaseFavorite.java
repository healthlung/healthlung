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
@ApiModel(value="DiseaseFavorite")
public class DiseaseFavorite implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "疾病收藏ID")
    @TableId(value = "disease_favorite_id", type = IdType.AUTO)
    @Null(message = "疾病收藏ID字段禁止传入")
    private Integer diseaseFavoriteId;

    @ApiModelProperty(value = "疾病ID")
    @NotNull(message = "疾病ID字段不能为空")
    private Integer diseaseId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "收藏时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "懒惰删除")
    @Null
    @TableLogic
    private Boolean flag;


}
