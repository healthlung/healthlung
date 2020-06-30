package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.HealthTipLike;
import com.edu.neu.healthlung.exception.BadDataException;
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

    @Override
    public boolean save(HealthTipLike entity) {

        if(healthTipService.getById(entity.getHealthTipId()) == null){
            throw new BadDataException("给定贴士不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }
        return super.save(entity);
    }
}
