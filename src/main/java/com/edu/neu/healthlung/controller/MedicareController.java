package com.edu.neu.healthlung.controller;


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

    @GetMapping("/medicares/hot/page/{pageNum}")
    @ApiOperation(value = "返回医保政策列表每页10个，按照热度排序")
    public List<Medicare> gets__(@PathVariable Integer pageNum){
        return medicareService.pageOrderByHot(pageNum);
    }

    @GetMapping("/medicares/page/{pageNum}")
    @ApiOperation(value = "返回医保政策列表每页10个，按照时间排序")
    public List<Medicare> gets(@PathVariable Integer pageNum){
        return medicareService.pageOrderByPublishDate(pageNum);
    }

    @GetMapping("/medicares/hot/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据标题和城市模糊搜索，按照热度排序")
    public List<Medicare> gets_(@PathVariable Integer pageNum, @PathVariable String queryStr){
        return medicareService.searchOrderByHot(pageNum, queryStr);
    }

    @GetMapping("/medicares/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据标题和城市模糊搜索，按照时间排序")
    public List<Medicare> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        return medicareService.searchOrderByPublishDate(pageNum, queryStr);
    }

}

