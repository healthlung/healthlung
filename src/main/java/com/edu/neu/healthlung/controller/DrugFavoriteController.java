package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.DiseaseFavorite;
import com.edu.neu.healthlung.entity.DrugFavorite;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.DiseaseFavoriteService;
import com.edu.neu.healthlung.service.DrugFavoriteService;
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
public class DrugFavoriteController {
    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private DrugFavoriteService favoriteService;

    @GetMapping("/favorites/drug/page/{pageNum}")
    @ApiOperation(value = "返回用户收藏的药品，每页10个")
    @Auth(needToken = true)
    public List<DrugFavorite> gets(@PathVariable Integer pageNum){
        Integer userId = ParamHolder.getCurrentUserId();
        return favoriteService.listByUserId(userId, pageNum);
    }

    @GetMapping("/favorite/drug/{drugId}")
    @ApiOperation(value = "根据药品ID和用户token，返回收藏实体")
    @Auth(needToken = true)
    public DrugFavorite get(@PathVariable Integer drugId){
        Integer userId = ParamHolder.getCurrentUserId();
        LambdaQueryWrapper<DrugFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrugFavorite::getUserId, userId).eq(DrugFavorite::getDrugId, drugId);
        return favoriteService.getOne(queryWrapper);
    }

    @PostMapping("/favorite/drug/{drugId}")
    @ApiOperation(value = "收藏某个药品")
    @Auth(needToken = true)
    public String save(@PathVariable Integer drugId){
        Integer userId = ParamHolder.getCurrentUserId();
        DrugFavorite favorite = new DrugFavorite();
        favorite.setDrugId(drugId);
        favorite.setUserId(userId);
        if(!favoriteService.save(favorite)){
            throw new DefaultException("收藏药品失败");
        }
        return "收藏药品成功";
    }

    @DeleteMapping("/favorite/drug/{itemId}")
    @ApiOperation(value = "取消收藏某个药品")
    @Auth(needToken = true)
    public String remove(@PathVariable Integer itemId){
        if(!favoriteService.removeByIdWithCheck(itemId)){
            throw new DefaultException("取消收藏药品失败");
        }
        return "取消收藏药品成功";
    }

}

