package com.edu.neu.healthlung.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.HealthTipComment;
import com.edu.neu.healthlung.entity.HealthTipFavorite;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.HealthTipCommentService;
import com.edu.neu.healthlung.service.HealthTipFavoriteService;
import com.edu.neu.healthlung.util.ParamHolder;
import com.edu.neu.healthlung.validate.InsertGroup;
import com.edu.neu.healthlung.validate.UpdateGroup;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
public class HealthTipCommentController {
    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private HealthTipCommentService commentService;

    @PostMapping("/comment/healthTip/{itemId}")
    @ApiOperation(value = "评论某个贴士")
    @Auth(needToken = true)
    public String save(@PathVariable Integer itemId, @Validated(value = InsertGroup.class) @RequestBody HealthTipComment comment){
        Integer userId = ParamHolder.getCurrentUserId();
        comment.setUserId(userId);
        comment.setHealthTipId(itemId);
        if(!commentService.save(comment)){
            throw new DefaultException("评论贴士失败");
        }
        return "评论贴士成功";
    }

    @GetMapping("/comments/healthTip/{healthTipId}/page/{pageNum}/")
    @ApiOperation(value = "返回贴士对应的评论，每页10个")
    public List<HealthTipComment> gets(@PathVariable Integer pageNum, @PathVariable Integer healthTipId){
        LambdaQueryWrapper<HealthTipComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTipComment::getHealthTipCommentId, healthTipId);
        return commentService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
    }

}

