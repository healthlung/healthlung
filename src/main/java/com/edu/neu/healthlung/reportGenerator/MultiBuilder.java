package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.InterviewRecord;

import java.util.List;

public class MultiBuilder implements ReportBuilder {

    private List<ReportBuilder> builderList;

    public List<ReportBuilder> getBuilderList() {
        return builderList;
    }

    public void setBuilderList(List<ReportBuilder> builderList) {
        this.builderList = builderList;
    }

    @Override
    public InterviewRecord build(InterviewRecord interviewRecord) {
        for (ReportBuilder currBuilder:builderList) {
            currBuilder.build(interviewRecord);
        }
        return interviewRecord;
    }
}
