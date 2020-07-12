package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.healthlung.entity.Doctor;
import com.edu.neu.healthlung.entity.HealthRecord;
import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.entity.LungNode;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.InterviewRecordMapper;
import com.edu.neu.healthlung.reportGenerator.ReportBuilder;
import com.edu.neu.healthlung.service.DoctorService;
import com.edu.neu.healthlung.service.HealthRecordService;
import com.edu.neu.healthlung.service.InterviewRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.util.ParamHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Service
public class InterviewRecordServiceImpl extends ServiceImpl<InterviewRecordMapper, InterviewRecord> implements InterviewRecordService {

    @Value("${healthlung.default-province}")
    private String defaultProvince;

    @Value("${healthlung.default-city}")
    private String defaultCity;

    @Value("${healthlung.video-temp-dir}")
    private String videoTempDir;

    @Resource
    DoctorService doctorService;

    @Resource
    HealthRecordService healthRecordService;

    @Resource
    ReportBuilder reportBuilder;

    @Override
    public InterviewRecord getByIdWithCheck(Integer recordId) {
        InterviewRecord interviewRecord = super.getById(recordId);
        if(interviewRecord == null){
            throw new NotFoundException("问诊记录不存在");
        }
        if(!interviewRecord.getUserId().equals(ParamHolder.getCurrentUserId())){
            throw new NoAuthException("无权查看他人的问诊记录");
        }
        return interviewRecord;
    }

    @Override
    public InterviewRecord generateRecord(MultipartFile video) {

        // 先填充这个人的ID
        InterviewRecord interviewRecord = new InterviewRecord();
        interviewRecord.setUserId(ParamHolder.getCurrentUserId());

        //上传视频
//        String videoName = video.getOriginalFilename();
//        File dest = new File(videoTempDir + videoName);
//        //todo: 视频处理
//        try {
//            video.transferTo(dest);
//        } catch (IOException e) {
//            System.out.println(e);
//        }

        //todo: 网络处理

        // 先模拟一个网络处理后的结果
        List<LungNode> lungNodes = new ArrayList<>();
        LungNode l1 = new LungNode();
        l1.setLevel(LungNode.LungNodeType.实性);
        l1.setImageUrl("https://i.loli.net/2020/06/23/WYajMlqL32C1FSU.png");
        l1.setLength(8.0);
        l1.setWidth(8.0);
        lungNodes.add(l1);
        LungNode l2 = new LungNode();
        l2.setLevel(LungNode.LungNodeType.部分实性);
        l2.setLength(9.0);
        l2.setWidth(9.0);
        l2.setImageUrl("https://i.loli.net/2020/06/23/WYajMlqL32C1FSU.png");
        lungNodes.add(l2);
        interviewRecord.setLungNodes(lungNodes);

        // 从数据库里找医生
        LambdaQueryWrapper<HealthRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(HealthRecord::getUserId, ParamHolder.getCurrentUserId());
        HealthRecord healthRecord = healthRecordService.getOne(queryWrapper);
        String city = healthRecord.getCity();
        String province = healthRecord.getProvince();
        if(city == null || province == null){
            city = defaultCity;
            province = defaultProvince;
        }

        LambdaQueryWrapper<Doctor> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Doctor::getCity, city);
        List<Doctor> cityDoctor = doctorService.list(queryWrapper1);

        LambdaQueryWrapper<Doctor> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Doctor::getProvince, province).eq(Doctor::getProvinceRecommend, true);
        List<Doctor> provinceDoctor = doctorService.list(queryWrapper2);

        LambdaQueryWrapper<Doctor> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(Doctor::getCountryRecommend, true);
        List<Doctor> countryDoctor = doctorService.list(queryWrapper3);

        List<List<Doctor>> doctors = new ArrayList<>();
        doctors.add(cityDoctor);
        doctors.add(provinceDoctor);
        doctors.add(countryDoctor);
        interviewRecord.setCandidateDoctors(doctors);

        //调用builder填充报告
        interviewRecord = reportBuilder.build(interviewRecord);

        //存入数据库
        super.save(interviewRecord);

        //返回，主键应该回填了
        return interviewRecord;
    }

    @Override
    public Integer generateRecord() {
        return this.generateRecord(null).getInterviewRecordId();
    }
}
