package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.entity.HealthRecord;
import com.edu.neu.healthlung.entity.User;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.HealthRecordService;
import com.edu.neu.healthlung.util.ParamHolder;
import com.edu.neu.healthlung.validate.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@RestController
public class HealthRecordController {

    @Resource
    HealthRecordService service;

    @GetMapping("/healthRecord")
    @ApiOperation(value = "获取当前用户的健康档案")
    @Auth(needToken = true, sameUser = true)
    public HealthRecord get(){
        LambdaQueryWrapper<HealthRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(HealthRecord::getUserId, ParamHolder.getCurrentUserId());
        HealthRecord healthRecord =  service.getOne(queryWrapper);
        if(healthRecord == null){
            throw new NotFoundException("健康档案不存在");
        }
        return healthRecord;
    }

    @PutMapping("/healthRecord")
    @Auth(needToken = true, sameUser = true)
    @ApiOperation(value = "用户修改自己的健康档案")
    public String save(@Validated @RequestBody HealthRecord healthRecord){
        healthRecord.setUserId(ParamHolder.getCurrentUserId());
        if(!service.updateById(healthRecord)){
            throw new DefaultException("更新失败");
        }
        return "更新成功";
    }
}

