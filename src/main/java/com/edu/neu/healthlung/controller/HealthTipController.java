package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.HealthTipService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
public class HealthTipController {
    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private HealthTipService healthTipService;

    @GetMapping("/healthTip/{healthTipId}")
    @ApiOperation(value = "根据贴士ID返回贴士信息")
    public HealthTip get(@PathVariable Integer healthTipId){
        HealthTip healthTip =  healthTipService.getById(healthTipId);
        if(healthTip == null){
            throw new NotFoundException("贴士不存在");
        }
        return healthTip;
    }

    @GetMapping("/healthTips/page/{pageNum}/")
    @ApiOperation(value = "返回贴士列表每页10个")
    public List<HealthTip> gets(@PathVariable Integer pageNum){
        return  healthTipService.page(new Page<>(pageNum, defaultPageSize)).getRecords();
    }

    @GetMapping("/healthTips/page/{pageNum}/module/{module}")
    @ApiOperation(value = "返回对应模块下的贴士列表")
    public List<HealthTip> gets_(@PathVariable Integer pageNum, @PathVariable String module){
        LambdaQueryWrapper<HealthTip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTip::getModule, module);
        return healthTipService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

    @GetMapping("/healthTips/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊搜索")
    public List<HealthTip> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        LambdaQueryWrapper<HealthTip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(HealthTip::getTitle, queryStr).or().like(HealthTip::getSimpleContent, queryStr);
        return healthTipService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

    @GetMapping("/healthTips/page/{pageNum}/module/{module}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊分模块搜索")
    public List<HealthTip> gets(@PathVariable Integer pageNum, @PathVariable String queryStr, @PathVariable String module){
        LambdaQueryWrapper<HealthTip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTip::getModule, module).and(i -> i.eq(HealthTip::getTitle, queryStr).or().like(HealthTip::getSimpleContent, queryStr));
        return healthTipService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

}

