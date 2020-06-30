package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.DiseaseFavorite;
import com.edu.neu.healthlung.entity.DrugFavorite;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.DiseaseFavoriteMapper;
import com.edu.neu.healthlung.mapper.DiseaseMapper;
import com.edu.neu.healthlung.service.DiseaseFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.DiseaseService;
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
public class DiseaseFavoriteServiceImpl extends ServiceImpl<DiseaseFavoriteMapper, DiseaseFavorite> implements DiseaseFavoriteService {

    @Resource
    DiseaseService diseaseService;

    @Resource
    UserService userService;

    @Override
    public boolean save(DiseaseFavorite diseaseFavorite) {

        if(diseaseService.getById(diseaseFavorite.getDiseaseId()) == null){
            throw new BadDataException("给定疾病不存在");
        }

        if(userService.getById(diseaseFavorite.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }
        return super.save(diseaseFavorite);
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {
        DiseaseFavorite dbItem = super.getById(itemId);
        if(dbItem == null){
            throw new NotFoundException("给定收藏不存在");
        }
        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的收藏");
        }
        return super.removeById(itemId);
    }
}
