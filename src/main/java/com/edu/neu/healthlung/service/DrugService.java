package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.Drug;
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
public interface DrugService extends IService<Drug> {

    List<Drug> pageOrderByHot(Integer pageNum);

    List<Drug> searchOrderByHot(Integer pageNum, String queryStr);
}
