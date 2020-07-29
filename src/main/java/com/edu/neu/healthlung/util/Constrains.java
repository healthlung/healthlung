package com.edu.neu.healthlung.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Constrains {
    private Map<String, String> healthTipModuleMap;

    public static final String HEALTHTIP_DICT_KEY = "healthtip_dict";

    public static final String HEALTHTIP_DATE_ZSET_KEY = "healthtip_date_zset";

    public static final String HEALTHTIP_HOT_ZSET_KEY = "healthtip_hot_zset";

    public static final String DISEASE_DICT_KEY = "disease_dict";

    public static final String DISEASE_HOT_ZSET_KEY = "disease_hot_zset";

    public static final String DRUG_DICT_KEY = "drug_dict";

    public static final String DRUG_HOT_ZSET_KEY = "drug_hot_zset";

    public Constrains(){
        healthTipModuleMap = new HashMap<>();
        healthTipModuleMap.put("健康食谱", "food");
        healthTipModuleMap.put("健康科普", "knowledge");
        healthTipModuleMap.put("养肺建议", "suggest");
        healthTipModuleMap.put("养生运动", "sport");
        healthTipModuleMap.put("慢性病管理", "manage");
    }

    public Map<String, String> getHealthTipModuleMap() {
        return healthTipModuleMap;
    }

}
