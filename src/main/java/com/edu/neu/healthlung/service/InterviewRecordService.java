package com.edu.neu.healthlung.service;

import com.edu.neu.healthlung.entity.InterviewRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
public interface InterviewRecordService extends IService<InterviewRecord> {

    InterviewRecord getByIdWithCheck(Integer recordId);

    InterviewRecord generateRecord(MultipartFile video);
}
