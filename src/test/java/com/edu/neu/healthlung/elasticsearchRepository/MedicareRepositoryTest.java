package com.edu.neu.healthlung.elasticsearchRepository;

import com.edu.neu.healthlung.HealthlungApplicationTests;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.entity.Medicare;
import com.edu.neu.healthlung.service.DrugService;
import com.edu.neu.healthlung.service.MedicareService;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicareRepositoryTest extends HealthlungApplicationTests {

    @Resource
    MedicareService medicareService;

    @Resource
    MedicareRepository medicareRepository;


    @Test
    void testSearch3() {

        Sort sort = Sort.by("favoriteNumber").descending();
        Pageable pageable = PageRequest.of(1, 10, sort);
        //利用构造器建造NativeSearchQuery  他可以添加条件,过滤,等复杂操作
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", "秦皇岛"))
                .withPageable(pageable)
                .build();
        //elasticsearchRestTemplate.search方法参数一,本机查询的构造,参数二index的类,可选参数三再次声明库名(可以多个)
        Page<Medicare> search = medicareRepository.search(query);
        search.getContent().forEach(searchHit-> System.out.println(searchHit.getTitle()));
    }

    @Test
    void testSearch() {

        List<Medicare> medicareList = medicareService.searchOrderByHot(0, "秦皇岛");
        medicareList.forEach(item -> {
            System.out.println(item.getTitle());
        });
        System.out.println(medicareList);
    }
    @Test
    void testSearch2() {
        Sort sort = Sort.by("favoriteNumber").descending();
        Pageable pageable = PageRequest.of(1, 10, sort);
        List<Medicare> medicareList = medicareRepository.findByContent("秦皇岛", pageable);
        medicareList.forEach(item -> {
            System.out.println(item.getTitle());
        });
        System.out.println(medicareList);
    }


    @Test
    void testSaveAll() {
        List<Medicare> medicareList = medicareService.list();
        medicareRepository.saveAll(medicareList);
        System.out.println();
    }

}