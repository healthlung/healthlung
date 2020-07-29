package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.HealthlungApplicationTests;
import com.edu.neu.healthlung.elasticsearchRepository.HealthTipRepository;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.service.HealthTipService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HealthTipServiceImplTest extends HealthlungApplicationTests {

    @Resource
    private HealthTipService healthTipService;

    @Resource
    private HealthTipRepository healthTipRepository;

    @Test
    void findBySimpleContent() {
        Sort sort = Sort.by("publishDate").ascending();
        Pageable pageable = PageRequest.of(0,10, sort);
        List<HealthTip> healthTipList = healthTipRepository.findBySimpleContentOrTitle("太极瑜伽", "太极瑜伽", pageable);
        healthTipList.forEach(System.out::println);
    }
}