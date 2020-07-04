package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.Medicare;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.MedicareService;
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
public class MedicareController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private MedicareService medicareService;

    @GetMapping("/medicare/{medicareId}")
    @ApiOperation(value = "根据医保ID返回医保信息")
    public Medicare get(@PathVariable Integer medicareId){
        Medicare item =  medicareService.getById(medicareId);
        if(item == null){
            throw new NotFoundException("医保政策不存在");
        }
        return item;
    }

    @GetMapping("/medicares/hot/page/{pageNum}/")
    @ApiOperation(value = "返回医保政策列表每页10个，按照热度排序")
    public List<Medicare> gets__(@PathVariable Integer pageNum){
        LambdaQueryWrapper<Medicare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Medicare::getPublishDate);
        Page<Medicare> page = medicareService.page(new Page<>(pageNum, defaultPageSize), queryWrapper);
        return page.getRecords();
    }

    @GetMapping("/medicares/page/{pageNum}/")
    @ApiOperation(value = "返回医保政策列表每页10个，按照时间排序")
    public List<Medicare> gets(@PathVariable Integer pageNum){
        LambdaQueryWrapper<Medicare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Medicare::getPublishDate);
        Page<Medicare> page = medicareService.page(new Page<>(pageNum, defaultPageSize), queryWrapper);
        return page.getRecords();
    }

    @GetMapping("/medicares/hot/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据标题和城市模糊搜索，按照热度排序")
    public List<Medicare> gets_(@PathVariable Integer pageNum, @PathVariable String queryStr){
        LambdaQueryWrapper<Medicare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Medicare::getTitle, queryStr).or().like(Medicare::getCity, queryStr).orderByDesc(Medicare::getFavoriteNumber);
        return medicareService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

    @GetMapping("/medicares/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据标题和城市模糊搜索，按照时间排序")
    public List<Medicare> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        LambdaQueryWrapper<Medicare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Medicare::getTitle, queryStr).or().like(Medicare::getCity, queryStr).orderByDesc(Medicare::getPublishDate);
        return medicareService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

}

