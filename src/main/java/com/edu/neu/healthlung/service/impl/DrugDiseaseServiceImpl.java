package com.edu.neu.healthlung.service.impl;

import com.edu.neu.healthlung.entity.DrugDisease;
import com.edu.neu.healthlung.entity.DrugFavorite;
import com.edu.neu.healthlung.entity.MedicareFavorite;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.mapper.DrugDiseaseMapper;
import com.edu.neu.healthlung.service.DrugDiseaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.service.DrugService;
import com.edu.neu.healthlung.service.MedicareService;
import com.edu.neu.healthlung.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Service
public class DrugDiseaseServiceImpl extends ServiceImpl<DrugDiseaseMapper, DrugDisease> implements DrugDiseaseService {

}
