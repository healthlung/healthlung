package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.Doctor;
import com.edu.neu.healthlung.entity.InterviewRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HospitalBuilder implements ReportBuilder {

    private static final String header = "根据您的所在地，为您推荐以下医院和医生：";
    private static final String sentence = "%s，%s，%s。";

    @Override
    public InterviewRecord build(InterviewRecord interviewRecord) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        List<List<Doctor>> doctors = interviewRecord.getCandidateDoctors();
        List<Doctor> cityDoctors = doctors.get(0);
        List<Doctor> provinceDoctors = doctors.get(1);
        List<Doctor> countryDoctors = doctors.get(2);
        if(cityDoctors.size() != 0){
            Doctor cityDoctor = cityDoctors.get(0);
            interviewRecord.setCityRecommendedDoctorId(cityDoctor.getDoctorId());
            stringBuilder.append(String.format(sentence, cityDoctor.getHospitalName(), cityDoctor.getDepartment(), cityDoctor.getName()));
        }
        for(Doctor doctor: provinceDoctors){
            if(!doctor.getDoctorId().equals(interviewRecord.getCityRecommendedDoctorId())){
                interviewRecord.setProvinceRecommendedDoctorId(doctor.getDoctorId());
                stringBuilder.append(String.format(sentence, doctor.getHospitalName(), doctor.getDepartment(), doctor.getName()));
                break;
            }
        }
        for(Doctor doctor: countryDoctors){
            if(!doctor.getDoctorId().equals(interviewRecord.getCityRecommendedDoctorId())
                    && !doctor.getDoctorId().equals(interviewRecord.getProvinceRecommendedDoctorId())){
                interviewRecord.setCountryRecommendedDoctorId(doctor.getDoctorId());
                stringBuilder.append(String.format(sentence, doctor.getHospitalName(), doctor.getDepartment(), doctor.getName()));
                break;
            }
        }
        interviewRecord.setHospitalRecommend(stringBuilder.toString());
        return interviewRecord;
    }
}
