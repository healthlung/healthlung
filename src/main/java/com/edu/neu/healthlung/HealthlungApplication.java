package com.edu.neu.healthlung;

import com.edu.neu.healthlung.util.StringListTypeHandler;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo: 打乱数据库的显示顺序，不然healthTips第一篇全是太极拳
//todo: 健康百科的整体搜索功能
@MapperScan(basePackages = {"com.edu.neu.healthlung.mapper"}, annotationClass = Mapper.class)
@SpringBootApplication
public class HealthlungApplication {

    private static final Logger logger = LoggerFactory.getLogger(StringListTypeHandler.class);

    public static void main(String[] args) {

        logger.info("start");

        SpringApplication.run(HealthlungApplication.class, args);
    }

}
