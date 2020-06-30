package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.entity.LungNode;

import java.util.List;

public class ImageDescriptionBuilder implements ReportBuilder{

    private static final String header = "在患者的肺部CT图像中共检测出肺结节数量为%d个。";
    private static final String sentence = "第%d个为%s结节，如图%d，大小为%.2fMM*%.2fMM。";

    private String generateImageDescription(List<LungNode> lungNodeList){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(header, lungNodeList.size()));
        for(int i = 0; i < lungNodeList.size(); i ++){
            LungNode currNode = lungNodeList.get(i);
            String currDescription = String.format(sentence, i + 1, currNode.getLevel().toString(), i + 1, currNode.getLength(), currNode.getWidth());
            builder.append(currDescription);
        }
        return builder.toString();
    }

    @Override
    public InterviewRecord build(InterviewRecord interviewRecord) {
        interviewRecord.setImageDescription(generateImageDescription(interviewRecord.getLungNodes()));
        return interviewRecord;
    }
}
