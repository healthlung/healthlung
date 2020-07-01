package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.HealthTipFavorite;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.HealthTipFavoriteMapper;
import com.edu.neu.healthlung.service.HealthTipFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.HealthTipService;
import com.edu.neu.healthlung.service.UserService;
import com.edu.neu.healthlung.util.ParamHolder;
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
public class HealthTipFavoriteServiceImpl extends ServiceImpl<HealthTipFavoriteMapper, HealthTipFavorite> implements HealthTipFavoriteService {
    @Resource
    HealthTipService healthTipService;

    @Resource
    UserService userService;

    @Override
    public boolean save(HealthTipFavorite entity) {

        HealthTip healthTip;

        if((healthTip = healthTipService.getById(entity.getHealthTipId())) == null){
            throw new BadDataException("给定贴士不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        healthTip.setFavoriteNumber(healthTip.getFavoriteNumber() + 1);

        if(!healthTipService.save(healthTip)){
            throw new DefaultException("点赞失败");
        }

        return super.save(entity);
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {

        HealthTipFavorite dbItem = super.getById(itemId);

        if(dbItem == null){
            throw new NotFoundException("给定贴士收藏不存在");
        }
        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的贴士收藏");
        }

        HealthTip healthTip;

        if((healthTip = healthTipService.getById(dbItem.getHealthTipId())) == null){
            throw new BadDataException("给定贴士不存在");
        }

        healthTip.setFavoriteNumber(healthTip.getFavoriteNumber() - 1);

        if(!healthTipService.save(healthTip)){
            throw new DefaultException("取消点赞失败");
        }

        return super.removeById(itemId);
    }
}
