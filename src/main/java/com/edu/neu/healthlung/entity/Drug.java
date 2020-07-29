package com.edu.neu.healthlung.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
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
@ApiModel(value="药品")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "healthlung", type = "drug", replicas = 0)
public class Drug implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @TableId(value = "drug_id", type = IdType.AUTO)
    @ApiModelProperty(value = "药品ID")
    private Integer drugId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "名称")
    private String name;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "药品种类")
    private String drugType;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "适应症")
    private String indication;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "主要成分")
    private String mainIngredient;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "功能主治")
    private String functionalIndication;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "用法用量")
    private String dosage;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "审批文号")
    private String approvalNumber;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "生产企业")
    private String manufacturer;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "价格")
    private String price;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "相关标签")
    private String tag;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;


}
