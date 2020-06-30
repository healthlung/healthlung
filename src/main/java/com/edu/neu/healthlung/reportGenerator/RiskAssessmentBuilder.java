package com.edu.neu.healthlung.reportGenerator;

import com.edu.neu.healthlung.entity.InterviewRecord;
import com.edu.neu.healthlung.entity.LungNode;

import java.util.*;

public class RiskAssessmentBuilder implements ReportBuilder {

    private static final String header = "根据检测结果，结合患者的吸烟历史。";
    private static final String sentence = "第%d个结节，%s。";
    // 结节类型，[最小值，最大值]，句子
    private Map<LungNode.LungNodeType, Map<List<Double>, Integer>> assessmentMap;
    private List<String> levelList;
    private List<String> summaryList;

    public RiskAssessmentBuilder(){
        levelList = new ArrayList<>();
        levelList.add("是肺癌超低危结节");
        levelList.add("是肺癌低危结节，建议1年后随访，发现生长则纳入高危结节处理，无生长行年度随访");
        levelList.add("是肺癌中危结节，应在3个月后进行随访观察其生长特性，发现结节生长纳入高危结节处理，无生长性则继续随访2年");
        levelList.add("是肺癌高危结节，应立刻由胸外科、肿瘤内科、呼吸科和影像医学科医师集体会诊");

        summaryList = new ArrayList<>();
        summaryList.add("整体来说，呈超低危，您的肺很健康。");
        summaryList.add("整体来说，呈低危，但也应多注意。");
        summaryList.add("整体来说，呈中危。");
        summaryList.add("整体来说，呈高危，请尽快就医。");



        assessmentMap = new HashMap<>();

        //实性
        Map<List<Double>, Integer>  map_1 = new HashMap<>();
        assessmentMap.put(LungNode.LungNodeType.实性, map_1);

        //实性低危
        List<Double> list_1 = new ArrayList<>();
        list_1.add(0.0);
        list_1.add(5.0);
        map_1.put(list_1, 1);

        //实性中危
        List<Double> list_2 = new ArrayList<>();
        list_2.add(5.0);
        list_2.add(15.0);
        map_1.put(list_2, 2);

        //实性高危
        List<Double> list_3 = new ArrayList<>();
        list_3.add(15.0);
        list_3.add(Double.MAX_VALUE);
        map_1.put(list_3, 3);

        //亚实性
        Map<List<Double>, Integer>  map_2 = new HashMap<>();
        assessmentMap.put(LungNode.LungNodeType.部分实性, map_2);

        //亚实性低危
        List<Double> list_4 = new ArrayList<>();
        list_4.add(0.0);
        list_4.add(8.0);
        map_2.put(list_4, 2);

        //亚实性高危
        List<Double> list_5 = new ArrayList<>();
        list_5.add(8.0);
        list_5.add(Double.MAX_VALUE);
        map_2.put(list_5, 3);

        //磨玻璃密度
        Map<List<Double>, Integer>  map_3 = new HashMap<>();
        assessmentMap.put(LungNode.LungNodeType.磨玻璃密度, map_3);


        //磨玻璃密度低危
        List<Double> list_6 = new ArrayList<>();
        list_6.add(0.0);
        list_6.add(5.0);
        map_3.put(list_6, 1);

        //磨玻璃密度低危
        List<Double> list_7 = new ArrayList<>();
        list_7.add(5.0);
        list_7.add(Double.MAX_VALUE);
        map_3.put(list_7, 2);
    }



    @Override
    public InterviewRecord build(InterviewRecord interviewRecord) {
        List<LungNode> lungNodeList = interviewRecord.getLungNodes();
        StringBuilder builder = new StringBuilder();
        builder.append(header);
        int total_max = 0;
        for(int i = 0; i < lungNodeList.size(); i ++) {
            LungNode currNode = lungNodeList.get(i);
            Map<List<Double>, Integer> currMap = assessmentMap.get(currNode.getLevel());
            for (Map.Entry<List<Double>, Integer> entry : currMap.entrySet()) {
                List<Double> minMaxList = entry.getKey();
                double min = minMaxList.get(0);
                double max = minMaxList.get(1);
                if (currNode.getLength() <= max && currNode.getLength() > min){
                    String currDescription = String.format(sentence, i + 1, levelList.get(entry.getValue()));
                    builder.append(currDescription);
                    if(entry.getValue() > total_max){
                        total_max = entry.getValue();
                    }
                    break;
                }
            }
        }
        interviewRecord.setRiskAssessment(builder.toString());
        interviewRecord.setRiskSummary(summaryList.get(total_max));
        return interviewRecord;
    }
}
