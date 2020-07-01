package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="健康贴士")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthTip implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "健康贴士ID")
    @TableId(value = "health_tip_id", type = IdType.AUTO)
    private Integer healthTipId;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "模块")
    private String module;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "简单内容")
    private String simpleContent;

    @ApiModelProperty(value = "发布日期")
    private Date publishDate;

    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber;

    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;

    @ApiModelProperty(value = "评论数")
    private Integer commentNumber;

}
