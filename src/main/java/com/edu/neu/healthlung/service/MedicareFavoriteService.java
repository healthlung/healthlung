package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.MedicareFavorite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
public interface MedicareFavoriteService extends IService<MedicareFavorite> {

    boolean removeByIdWithCheck(Integer itemId);

    List<MedicareFavorite> listByUserId(Integer userId, Integer pageNum);
}
