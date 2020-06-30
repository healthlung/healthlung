package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.DrugFavorite;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
public interface DrugFavoriteService extends IService<DrugFavorite> {

    boolean removeByIdWithCheck(Integer itemId);
}
