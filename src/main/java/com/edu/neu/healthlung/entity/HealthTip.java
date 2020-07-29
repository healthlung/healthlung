package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "healthlung", type = "healthTip", replicas = 0)
public class HealthTip implements Serializable{

    private static final long serialVersionUID=1L;

    @Id
    @ApiModelProperty(value = "健康贴士ID")
    @TableId(value = "health_tip_id", type = IdType.AUTO)
    private Integer healthTipId;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "模块")
    private String module;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "标题")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "内容")
    private String content;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "简单内容")
    private String simpleContent;

    @Field(type = FieldType.Date,format = DateFormat.basic_date)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期")
    private LocalDate publishDate;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "点赞数")
    private Integer likeNumber;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "评论数")
    private Integer commentNumber;

}
