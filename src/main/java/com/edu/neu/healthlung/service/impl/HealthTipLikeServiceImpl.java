package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.HealthTipFavorite;
import com.edu.neu.healthlung.entity.HealthTipLike;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.mapper.HealthTipLikeMapper;
import com.edu.neu.healthlung.service.HealthTipLikeService;
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
public class HealthTipLikeServiceImpl extends ServiceImpl<HealthTipLikeMapper, HealthTipLike> implements HealthTipLikeService {
    @Resource
    HealthTipService healthTipService;

    @Resource
    UserService userService;

    private boolean exist(HealthTipLike entity){
        LambdaQueryWrapper<HealthTipLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthTipLike::getHealthTipId, entity.getHealthTipId()).eq(HealthTipLike::getUserId, entity.getUserId());
        return super.getOne(queryWrapper) != null;
    }

    @Override
    public boolean save(HealthTipLike entity) {

        HealthTip healthTip;

        if((healthTip = healthTipService.getById(entity.getHealthTipId())) == null){
            throw new BadDataException("给定贴士不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        if(this.exist(entity)){
            throw new BadDataException("无法重复点赞");
        }

        healthTip.setLikeNumber(healthTip.getLikeNumber() + 1);

        if(!healthTipService.save(healthTip)){
            throw new DefaultException("点赞失败");
        }

        return super.save(entity);
    }
}
