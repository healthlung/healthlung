package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.HealthTipComment;
import com.edu.neu.healthlung.entity.HealthTipLike;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.mapper.HealthTipCommentMapper;
import com.edu.neu.healthlung.service.HealthTipCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.HealthTipService;
import com.edu.neu.healthlung.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Service
public class HealthTipCommentServiceImpl extends ServiceImpl<HealthTipCommentMapper, HealthTipComment> implements HealthTipCommentService {
    @Resource
    HealthTipService healthTipService;

    @Resource
    UserService userService;

    @Override
    public boolean save(HealthTipComment entity) {

        HealthTip healthTip;

        if((healthTip = healthTipService.getById(entity.getHealthTipId())) == null){
            throw new BadDataException("给定贴士不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        // 评论数 + 1
        healthTip.setCommentNumber(healthTip.getCommentNumber() + 1);
        if(!healthTipService.save(healthTip)){
            throw new DefaultException("点赞失败");
        }
        return super.save(entity);
    }
}
