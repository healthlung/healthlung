package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.HealthTipComment;
import com.edu.neu.healthlung.entity.HealthTipFavorite;

import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.HealthTipFavoriteService;
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
public class HealthTipFavoriteController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private HealthTipFavoriteService favoriteService;

    @GetMapping("/favorite/healthTip/{healthTipId}")
    @ApiOperation(value = "根据贴士ID和用户token，返回收藏实体")
    @Auth(needToken = true)
    public HealthTipFavorite get(@PathVariable Integer healthTipId){
        Integer userId = ParamHolder.getCurrentUserId();
        LambdaQueryWrapper<HealthTipFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTipFavorite::getUserId, userId).eq(HealthTipFavorite::getHealthTipId, healthTipId);
        return favoriteService.getOne(queryWrapper);
    }

    @GetMapping("/favorites/healthTip/page/{pageNum}")
    @ApiOperation(value = "返回用户收藏的贴士，每页10个")
    @Auth(needToken = true)
    public List<HealthTipFavorite> gets(@PathVariable Integer pageNum){
        Integer userId = ParamHolder.getCurrentUserId();
        return favoriteService.listByUserId(userId, pageNum);
    }

    @PostMapping("/favorite/healthTip/{itemId}")
    @ApiOperation(value = "收藏某个贴士")
    @Auth(needToken = true)
    public String save(@PathVariable Integer itemId){
        Integer userId = ParamHolder.getCurrentUserId();
        HealthTipFavorite favorite = new HealthTipFavorite();
        favorite.setHealthTipId(itemId);
        favorite.setUserId(userId);
        if(!favoriteService.save(favorite)){
            throw new DefaultException("收藏贴士失败");
        }
        return "收藏贴士成功";
    }

    @DeleteMapping("/favorite/healthTip/{itemId}")
    @ApiOperation(value = "取消收藏某个贴士")
    @Auth(needToken = true)
    public String remove(@PathVariable Integer itemId){
        if(!favoriteService.removeByIdWithCheck(itemId)){
            throw new DefaultException("取消收藏贴士失败");
        }
        return "取消收藏贴士成功";
    }
}

