package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.entity.HealthTip;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface HealthTipRepository extends ElasticsearchRepository<HealthTip, Integer> {

    List<HealthTip> findBySimpleContentOrTitle(String queryStr, String queryStr_, Pageable pageable);

    List<HealthTip> findBySimpleContentOrTitleAndModule(String simpleContent, String title, String module, Pageable pageable);

}
