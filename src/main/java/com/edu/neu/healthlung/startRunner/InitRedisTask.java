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

@Component
@Order(1)
public class InitRedisTask implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(InitRedisTask.class);

    @Resource
    HealthTipInitializer healthTipInitializer;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        clearDB();
        healthTipInitializer.init();
        logger.info("Redis DB healthTip inited");
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
