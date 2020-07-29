package com.edu.neu.healthlung.startRunner;

import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.service.DiseaseService;
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
public class DiseaseInitializer implements RedisInitializer {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private DiseaseService diseaseService;

    @Override
    public void init() {
        List<Disease> diseaseList = diseaseService.list();
        initDict(diseaseList);
        initHotZSet(diseaseList);
        initHotZSetByPinyin(diseaseList);
    }

    private void initDict(List<Disease> diseaseList) {
        BoundHashOperations<Object, Object, Object> boundHashOps = redisTemplate.boundHashOps(Constrains.DISEASE_DICT_KEY);
        Map<Integer, Disease> diseaseMap = new HashMap<>();
        diseaseList.forEach(item-> diseaseMap.put(item.getDiseaseId(), item));
        boundHashOps.putAll(diseaseMap);
    }

    private void initHotZSet(List<Disease> diseaseList){
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.DISEASE_HOT_ZSET_KEY);
        diseaseList.forEach(item-> boundZSetOps.add(item.getDiseaseId(), item.getFavoriteNumber()));
    }

    private void initHotZSetByPinyin(List<Disease> diseaseList){
        diseaseList.forEach(item -> {
            String pinyinKey = item.getPinyin();
            String fullKey = Constrains.DISEASE_HOT_ZSET_KEY + "_" + pinyinKey;
            redisTemplate.opsForZSet().add(fullKey, item.getDiseaseId(), item.getFavoriteNumber());
        });
    }
}
