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
@ApiModel(value="医院")
public class Hospital implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "医院ID")
    @TableId(value = "hospital_id", type = IdType.AUTO)
    private Integer hospitalId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "爬虫网址")
    private String spiderUrl;

    @ApiModelProperty(value = "图片网址")
    private String imageUrl;

    @ApiModelProperty(value = "电话号码")
    private String telNumber;

    @ApiModelProperty(value = "医院级别")
    private String level;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "描述")
    private String description;


}
