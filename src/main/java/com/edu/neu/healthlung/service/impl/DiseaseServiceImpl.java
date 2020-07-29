package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.elasticsearchRepository.DiseaseRepository;
import com.edu.neu.healthlung.entity.Disease;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.mapper.DiseaseMapper;
import com.edu.neu.healthlung.service.DiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.util.Constrains;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private DiseaseRepository diseaseRepository;

    /**
     * 重写这个方法，从redis中找
     * */
    @Override
    public Disease getById(Serializable id){
        Disease disease =  (Disease)redisTemplate.opsForHash().get(Constrains.DISEASE_DICT_KEY, id);
        return disease;
    }

    @Override
    public List<Disease> pageOrderByHot(Integer pageNum) {
        return pageFromRedis(pageNum, Constrains.DISEASE_HOT_ZSET_KEY);
    }

    @Override
    public List<Disease> listByPinyinOrderByHot(String pinyin) {
        Set<Object> diseaseSet = redisTemplate.opsForZSet().range(Constrains.DISEASE_HOT_ZSET_KEY + "_" + pinyin, 0, -1);
        List<Disease> result = new ArrayList<>();

        if (diseaseSet != null) {
            diseaseSet.forEach(item -> {
                result.add((Disease) item);
            });
        }
        return result;
    }

    @Override
    public List<Disease> searchOrderByHot(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("favoriteNumber").descending();
        Pageable pageable = PageRequest.of(pageNum,defaultPageSize, sort);
        return diseaseRepository.findByNameOrSymptom(queryStr, queryStr, pageable);
    }

    private List<Disease> pageFromRedis(Integer pageNum, String key){
        if(pageNum < 1){
            throw new BadDataException("页码不合法");
        }
        Set<Object> integerSet = redisTemplate.opsForZSet().reverseRange( key,
                (pageNum - 1) * defaultPageSize,
                pageNum * defaultPageSize );

        List<Disease> result = new ArrayList<>();

        if (integerSet != null) {
            integerSet.forEach(item -> {
                result.add(this.getById((Serializable) item));
            });
        }
        return result;
    }
}
