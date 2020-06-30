package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.entity.LungNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageBuilder implements ReportBuilder {

    @Override
    public InterviewRecord build(InterviewRecord interviewRecord) {
        List<LungNode> lungNodes = interviewRecord.getLungNodes();
        List<String> imageUrls = new ArrayList<>();
        for(LungNode lungNode: lungNodes){
            imageUrls.add(lungNode.getImageUrl());
        }
        interviewRecord.setCtImageUrls(imageUrls);
        return interviewRecord;
    }
}
