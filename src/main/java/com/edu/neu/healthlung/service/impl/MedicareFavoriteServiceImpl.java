package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.*;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.MedicareFavoriteMapper;
import com.edu.neu.healthlung.service.MedicareFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.MedicareService;
import com.edu.neu.healthlung.service.UserService;
import com.edu.neu.healthlung.util.ParamHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Service
public class MedicareFavoriteServiceImpl extends ServiceImpl<MedicareFavoriteMapper, MedicareFavorite> implements MedicareFavoriteService {

    @Resource
    MedicareService medicareService;

    @Resource
    UserService userService;

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    private boolean exist(MedicareFavorite entity){
        LambdaQueryWrapper<MedicareFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicareFavorite::getMedicareId, entity.getMedicareId()).eq(MedicareFavorite::getUserId, entity.getUserId());
        return super.getOne(queryWrapper) != null;
    }

    @Override
    public boolean save(MedicareFavorite entity) {

        Medicare medicare;

        if((medicare = medicareService.getById(entity.getMedicareId())) == null){
            throw new BadDataException("给定医保不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        if(this.exist(entity)){
            throw new BadDataException("无法重复收藏");
        }

        medicare.setFavoriteNumber(medicare.getFavoriteNumber() + 1);

        if(!medicareService.save(medicare)){
            throw new DefaultException("收藏医保失败");
        }

        return super.save(entity);
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {

        MedicareFavorite dbItem = super.getById(itemId);

        if(dbItem == null){
            throw new NotFoundException("给定医保收藏不存在");
        }

        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的医保收藏");
        }

        Medicare medicare;

        if((medicare = medicareService.getById(dbItem.getMedicareId())) == null){
            throw new BadDataException("给定医保不存在");
        }

        medicare.setFavoriteNumber(medicare.getFavoriteNumber() - 1);

        if(!medicareService.save(medicare)){
            throw new DefaultException("收藏医保失败");
        }

        return super.removeById(itemId);
    }

    @Override
    public List<MedicareFavorite> listByUserId(Integer userId, Integer pageNum) {

        LambdaQueryWrapper<MedicareFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MedicareFavorite::getUserId, ParamHolder.getCurrentUserId());

        List<MedicareFavorite> favoriteList = super.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();

        for(MedicareFavorite item : favoriteList){
            Integer subId = item.getMedicareId();
            Medicare sub = medicareService.getById(subId);
            item.setMedicare(sub);
        }
        return null;
    }
}
