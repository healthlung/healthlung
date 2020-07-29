package com.edu.neu.healthlung.elasticsearchRepository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.neu.healthlung.HealthlungApplicationTests;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.service.HealthTipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;


class HealthTipRepositoryTest extends HealthlungApplicationTests {

    @Resource
    private HealthTipRepository healthTipRepository;

    @Resource
    private HealthTipService healthTipService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveAll() {
        List<HealthTip> healthTipList = healthTipService.list();
        healthTipRepository.saveAll(healthTipList);
    }

    @Test
    void testSaveAll2() {

        int pageNum = 1;
        int defaultPageSize = 10;
        LambdaQueryWrapper<HealthTip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(HealthTip::getFavoriteNumber);
        List<HealthTip> healthTipList = healthTipService.page(new Page<>(pageNum, defaultPageSize), queryWrapper).getRecords();
        healthTipRepository.saveAll(healthTipList);
        Iterable<HealthTip> all = healthTipRepository.findAll();
        all.forEach(System.out::println);
    }
}