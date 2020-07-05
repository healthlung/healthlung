package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.HealthTipComment;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
public interface HealthTipCommentService extends IService<HealthTipComment> {

    List<HealthTipComment> listWithUser(Integer pageNum, Integer healthTipId);
}
