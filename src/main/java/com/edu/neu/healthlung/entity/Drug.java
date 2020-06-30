package com.edu.neu.healthlung.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="药品")
public class Drug implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "drug_id", type = IdType.AUTO)
    @ApiModelProperty(value = "药品ID")
    private Integer drugId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "药品种类")
    private String drugType;

    @ApiModelProperty(value = "适应症")
    private String indication;

    @ApiModelProperty(value = "主要成分")
    private String mainIngredient;

    @ApiModelProperty(value = "功能主治")
    private String function;

    @ApiModelProperty(value = "用法用量")
    private String dosage;

    @ApiModelProperty(value = "审批文号")
    private String approvalNumber;

    @ApiModelProperty(value = "生产企业")
    private String manufacturer;

    @ApiModelProperty(value = "价格")
    private String price;

    @ApiModelProperty(value = "相关标签")
    private String tag;

    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;


}
