package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.HealthTip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DiseaseRepository extends ElasticsearchRepository<Disease, Integer>{

    List<Disease> findByNameOrSymptom(String queryStr, String queryStr_, Pageable pageable);
}
