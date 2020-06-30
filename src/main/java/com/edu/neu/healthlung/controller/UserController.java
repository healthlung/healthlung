package com.edu.neu.healthlung.controller;


import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.User;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import com.edu.neu.healthlung.exception.NotFoundException;
import com.edu.neu.healthlung.service.UserService;
import com.edu.neu.healthlung.util.ParamHolder;
import com.edu.neu.healthlung.validate.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author t0ugh
 * @since 2020-06-28
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{userId}")
    @ApiOperation(value = "根据用户ID返回用户信息")
    //todo: 不要返回password
    public User getUser(@PathVariable Integer userId){
        User user =  userService.getById(userId);
        if(user == null){
            throw new NotFoundException("用户不存在");
        }
        return user;
    }

    @GetMapping("/me")
    @ApiOperation(value = "获取当前用户的信息")
    @Auth(needToken = true, sameUser = true)
    public User getMe(){
        User user =  userService.getById(ParamHolder.getCurrentUserId());
        if(user == null){
            throw new NotFoundException("用户不存在");
        }
        return user;
    }


    @ApiOperation(value = "检测邮箱是否被使用了，使用了返回false")
    @GetMapping("/user/email/{email}")
    public Boolean existByEmail(@PathVariable String email){
        return !userService.existByEmail(email);
    }

    @PutMapping("/user")
    @Auth(needToken = true, sameUser = true)
    @ApiOperation(value = "用户修改自己的基本信息")
    public String updateUser(@Validated(value = UpdateGroup.class) @RequestBody User user){
        user.setUserId(ParamHolder.getCurrentUserId());
        if(!userService.updateById(user)){
            throw new DefaultException("更新失败");
        }
        return "更新成功";
    }


    @PostMapping("/user")
    @ApiOperation(value = "注册")
    public String register(@RequestBody User user){
        if(!userService.register(user)){
            throw new DefaultException("注册失败");
        }
        return "注册成功";
    }

}

