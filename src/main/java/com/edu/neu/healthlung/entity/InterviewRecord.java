package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.edu.neu.healthlung.util.LungNodeListTypeHandler;
import com.edu.neu.healthlung.util.StringListTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value="问诊记录")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InterviewRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "问诊记录ID")
    @TableId(value = "interview_record_id", type = IdType.AUTO)
    private Integer interviewRecordId;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "CT图像列表")
    @TableField(value = "ct_image_urls", typeHandler = StringListTypeHandler.class)
    private List<String> ctImageUrls;

    @ApiModelProperty(value = "图片描述")
    private String imageDescription;

    @ApiModelProperty(value = "风险评估")
    private String riskAssessment;

    @ApiModelProperty(value = "风险总结")
    private String riskSummary;

    @ApiModelProperty(value = "治疗方法")
    private String treatmentMethod;

    @ApiModelProperty(value = "推荐医生")
    private String hospitalRecommend;

    @ApiModelProperty(value = "城市推荐医生ID")
    private Integer cityRecommendedDoctorId;

    @ApiModelProperty(value = "省份推荐医生ID")
    private Integer provinceRecommendedDoctorId;

    @ApiModelProperty(value = "国家推荐医生ID")
    private Integer countryRecommendedDoctorId;

    @ApiModelProperty(value = "医院ID")
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    @JsonIgnore
    @TableField(value = "lung_nodes", typeHandler = LungNodeListTypeHandler.class)
    private List<LungNode> lungNodes;

    //候选医生列表
    @JsonIgnore
    @TableField(exist = false)
    private List<List<Doctor>> candidateDoctors;

}
