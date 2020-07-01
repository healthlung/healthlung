package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.*;

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
@ApiModel(value="健康档案")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @NotNull(message = "健康档案ID不能为空")
    @TableId(value = "health_record_id", type = IdType.AUTO)
    private Integer healthRecordId;

    @Null(message = "禁止传入用户ID")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @NotBlank(message = "实际名称不能为空")
    @ApiModelProperty(value = "实际名称")
    private String actualName;

    @Pattern(regexp = "^(男|女)$", message = "性别只能是男或者女")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Min(value = 1, message = "年龄必须大于1岁")
    @Max(value = 200, message = "年龄必须小于200岁")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @NotNull(message = "是否有慢性肺部疾病字段不能为空")
    @ApiModelProperty(value = "是否有慢性肺部疾病")
    private Boolean chronicLungDisease;

    @ApiModelProperty(value = "慢性肺部疾病详情")
    private String chronicLungDiseaseDetail;

    @NotNull(message = "是否有肺部感染字段不能为空")
    @ApiModelProperty(value = "是否有肺部感染")
    private Boolean lungInfection;


    @ApiModelProperty(value = "肺部感染详情")
    private String lungInfectionDetail;

    @NotNull(message = "是否有恶性肿瘤字段不能为空")
    @ApiModelProperty(value = "是否有恶性肿瘤")
    private Boolean malignancy;

    @ApiModelProperty(value = "恶性肿瘤详情")
    private String malignancyDetail;

    @NotNull(message = "是否有家族肺病字段不能为空")
    @ApiModelProperty(value = "是否有家族肺病")
    private Boolean familyLungDisease;

    @ApiModelProperty(value = "家族肺病详情")
    private String familyLungDiseaseDetail;

    @NotNull(message = "是否有家族肺癌字段不能为空")
    @ApiModelProperty(value = "是否有家族肺癌")
    private Boolean familyLungCancer;

    @ApiModelProperty(value = "家族肺癌详情")
    private String familyLungCancerDetail;

    @Pattern(regexp = "^(无|1-10年|11-20年|21-30年|30年以上)$", message = "吸烟史只能是{'无','1-10年','11-20年','21-30年','30年以上'}中的枚举")
    @ApiModelProperty(value = "吸烟史 '无','1-10年','11-20年','21-30年','30年以上'")
    private String smokingHistory;

    @NotNull(message = "刺激性干咳字段不能为空")
    @ApiModelProperty(value = "刺激性干咳")
    private Boolean irritatingDryCough;

    @NotNull(message = "咳嗽咳痰字段不能为空")
    @ApiModelProperty(value = "咳嗽咳痰")
    private Boolean coughAndSputum;

    @NotNull(message = "痰中带血字段不能为空")
    @ApiModelProperty(value = "痰中带血")
    private Boolean bloodSputum;

    @NotNull(message = "胸闷胸痛字段不能为空")
    @ApiModelProperty(value = "胸闷胸痛")
    private Boolean chestTightness;

    @NotNull(message = "呼吸困难字段不能为空")
    @ApiModelProperty(value = "呼吸困难")
    private Boolean difficultyBreathing;

    @NotNull(message = "反复肺部感染字段不能为空")
    @ApiModelProperty(value = "反复肺部感染")
    private Boolean recurrentLungInfection;

    @ApiModelProperty(value = "居住省份")
    private String province;

    @ApiModelProperty(value = "居住城市")
    private String city;

    @NotNull(message = "是否接触粉尘不能为空")
    @ApiModelProperty(value = "是否接触粉尘")
    private Boolean dustContactWork;

    @ApiModelProperty(value = "接触粉尘详情")
    private String dustContactWordDetail;

    @NotNull(message = "是否接触刺激性气体不能为空")
    @ApiModelProperty(value = "是否接触刺激性气体")
    private Boolean hazardousGasContactWork;

    @ApiModelProperty(value = "接触刺激性气体详情")
    private String hazardousGasContactWorkDetail;

}
