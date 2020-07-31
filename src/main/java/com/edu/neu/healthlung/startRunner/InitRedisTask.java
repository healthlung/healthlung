package com.edu.neu.healthlung.startRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//todo: 加回来
//@Component
//@Order(1)
public class InitRedisTask implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(InitRedisTask.class);

    @Resource
    HealthTipInitializer healthTipInitializer;

    @Resource
    DiseaseInitializer diseaseInitializer;

    @Resource
    DrugInitializer drugInitializer;

    @Resource
    MedicareInitializer medicareInitializer;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clearDB();
        healthTipInitializer.init();
        logger.info("Redis DB healthTip inited");
        diseaseInitializer.init();
        logger.info("Redis DB disease inited");
        drugInitializer.init();
        logger.info("Redis DB drug inited");
        medicareInitializer.init();
        logger.info("Redis DB medicare inited");
    }

    private void clearDB(){
        logger.info("Clearing Redis DB");
        redisTemplate.execute((RedisCallback<Void>) con -> {
            con.flushAll();
            logger.info("Redis DB cleared");
            return null;
        });
    }
}
