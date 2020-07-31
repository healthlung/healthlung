package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
@ApiModel(value="医保政策")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "medicare", type = "medicare", replicas = 0)
public class Medicare implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "医保政策ID")
    @TableId(value = "medicare_id", type = IdType.AUTO)
    private Integer medicareId;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "标题")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "内容")
    private String content;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Field(type = FieldType.Date,format = DateFormat.basic_date)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期")
    private LocalDate publishDate;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "城市")
    private String city;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;

}
