package com.edu.neu.healthlung.controller;

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

    @GetMapping("/healthTips/page/{pageNum}")
    @ApiOperation(value = "返回贴士列表每页10个, 按照时间排序")
    public List<HealthTip> gets(@PathVariable Integer pageNum){
        return healthTipService.pageOrderByPublishDate(pageNum);
    }

    //todo: 从缓存中找
    @GetMapping("/healthTips/hot/page/{pageNum}")
    @ApiOperation(value = "返回贴士列表每页10个, 按照热度排序")
    public List<HealthTip> gets__(@PathVariable Integer pageNum){
        return healthTipService.pageOrderByHot(pageNum);
    }

    //todo: 从缓存中找
    @GetMapping("/healthTips/page/{pageNum}/module/{module}")
    @ApiOperation(value = "返回对应模块下的贴士列表, 按照日期排序")
    public List<HealthTip> gets__(@PathVariable Integer pageNum, @PathVariable String module){
        return healthTipService.pageByModuleOrderByPublishDate(pageNum, module);
    }

    //todo: 从缓存中找
    @GetMapping("/healthTips/hot/page/{pageNum}/module/{module}")
    @ApiOperation(value = "返回对应模块下的贴士列表, 按照热度排序")
    public List<HealthTip> gets_(@PathVariable Integer pageNum, @PathVariable String module){
        return healthTipService.pageByModuleOrderByHot(pageNum, module);
    }

    //todo: 从es中找
    @GetMapping("/healthTips/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊搜索, 按照时间排序")
    public List<HealthTip> gets___(@PathVariable Integer pageNum, @PathVariable String queryStr){
        return healthTipService.searchOrderByPublishDate(pageNum, queryStr);
    }

    //todo: 从es中找
    @GetMapping("/healthTips/hot/page/{pageNum}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊搜索，按照热度排序")
    public List<HealthTip> gets____(@PathVariable Integer pageNum, @PathVariable String queryStr){
        return healthTipService.searchOrderByHot(pageNum, queryStr);
    }

    //todo: 从es中找
    @GetMapping("/healthTips/page/{pageNum}/module/{module}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊分模块搜索，按照时间排序")
    public List<HealthTip> gets(@PathVariable Integer pageNum, @PathVariable String queryStr, @PathVariable String module){
        return healthTipService.searchByModuleOrderByPublishDate(pageNum, module, queryStr);
    }

    //todo: 从es中找｛但是按照热度排序如何解决，更新就同步开销过大，先按照es里面的查，然后每隔多长时间同步一下｝
    @GetMapping("/healthTips/hot/page/{pageNum}/module/{module}/query/{queryStr}")
    @ApiOperation(value = "根据贴士标题和简单内容模糊分模块搜索，按照热度排序")
    public List<HealthTip> gets_(@PathVariable Integer pageNum, @PathVariable String queryStr, @PathVariable String module){
        return healthTipService.searchByModuleOrderByHot(pageNum, module, queryStr);
    }

}

