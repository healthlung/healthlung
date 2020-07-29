package com.edu.neu.healthlung.startRunner;

import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.service.DiseaseService;
import com.edu.neu.healthlung.service.DrugService;
import com.edu.neu.healthlung.util.Constrains;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DrugInitializer implements RedisInitializer {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private DrugService drugService;

    @Override
    public void init() {
        List<Drug> drugList = drugService.list();
        initDict(drugList);
        initHotZSet(drugList);
    }

    private void initDict(List<Drug> drugList) {
        BoundHashOperations<Object, Object, Object> boundHashOps = redisTemplate.boundHashOps(Constrains.DRUG_DICT_KEY);
        Map<Integer, Drug> drugMap = new HashMap<>();
        drugList.forEach(item-> drugMap.put(item.getDrugId(), item));
        boundHashOps.putAll(drugMap);
    }

    private void initHotZSet(List<Drug> drugList){
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.DRUG_HOT_ZSET_KEY);
        drugList.forEach(item-> boundZSetOps.add(item.getDrugId(), item.getFavoriteNumber()));
    }
}
