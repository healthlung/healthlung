package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.elasticsearchRepository.HealthTipRepository;
import com.edu.neu.healthlung.entity.HealthTip;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.mapper.HealthTipMapper;
import com.edu.neu.healthlung.service.HealthTipService;
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
import java.util.Map;
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
public class HealthTipServiceImpl extends ServiceImpl<HealthTipMapper, HealthTip> implements HealthTipService {

    @Resource
    private HealthTipRepository healthTipRepository;

    @Resource
    private Constrains constrains;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${healthlung.default-page-size}")
    private Integer defaultPageSize;


    /**
     * 重写这个方法，从redis中找
     * */
    @Override
    public HealthTip getById(Serializable id){
        return (HealthTip) redisTemplate.opsForHash().get(Constrains.HEALTHTIP_DICT_KEY, id);
    }

    public List<HealthTip> pageOrderByPublishDate(Integer pageNum){
        return pageFromRedis(pageNum, Constrains.HEALTHTIP_DATE_ZSET_KEY);
    }

    @Override
    public List<HealthTip> pageOrderByHot(Integer pageNum) {
        return pageFromRedis(pageNum, Constrains.HEALTHTIP_HOT_ZSET_KEY);
    }

    @Override
    public List<HealthTip> pageByModuleOrderByPublishDate(Integer pageNum, String module) {
        return pageFromRedisByModule(pageNum, Constrains.HEALTHTIP_DATE_ZSET_KEY, module);
    }

    @Override
    public List<HealthTip> pageByModuleOrderByHot(Integer pageNum, String module) {
        return pageFromRedisByModule(pageNum, Constrains.HEALTHTIP_HOT_ZSET_KEY, module);
    }

    @Override
    public List<HealthTip> searchOrderByPublishDate(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("publishDate").descending();
        return searchFromES(pageNum, queryStr, sort);
    }

    @Override
    public List<HealthTip> searchOrderByHot(Integer pageNum, String queryStr) {
        Sort sort = Sort.by("favoriteNumber").descending();
        return searchFromES(pageNum, queryStr, sort);
    }

    @Override
    public List<HealthTip> searchByModuleOrderByPublishDate(Integer pageNum, String module, String queryStr) {
        Sort sort = Sort.by("publishDate").descending();
        return searchByModuleFromES(pageNum, queryStr, module, sort);
    }

    @Override
    public List<HealthTip> searchByModuleOrderByHot(Integer pageNum, String module, String queryStr) {
        Sort sort = Sort.by("favoriteNumber").descending();
        return searchByModuleFromES(pageNum, queryStr, module, sort);
    }

    private List<HealthTip> searchFromES(Integer pageNum, String queryStr, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum - 1,defaultPageSize, sort);
        return healthTipRepository.findBySimpleContentOrTitle(queryStr, queryStr, pageable);
    }

    private List<HealthTip> searchByModuleFromES(Integer pageNum, String queryStr, String module, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum,defaultPageSize, sort);
        return healthTipRepository.findBySimpleContentOrTitleAndModule(queryStr, queryStr, module, pageable);
    }


    private List<HealthTip> pageFromRedisByModule(Integer pageNum, String key, String module){
        Map<String, String> moduleMap = constrains.getHealthTipModuleMap();
        String moduleKey = moduleMap.get(module);
        if(moduleKey == null){
            throw new BadDataException("给定模块不存在");
        }
        return pageFromRedis(pageNum, key + "_" + moduleKey);
    }

    private List<HealthTip> pageFromRedis(Integer pageNum, String key){
        if(pageNum < 1){
            throw new BadDataException("页码不合法");
        }
        Set<Object> integerSet = redisTemplate.opsForZSet().reverseRange( key,
                (pageNum - 1) * defaultPageSize,
                pageNum * defaultPageSize );

        List<HealthTip> result = new ArrayList<>();

        if (integerSet != null) {
            integerSet.forEach(item -> {
                result.add(this.getById((Serializable) item));
            });
        }
        return result;
    }

}
