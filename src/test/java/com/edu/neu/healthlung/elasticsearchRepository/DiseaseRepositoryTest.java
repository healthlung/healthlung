package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.HealthlungApplicationTests;
import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.service.DiseaseService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseRepositoryTest extends HealthlungApplicationTests {

    @Resource
    DiseaseService diseaseService;

    @Resource
    DiseaseRepository diseaseRepository;

    @Test
    void testSaveAll() {
        List<Disease> diseaseList = diseaseService.list();
        diseaseRepository.saveAll(diseaseList);
    }


}