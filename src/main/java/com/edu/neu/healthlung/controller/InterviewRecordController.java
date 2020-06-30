package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.DiseaseFavorite;
import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.InterviewRecordService;
import com.edu.neu.healthlung.util.ParamHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@RestController
public class InterviewRecordController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    InterviewRecordService interviewRecordService;

    @PostMapping("/interviewRecord")
    @Auth(needToken = true)
    @ApiOperation(value = "生成问诊记录")
    public InterviewRecord generateRecord(@RequestParam("video") MultipartFile video) {
        return interviewRecordService.generateRecord(video);
    }

    @GetMapping("/interviewRecord/{recordId}")
    @ApiOperation(value = "根据记录ID返回诊断记录，只能获得本用户的问诊记录")
    @Auth(needToken = true)
    public InterviewRecord get(@PathVariable Integer recordId){
        InterviewRecord interviewRecord =  interviewRecordService.getByIdWithCheck(recordId);
        if(interviewRecord == null){
            throw new NotFoundException("记录不存在");
        }
        return interviewRecord;
    }

    @GetMapping("/interviewRecords/page/{pageNum}/")
    @ApiOperation(value = "返回当前用户诊断记录列表每页10个")
    @Auth(needToken = true)
    public List<InterviewRecord> gets(@PathVariable Integer pageNum){
        LambdaQueryWrapper<InterviewRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterviewRecord::getUserId, ParamHolder.getCurrentUserId());
        return interviewRecordService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }
}

