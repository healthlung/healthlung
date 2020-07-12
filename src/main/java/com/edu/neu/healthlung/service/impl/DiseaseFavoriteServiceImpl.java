package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.entity.*;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.DiseaseFavoriteMapper;
import com.edu.neu.healthlung.mapper.DiseaseMapper;
import com.edu.neu.healthlung.service.DiseaseFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.DiseaseService;
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
public class DiseaseFavoriteServiceImpl extends ServiceImpl<DiseaseFavoriteMapper, DiseaseFavorite> implements DiseaseFavoriteService {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    DiseaseService diseaseService;

    @Resource
    UserService userService;

    private boolean exist(DiseaseFavorite entity){
        LambdaQueryWrapper<DiseaseFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DiseaseFavorite::getDiseaseId, entity.getDiseaseId()).eq(DiseaseFavorite::getUserId, entity.getUserId());
        return super.getOne(queryWrapper) != null;
    }

    @Override
    public boolean save(DiseaseFavorite diseaseFavorite) {

        Disease disease;

        if((disease = diseaseService.getById(diseaseFavorite.getDiseaseId())) == null){
            throw new BadDataException("给定疾病不存在");
        }

        if(userService.getById(diseaseFavorite.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }

        if(this.exist(diseaseFavorite)){
            throw new BadDataException("无法重复收藏");
        }


        disease.setFavoriteNumber(disease.getFavoriteNumber() + 1);

        if(!diseaseService.updateById(disease)){
            throw new DefaultException("收藏疾病失败");
        }

        return super.save(diseaseFavorite);
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {

        LambdaQueryWrapper<DiseaseFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DiseaseFavorite::getDiseaseId, itemId).eq(DiseaseFavorite::getUserId, ParamHolder.getCurrentUserId());

        DiseaseFavorite dbItem = super.getOne(queryWrapper);

        if(dbItem == null){
            throw new NotFoundException("给定疾病收藏不存在");
        }

        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的疾病收藏");
        }

        Disease disease;

        if((disease = diseaseService.getById(dbItem.getDiseaseId())) == null){
            throw new BadDataException("给定贴士不存在");
        }

        disease.setFavoriteNumber(disease.getFavoriteNumber() - 1);

        if(!diseaseService.updateById(disease)){
            throw new DefaultException("取消收藏失败");
        }

        return super.removeById(dbItem.getDiseaseFavoriteId());
    }

    @Override
    public List<DiseaseFavorite> listByUserId(Integer userId, Integer pageNum) {

        LambdaQueryWrapper<DiseaseFavorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DiseaseFavorite::getUserId, ParamHolder.getCurrentUserId());

        List<DiseaseFavorite> diseaseFavorites = super.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();

        for(DiseaseFavorite item : diseaseFavorites){
            Integer subId = item.getDiseaseId();
            Disease sub = diseaseService.getById(subId);
            item.setDisease(sub);
        }

        return diseaseFavorites;
    }
}
