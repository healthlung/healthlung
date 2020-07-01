package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@ApiModel(value="药品的收藏")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugFavorite implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "药品收藏ID")
    @TableId(value = "drug_favorite_id", type = IdType.AUTO)
    @Null(message = "药品收藏ID字段禁止传入")
    private Integer drugFavoriteId;

    @ApiModelProperty(value = "收藏日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @ApiModelProperty(value = "药品ID")
    @NotNull(message = "药品ID字段不能为空")
    private Integer drugId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @TableField(exist = false)
    private Drug drug;

    @Null
    @TableLogic
    private Boolean flag;


}
