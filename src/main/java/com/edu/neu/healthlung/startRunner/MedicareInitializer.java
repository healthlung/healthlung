package com.edu.neu.healthlung.startRunner;

import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.Medicare;
import com.edu.neu.healthlung.service.MedicareService;
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
public class MedicareInitializer implements RedisInitializer {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    MedicareService medicareService;

    @Override
    public void init() {
        List<Medicare> medicareList = medicareService.list();
        initDict(medicareList);
        initHotZSet(medicareList);
        initDateZSet(medicareList);
    }

    private void initDateZSet(List<Medicare> medicareList) {
        BoundHashOperations<Object, Object, Object> boundHashOps = redisTemplate.boundHashOps(Constrains.MEDICARE_DICT_KEY);
        Map<Integer, Medicare> medicareMap = new HashMap<>();
        medicareList.forEach(item-> medicareMap.put(item.getMedicareId(), item));
        boundHashOps.putAll(medicareMap);
    }

    private void initHotZSet(List<Medicare> medicareList) {
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.MEDICARE_DATE_ZSET_KEY);
        medicareList.forEach(item-> boundZSetOps.add(item.getMedicareId(), item.getPublishDate().toEpochDay()));
    }

    private void initDict(List<Medicare> medicareList) {
        BoundZSetOperations<Object, Object> boundZSetOps = redisTemplate.boundZSetOps(Constrains.MEDICARE_HOT_ZSET_KEY);
        medicareList.forEach(item-> boundZSetOps.add(item.getMedicareId(), item.getFavoriteNumber()));
    }
}
