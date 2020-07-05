package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.HealthTipFavorite;
import com.edu.neu.healthlung.entity.HealthTipLike;
import com.edu.neu.healthlung.entity.MedicareFavorite;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.HealthTipFavoriteService;
import com.edu.neu.healthlung.service.HealthTipLikeService;
import com.edu.neu.healthlung.service.MedicareFavoriteService;
import com.edu.neu.healthlung.util.ParamHolder;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@RestController
public class HealthTipLikeController {
    @Resource
    private HealthTipLikeService likeService;

    @GetMapping("/like/healthTip/{healthTipId}")
    @ApiOperation(value = "根据贴士ID和用户token，返回点赞实体")
    @Auth(needToken = true)
    public HealthTipLike get(@PathVariable Integer healthTipId){
        Integer userId = ParamHolder.getCurrentUserId();
        LambdaQueryWrapper<HealthTipLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTipLike::getUserId, userId).eq(HealthTipLike::getHealthTipId, healthTipId);
        return likeService.getOne(queryWrapper);
    }

    @PostMapping("/like/healthTip/{itemId}")
    @ApiOperation(value = "点赞某个贴士")
    @Auth(needToken = true)
    public String save(@PathVariable Integer itemId){
        Integer userId = ParamHolder.getCurrentUserId();
        HealthTipLike like = new HealthTipLike();
        like.setHealthTipId(itemId);
        like.setUserId(userId);
        if(!likeService.save(like)){
            throw new DefaultException("点赞贴士失败");
        }
        return "点赞贴士成功";
    }
}

