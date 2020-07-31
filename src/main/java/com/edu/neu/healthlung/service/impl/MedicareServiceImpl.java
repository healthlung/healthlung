package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.elasticsearchRepository.MedicareRepository;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.entity.Medicare;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.mapper.MedicareMapper;
import com.edu.neu.healthlung.service.MedicareService;
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
public class MedicareServiceImpl extends ServiceImpl<MedicareMapper, Medicare> implements MedicareService {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;

    @Resource
    private MedicareRepository medicareRepository;

    /**
     * 重写这个方法，从redis中找
     * */
    @Override
    public Medicare getById(Serializable id){
        return (Medicare) redisTemplate.opsForHash().get(Constrains.MEDICARE_DICT_KEY, id);
    }

    @Override
    public List<Medicare> pageOrderByHot(Integer pageNum) {
        return pageFromRedis(pageNum, Constrains.MEDICARE_HOT_ZSET_KEY);
    }

    @Override
    public List<Medicare> pageOrderByPublishDate(Integer pageNum) {
        return pageFromRedis(pageNum, Constrains.MEDICARE_DATE_ZSET_KEY);
    }

    @Override
    public List<Medicare> searchOrderByHot(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("favoriteNumber").descending();
        List<Medicare> medicareList = searchFromES(pageNum, queryStr, sort);
        System.out.println();
        return medicareList;
    }

    @Override
    public List<Medicare> searchOrderByPublishDate(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("publishDate").descending();
        List<Medicare> medicareList = searchFromES(pageNum, queryStr, sort);
        System.out.println();
        return medicareList;
    }


    private List<Medicare> searchFromES(Integer pageNum, String queryStr, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum - 1, defaultPageSize, sort);
        return medicareRepository.findByTitleOrCity(queryStr, queryStr, pageable);
    }

    private List<Medicare> pageFromRedis(Integer pageNum, String key){
        if(pageNum < 1){
            throw new BadDataException("页码不合法");
        }
        Set<Object> integerSet = redisTemplate.opsForZSet().reverseRange( key,
                (pageNum - 1) * defaultPageSize,
                pageNum * defaultPageSize );

        List<Medicare> result = new ArrayList<>();

        if (integerSet != null) {
            integerSet.forEach(item -> {
                result.add(this.getById((Serializable) item));
            });
        }
        return result;
    }
}
