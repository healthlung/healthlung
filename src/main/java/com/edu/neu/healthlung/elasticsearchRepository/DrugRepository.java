package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.entity.HealthTip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DrugRepository extends ElasticsearchRepository<Drug, Integer> {
    List<Drug> findByNameOrIndication(String queryStr, String queryStr_, Pageable pageable);
}
