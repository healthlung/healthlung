package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.entity.Medicare;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MedicareRepository extends ElasticsearchRepository<Medicare, Integer> {

    List<Medicare> findByTitleOrCity(String title, String city, Pageable pageable);

    List<Medicare> findByCity(String city, Pageable pageable);

    List<Medicare> findByContent(String content, Pageable pageable);

    List<Medicare> findByMedicareId(Integer medicareId, Pageable pageable);

}
