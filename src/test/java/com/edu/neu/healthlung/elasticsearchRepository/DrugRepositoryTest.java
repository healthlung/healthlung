package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.HealthlungApplicationTests;
import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.service.DrugService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DrugRepositoryTest extends HealthlungApplicationTests {

    @Resource
    DrugService drugService;

    @Resource
    DrugRepository drugRepository;


    @Test
    void testSaveAll() {
        List<Drug> drugList = drugService.list();
        drugRepository.saveAll(drugList);
    }

}