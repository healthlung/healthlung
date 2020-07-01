package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.DiseaseFavorite;
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
public interface DiseaseFavoriteService extends IService<DiseaseFavorite> {

    boolean removeByIdWithCheck(Integer itemId);

    List<DiseaseFavorite> listByUserId(Integer userId, Integer pageNum);
}
