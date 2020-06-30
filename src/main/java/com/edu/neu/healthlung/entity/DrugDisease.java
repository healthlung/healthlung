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
@ApiModel(value="药品疾病的联系")
public class DrugDisease implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "药品疾病ID")
    @TableId(value = "drug_disease_id", type = IdType.AUTO)
    private Integer drugDiseaseId;

    @ApiModelProperty(value = "药品ID")
    private Integer drugId;

    @ApiModelProperty(value = "疾病ID")
    private Integer diseaseId;


}
