package com.edu.neu.healthlung.startRunner;

import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.service.HealthTipService;
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
public class HealthTipInitializer implements RedisInitializer {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private HealthTipService healthTipService;


    @Resource
    private Constrains constrains;

    public void init(){
        List<HealthTip> healthTipList = healthTipService.list();
        initDict(healthTipList);
        initDateZSet(healthTipList);
        initHotZSet(healthTipList);
        initHotZSetByModule(healthTipList);
        initDateZSetByModule(healthTipList);
    }

    private void initDict(List<HealthTip> healthTipList) {
        BoundHashOperations<Object, Object, Object> boundHashOps = redisTemplate.boundHashOps(Constrains.HEALTHTIP_DICT_KEY);
        Map<String, HealthTip> healthTipMap = new HashMap<>();
        healthTipList.forEach(item-> healthTipMap.put(item.getHealthTipId().toString(), item));
        boundHashOps.putAll(healthTipMap);
    }

    private void initDateZSet(List<HealthTip> healthTipList){
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.HEALTHTIP_DATE_ZSET_KEY);
        healthTipList.forEach(item-> boundZSetOps.add(item.getHealthTipId(), item.getPublishDate().toEpochDay()));
    }

    private void initHotZSet(List<HealthTip> healthTipList){
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.HEALTHTIP_HOT_ZSET_KEY);
        healthTipList.forEach(item-> boundZSetOps.add(item.getHealthTipId(), item.getFavoriteNumber()));
    }

    private void initHotZSetByModule(List<HealthTip> healthTipList){
        Map<String, String> moduleMap = constrains.getHealthTipModuleMap();
        healthTipList.forEach(item -> {
            String moduleKey = moduleMap.get(item.getModule());
            String fullModuleKey = Constrains.HEALTHTIP_HOT_ZSET_KEY + "_" + moduleKey;
            redisTemplate.opsForZSet().add(fullModuleKey, item.getHealthTipId(), item.getFavoriteNumber());
        });
    }

    private void initDateZSetByModule(List<HealthTip> healthTipList){
        Map<String, String> moduleMap = constrains.getHealthTipModuleMap();
        healthTipList.forEach(item -> {
            String moduleKey = moduleMap.get(item.getModule());
            String fullModuleKey = Constrains.HEALTHTIP_DATE_ZSET_KEY + "_" + moduleKey;
            redisTemplate.opsForZSet().add(fullModuleKey, item.getHealthTipId(), item.getPublishDate().toEpochDay());
        });
    }
}
