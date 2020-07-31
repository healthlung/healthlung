package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.Medicare;
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
public interface MedicareService extends IService<Medicare> {

    List<Medicare> pageOrderByHot(Integer pageNum);

    List<Medicare> pageOrderByPublishDate(Integer pageNum);

    List<Medicare> searchOrderByHot(Integer pageNum, String queryStr);

    List<Medicare> searchOrderByPublishDate(Integer pageNum, String queryStr);


}
