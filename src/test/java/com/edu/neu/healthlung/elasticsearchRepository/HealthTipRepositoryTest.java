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
    void testSearch(){
        List<HealthTip> healthTipList = healthTipService.searchOrderByPublishDate(1, "太极");
        healthTipList.forEach(item -> {
            System.out.println(item.getTitle());
        });
    }

    @Test
    void testSaveAll() {
        List<HealthTip> healthTipList = healthTipService.list();
        healthTipRepository.saveAll(healthTipList);
    }

}