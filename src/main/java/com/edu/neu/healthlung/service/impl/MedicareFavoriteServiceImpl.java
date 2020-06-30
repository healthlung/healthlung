package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.MedicareFavorite;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.MedicareFavoriteMapper;
import com.edu.neu.healthlung.service.MedicareFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.MedicareService;
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
public class MedicareFavoriteServiceImpl extends ServiceImpl<MedicareFavoriteMapper, MedicareFavorite> implements MedicareFavoriteService {

    @Resource
    MedicareService medicareService;

    @Resource
    UserService userService;

    @Override
    public boolean save(MedicareFavorite entity) {

        if(medicareService.getById(entity.getMedicareId()) == null){
            throw new BadDataException("给定医保不存在");
        }

        if(userService.getById(entity.getUserId()) == null){
            throw new BadDataException("给定用户不存在");
        }
        return super.save(entity);
    }

    @Override
    public boolean removeByIdWithCheck(Integer itemId) {
        MedicareFavorite dbItem = super.getById(itemId);
        if(dbItem == null){
            throw new NotFoundException("给定收藏不存在");
        }
        if(!dbItem.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权删除其他用户的收藏");
        }
        return super.removeById(itemId);
    }
}
