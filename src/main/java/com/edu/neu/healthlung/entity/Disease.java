package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
@ApiModel(value="疾病")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(indexName = "healthlung", type = "disease", replicas = 0)
public class Disease implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @ApiModelProperty(value = "疾病ID")
    @TableId(value = "disease_id", type = IdType.AUTO)
    private Integer diseaseId;

    @Field(type = FieldType.Keyword, index = false)
    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "名称")
    private String name;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "别名")
    private String anotherName;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "症状")
    private String symptom;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "简单描述")
    private String simpleDescription;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "描述")
    private String description;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "是否医保")
    private String medicare;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "发病部位")
    private String onset;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "传染性")
    private String infectious;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "多发人群")
    private String frequentCrowd;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "并发症")
    private String concurrentDisease;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "就诊科室")
    private String treatmentDepartment;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "费用")
    private String treatmentFees;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "治愈率")
    private String cureRate;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "治疗周期")
    private String treatmentCycle;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "治疗方法")
    private String treatmentMethod;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "相关检查")
    private String medicalExam;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "最佳就诊时间")
    private String bestTime;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "就诊时长")
    private String treatmentDuration;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "复诊率")
    private String revisitFrequency;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty(value = "就诊前准备")
    private String treatmentPrepare;

    @Field(type = FieldType.Integer)
    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;


}
