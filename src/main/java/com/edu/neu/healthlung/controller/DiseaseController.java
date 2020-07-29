package com.edu.neu.healthlung.controller;


import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.DiseaseService;
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

    @GetMapping("/diseases/page/{pageNum}")
    @ApiOperation(value = "返回疾病列表每页10个")
    public List<Disease> gets(@PathVariable Integer pageNum){
        return diseaseService.pageOrderByHot(pageNum);
    }

    @GetMapping("/diseases/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据疾病名称和症状模糊搜索，每页10个")
    public List<Disease> gets(@PathVariable Integer pageNum, @PathVariable String queryStr){
        return diseaseService.searchOrderByHot(pageNum, queryStr);
    }

    @GetMapping("/diseases/pinyin/{pinyin}")
    @ApiOperation(value = "根据疾病名称的拼音首字母分类检索")
    public List<Disease> gets_(@PathVariable String pinyin){
        return diseaseService.listByPinyinOrderByHot(pinyin);
    }

    @GetMapping("/diseases/hot/page/{pageNum}")
    @ApiOperation(value = "根据疾病热度排序，每页10个")
    public List<Disease> gets_(@PathVariable Integer pageNum){
        return diseaseService.pageOrderByHot(pageNum);
    }

}

