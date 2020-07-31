package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.elasticsearchRepository.DrugRepository;
import com.edu.neu.healthlung.entity.Drug;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.mapper.DrugMapper;
import com.edu.neu.healthlung.service.DrugService;
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
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug> implements DrugService {

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private DrugRepository drugRepository;


    /**
     * 重写这个方法，从redis中找
     * */
    @Override
    public Drug getById(Serializable id){
        return (Drug) redisTemplate.opsForHash().get(Constrains.DRUG_DICT_KEY, id);
    }

    @Override
    public List<Drug> pageOrderByHot(Integer pageNum) {
        return pageFromRedis(pageNum, Constrains.DRUG_HOT_ZSET_KEY);
    }

    @Override
    public List<Drug> searchOrderByHot(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("favoriteNumber").descending();
        Pageable pageable = PageRequest.of(pageNum - 1,defaultPageSize, sort);
        return drugRepository.findByNameOrIndication(queryStr, queryStr, pageable);
    }

    private List<Drug> pageFromRedis(Integer pageNum, String key){
        if(pageNum < 1){
            throw new BadDataException("页码不合法");
        }
        Set<Object> integerSet = redisTemplate.opsForZSet().reverseRange( key,
                (pageNum - 1) * defaultPageSize,
                pageNum * defaultPageSize );

        List<Drug> result = new ArrayList<>();

        if (integerSet != null) {
            integerSet.forEach(item -> {
                result.add(this.getById((Serializable) item));
            });
        }
        return result;
    }
}
