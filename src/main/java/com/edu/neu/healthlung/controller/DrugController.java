package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.DrugService;
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
public class DrugController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private DrugService drugService;

    @GetMapping("/drug/{drugId}")
    @ApiOperation(value = "根据药品ID返回药品信息")
    public Drug get(@PathVariable Integer drugId){
        Drug drug =  drugService.getById(drugId);
        if(drug == null){
            throw new NotFoundException("药品不存在");
        }
        return drug;
    }

    @GetMapping("/drugs/page/{pageNum}")
    @ApiOperation(value = "返回药品列表每页10个")
    public List<Drug> gets(@PathVariable Integer pageNum){
        Page<Drug> page = drugService.page(new Page<>(pageNum, defaultPageSize));
        return page.getRecords();
    }

    @GetMapping("/drugs/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据药品名称和适应症模糊搜索")
    public List<Drug> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        LambdaQueryWrapper<Drug> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Drug::getName, queryStr).or().like(Drug::getIndication, queryStr);
        return drugService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

}

