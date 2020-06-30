package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.Doctor;
import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.entity.LungNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // 构建一个肺结节数组
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

        //构建医生数组
        Doctor doctor_1 = new Doctor();
        doctor_1.setDoctorId(674);
        doctor_1.setName("曹捍波");
        doctor_1.setDepartment("肺部结节门诊");
        doctor_1.setHospitalName("舟山医院");
        List<Doctor> cityDoctors = new ArrayList<>();
        cityDoctors.add(doctor_1);

        Doctor doctor_2 = new Doctor();
        doctor_2.setDoctorId(650);
        doctor_2.setName("陈良良");
        doctor_2.setDepartment("主任医师");
        doctor_2.setHospitalName("浙江省中医院");
        List<Doctor> provinceDoctors = new ArrayList<>();
        provinceDoctors.add(doctor_2);

        Doctor doctor_3 = new Doctor();
        doctor_3.setDoctorId(410);
        doctor_3.setName("黄雯霞");
        doctor_3.setDepartment("中西医结合科");
        doctor_3.setHospitalName("复旦大学附属肿瘤医院徐汇院区");
        List<Doctor> countryDoctors = new ArrayList<>();
        countryDoctors.add(doctor_3);

        List<List<Doctor>> doctors = new ArrayList<>();
        doctors.add(cityDoctors);
        doctors.add(provinceDoctors);
        doctors.add(countryDoctors);

        InterviewRecord interviewRecord = new InterviewRecord();
        interviewRecord.setLungNodes(lungNodes);
        interviewRecord.setCandidateDoctors(doctors);

        // 构建构造器
        ReportBuilder[] reportBuilders = {
                new ImageBuilder(),
                new ImageDescriptionBuilder(),
                new RiskAssessmentBuilder(),
                new HospitalBuilder()
        };
        List<ReportBuilder> reportBuilderList = new ArrayList<>();
        Collections.addAll(reportBuilderList, reportBuilders);
        ReportBuilder builder = new MultiBuilder();
        ((MultiBuilder) builder).setBuilderList(reportBuilderList);
        interviewRecord = builder.build(interviewRecord);
        System.out.println(interviewRecord.getImageDescription());
        System.out.println(interviewRecord.getRiskAssessment());
        System.out.println(interviewRecord.getRiskSummary());
        System.out.println(interviewRecord.getHospitalRecommend());

    }
}
