package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.Disease;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.neu.healthlung.entity.HealthTip;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
public interface DiseaseService extends IService<Disease> {
    List<Disease> pageOrderByHot(Integer pageNum);
    //todo: 这个list不能全返回
    List<Disease> listByPinyinOrderByHot(String pinyin);

    List<Disease> searchOrderByHot(Integer pageNum, String queryStr);
}
