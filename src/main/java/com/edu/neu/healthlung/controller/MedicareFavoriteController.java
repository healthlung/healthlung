package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.DiseaseFavorite;
import com.edu.neu.healthlung.entity.DrugFavorite;
import com.edu.neu.healthlung.entity.Medicare;
import com.edu.neu.healthlung.entity.MedicareFavorite;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.DrugFavoriteService;
import com.edu.neu.healthlung.service.MedicareFavoriteService;
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
public class MedicareFavoriteController {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private MedicareFavoriteService favoriteService;

    @GetMapping("/favorites/medicare/page/{pageNum}")
    @ApiOperation(value = "返回用户收藏的医保政策，每页10个")
    @Auth(needToken = true)
    public List<MedicareFavorite> gets(@PathVariable Integer pageNum){
        Integer userId = ParamHolder.getCurrentUserId();
        return favoriteService.listByUserId(userId, pageNum);
//        LambdaQueryWrapper<MedicareFavorite> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(MedicareFavorite::getUserId, ParamHolder.getCurrentUserId());
//        return favoriteService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

    @GetMapping("/favorite/medicare/{medicareId}")
    @ApiOperation(value = "根据医保ID和用户token，返回收藏实体")
    @Auth(needToken = true)
    public MedicareFavorite get(@PathVariable Integer medicareId){
        Integer userId = ParamHolder.getCurrentUserId();
        LambdaQueryWrapper<MedicareFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicareFavorite::getUserId, userId).eq(MedicareFavorite::getMedicareId, medicareId);
        return favoriteService.getOne(queryWrapper);
    }

    @PostMapping("/favorite/medicare/{itemId}")
    @ApiOperation(value = "收藏某个医保政策")
    @Auth(needToken = true)
    public String save(@PathVariable Integer itemId){
        Integer userId = ParamHolder.getCurrentUserId();
        MedicareFavorite favorite = new MedicareFavorite();
        favorite.setMedicareId(itemId);
        favorite.setUserId(userId);
        if(!favoriteService.save(favorite)){
            throw new DefaultException("收藏医保政策失败");
        }
        return "收藏医保政策成功";
    }

    @DeleteMapping("/favorite/medicare/{medicareId}")
    @ApiOperation(value = "取消收藏某个医保政策")
    @Auth(needToken = true)
    public String remove(@PathVariable Integer medicareId){
        if(!favoriteService.removeByMedicareId(medicareId)){
            throw new DefaultException("取消收藏医保政策失败");
        }
        return "取消收藏医保政策成功";
    }

}

