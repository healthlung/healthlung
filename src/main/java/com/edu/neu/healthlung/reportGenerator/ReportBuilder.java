package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.InterviewRecord;

public interface ReportBuilder {
    InterviewRecord build(InterviewRecord interviewRecord);
}
