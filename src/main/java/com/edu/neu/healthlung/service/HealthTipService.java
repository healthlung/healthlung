package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.HealthTip;
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
public interface HealthTipService extends IService<HealthTip> {

    List<HealthTip> pageOrderByPublishDate(Integer pageNum);

    List<HealthTip> pageOrderByHot(Integer pageNum);

    List<HealthTip> pageByModuleOrderByPublishDate(Integer pageNum, String module);

    List<HealthTip> pageByModuleOrderByHot(Integer pageNum, String module);

    List<HealthTip> searchOrderByPublishDate(Integer pageNum, String queryStr);

    List<HealthTip> searchOrderByHot(Integer pageNum, String queryStr);

    List<HealthTip> searchByModuleOrderByPublishDate(Integer pageNum, String module, String queryStr);

    List<HealthTip> searchByModuleOrderByHot(Integer pageNum, String module, String queryStr);

}
