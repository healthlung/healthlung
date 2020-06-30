package com.edu.neu.healthlung.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edu.neu.healthlung.entity.HealthRecord;
import com.edu.neu.healthlung.entity.User;
import com.edu.neu.healthlung.exception.BadDataException;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.mapper.UserMapper;
import com.edu.neu.healthlung.service.HealthRecordService;
import com.edu.neu.healthlung.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.neu.healthlung.util.Encoder;
import com.edu.neu.healthlung.util.TokenGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@Transactional
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private HealthRecordService healthRecordService;

    @Override
    public Boolean existByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user =  userMapper.selectOne(queryWrapper);
        return user != null && user.getUserId() != null && user.getEmail() != null;
    }

    @Override
    public Boolean register(User user) {

        //密码进入数据库前加密
        String password = user.getPassword();
        String encryptPassword = Encoder.string2Sha1(password);

        user.setPassword(encryptPassword);

        if(userMapper.insert(user) == 0){
            throw new DefaultException("注册失败");
        }

        //注册的时候直接创建一份空的健康档案
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setUserId(user.getUserId());
        return healthRecordService.save(healthRecord);
    }

    @Override
    public String login(String email, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user =  userMapper.selectOne(queryWrapper);

        if(user == null || user.getUserId() == null || user.getPassword() == null){
            throw new NotFoundException("用户不存在");
        }

        if(!Encoder.string2Sha1(password).equals(user.getPassword())){
            throw new BadDataException("密码错误");
        }

        String token = TokenGenerator.generate(user.getUserId());
        return token;
    }
}
