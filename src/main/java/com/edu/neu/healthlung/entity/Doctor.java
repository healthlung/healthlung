package com.edu.neu.healthlung.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="医生")
public class Doctor implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "医生ID")
    @TableId(value = "doctor_id", type = IdType.AUTO)
    private Integer doctorId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "医院ID")
    private Integer hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "图片Url")
    private String imageUrl;

    @ApiModelProperty(value = "所属部门")
    private String department;

    @ApiModelProperty(value = "职称")
    private String jobTitle;

    @ApiModelProperty(value = "擅长")
    private String expertIn;

    @ApiModelProperty(value = "简单描述")
    private String simpleDescription;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "省份推荐")
    private Boolean provinceRecommend;

    @ApiModelProperty(value = "全国推荐")
    private Boolean countryRecommend;

}
