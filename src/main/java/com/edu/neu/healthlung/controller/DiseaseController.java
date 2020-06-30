package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.User;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.DiseaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class DiseaseController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private DiseaseService diseaseService;

    @GetMapping("/disease/{diseaseId}")
    @ApiOperation(value = "根据疾病ID返回疾病信息")
    public Disease get(@PathVariable Integer diseaseId){
        Disease disease =  diseaseService.getById(diseaseId);
        if(disease == null){
            throw new NotFoundException("疾病不存在");
        }
        return disease;
    }

    @GetMapping("/diseases/page/{pageNum}/")
    @ApiOperation(value = "返回药品列表每页10个")
    public List<Disease> gets(@PathVariable Integer pageNum){
        Page<Disease> page = diseaseService.page(new Page<>(pageNum, defaultPageSize));
        List<Disease> diseaseList = page.getRecords();
        return diseaseList;
    }

    @GetMapping("/diseases/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据疾病名称和症状模糊搜索，每页10个")
    public List<Disease> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        LambdaQueryWrapper<Disease> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Disease::getName, queryStr).or().like(Disease::getSymptom, queryStr);
        Page<Disease> page = diseaseService.page(new Page<>(pageNum, defaultPageSize), queryWrapper);
        return page.getRecords();
    }
}

