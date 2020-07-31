package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.*;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.DrugFavoriteMapper;
import com.edu.neu.healthlung.service.DrugFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.DrugService;
import com.edu.neu.healthlung.service.UserService;
import com.edu.neu.healthlung.util.Constrains;
import com.edu.neu.healthlung.util.ParamHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
public class DrugFavoriteServiceImpl extends ServiceImpl<DrugFavoriteMapper, DrugFavorite> implements DrugFavoriteService {

    @Resource
    DrugService drugService;

    @Resource
    UserService userService;

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;


    private boolean exist(DrugFavorite entity){
        LambdaQueryWrapper<DrugFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrugFavorite::getDrugId, entity.getDrugId()).eq(DrugFavorite::getUserId, entity.getUserId());
        return super.getOne(queryWrapper) != null;
    }

    @Override
    public boolean save(DrugFavorite entity) {

        Drug drug;

        if((drug = drugService.getById(entity.getDrugId())) == null){
            throw new BadDataException("给定药品不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        if(this.exist(entity)){
            throw new BadDataException("无法重复收藏");
        }

        drug.setFavoriteNumber(drug.getFavoriteNumber() + 1);

        if(!drugService.updateById(drug)){
            throw new DefaultException("收藏药品失败");
        }

        boolean result = super.save(entity);

        updateRedis(drug, -1);

        return result;
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {

        LambdaQueryWrapper<DrugFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrugFavorite::getDrugId, itemId).eq(DrugFavorite::getUserId, ParamHolder.getCurrentUserId());

        DrugFavorite dbItem = super.getOne(queryWrapper);

        if(dbItem == null){
            throw new NotFoundException("给定药品收藏不存在");
        }

        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的收藏");
        }

        Drug drug;

        if((drug = drugService.getById(dbItem.getDrugId())) == null){
            throw new BadDataException("给定药品不存在");
        }

        drug.setFavoriteNumber(drug.getFavoriteNumber() - 1);

        if(!drugService.updateById(drug)){
            throw new DefaultException("取消收藏药品失败");
        }

        boolean result = super.removeById(dbItem.getDrugFavoriteId());

        updateRedis(drug, -1);

        return result;
    }

    @Override
    public List<DrugFavorite> listByUserId(Integer userId, Integer pageNum) {

        LambdaQueryWrapper<DrugFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DrugFavorite::getUserId, ParamHolder.getCurrentUserId());

        List<DrugFavorite> favoriteList =  super.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();

        for(DrugFavorite item : favoriteList){
            Integer subId = item.getDrugId();
            Drug sub = drugService.getById(subId);
            item.setDrug(sub);
        }

        return favoriteList;
    }

    private void updateRedis(Drug drug, double scoreAdd){

        redisTemplate.opsForHash().put(Constrains.DRUG_DICT_KEY, drug.getDrugId(), drug);

        redisTemplate.opsForZSet().incrementScore(Constrains.DRUG_HOT_ZSET_KEY, drug.getDrugId(), scoreAdd);
    }

}
