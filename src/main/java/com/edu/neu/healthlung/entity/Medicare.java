package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="医保政策")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Medicare implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "医保政策ID")
    @TableId(value = "medicare_id", type = IdType.AUTO)
    private Integer medicareId;

    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发布日期")
    private Date publishDate;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;


}
