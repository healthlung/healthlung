package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel(value="疾病")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Disease implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "疾病ID")
    @TableId(value = "disease_id", type = IdType.AUTO)
    private Integer diseaseId;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "拼音")
    private String pinyin;

    @ApiModelProperty(value = "别名")
    private String anotherName;

    @ApiModelProperty(value = "症状")
    private String symptom;

    @ApiModelProperty(value = "简单描述")
    private String simpleDescription;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "是否医保")
    private String medicare;

    @ApiModelProperty(value = "发病部位")
    private String onset;

    @ApiModelProperty(value = "传染性")
    private String infectious;

    @ApiModelProperty(value = "多发人群")
    private String frequentCrowd;

    @ApiModelProperty(value = "并发症")
    private String concurrentDisease;

    @ApiModelProperty(value = "就诊科室")
    private String treatmentDepartment;

    @ApiModelProperty(value = "费用")
    private String treatmentFees;

    @ApiModelProperty(value = "治愈率")
    private String cureRate;

    @ApiModelProperty(value = "治疗周期")
    private String treatmentCycle;

    @ApiModelProperty(value = "治疗方法")
    private String treatmentMethod;

    @ApiModelProperty(value = "相关检查")
    private String medicalExam;

    @ApiModelProperty(value = "最佳就诊时间")
    private String bestTime;

    @ApiModelProperty(value = "就诊时长")
    private String treatmentDuration;

    @ApiModelProperty(value = "复诊率")
    private String revisitFrequency;

    @ApiModelProperty(value = "就诊前准备")
    private String treatmentPrepare;

    @ApiModelProperty(value = "收藏数")
    private Integer favoriteNumber;


}
