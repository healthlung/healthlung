package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.DiseaseFavorite;
import com.edu.neu.healthlung.entity.DrugFavorite;
import com.edu.neu.healthlung.entity.HealthTipComment;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.DiseaseFavoriteService;
import com.edu.neu.healthlung.util.ParamHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
public class DiseaseFavoriteController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private DiseaseFavoriteService favoriteService;

    @GetMapping("/favorites/disease/page/{pageNum}")
    @ApiOperation(value = "返回用户收藏的疾病，每页10个")
    @Auth(needToken = true)
    public List<DiseaseFavorite> gets(@PathVariable Integer pageNum){
        Integer userId = ParamHolder.getCurrentUserId();
        return favoriteService.listByUserId(userId, pageNum);
    }

    @GetMapping("/favorite/disease/{diseaseId}")
    @ApiOperation(value = "根据疾病ID和用户token，返回收藏实体")
    @Auth(needToken = true)
    public DiseaseFavorite get(@PathVariable Integer diseaseId){
        Integer userId = ParamHolder.getCurrentUserId();
        LambdaQueryWrapper<DiseaseFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DiseaseFavorite::getUserId, userId).eq(DiseaseFavorite::getDiseaseId, diseaseId);
        return favoriteService.getOne(queryWrapper);
    }

    @PostMapping("/favorite/disease/{diseaseId}")
    @ApiOperation(value = "收藏某个疾病")
    @Auth(needToken = true)
    public String save(@PathVariable Integer diseaseId){
        Integer userId = ParamHolder.getCurrentUserId();
        DiseaseFavorite diseaseFavorite = new DiseaseFavorite();
        diseaseFavorite.setDiseaseId(diseaseId);
        diseaseFavorite.setUserId(userId);
        if(!favoriteService.save(diseaseFavorite)){
            throw new DefaultException("收藏疾病失败");
        }
        return "收藏疾病成功";
    }

    @DeleteMapping("/favorite/disease/{itemId}")
    @ApiOperation(value = "取消收藏某个疾病")
    @Auth(needToken = true)
    public String remove(@PathVariable Integer itemId){
        if(!favoriteService.removeByIdWithCheck(itemId)){
            throw new DefaultException("取消收藏疾病失败");
        }
        return "取消收藏疾病成功";
    }

}

