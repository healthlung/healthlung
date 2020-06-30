package com.edu.neu.healthlung;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.edu.neu.healthlung.mapper"}, annotationClass = Mapper.class)
@SpringBootApplication
public class HealthlungApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthlungApplication.class, args);
    }

}
