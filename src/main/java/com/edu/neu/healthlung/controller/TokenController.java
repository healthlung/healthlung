package com.edu.neu.healthlung.controller;

import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.entity.User;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.service.UserService;
import com.edu.neu.healthlung.util.DTO;
import com.edu.neu.healthlung.util.DTOFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TokenController {
    @Resource
    private UserService userService;


    @PostMapping("/token")
    @ApiOperation(value = "登陆")
    public DTO login(@RequestBody User user){
        String token = userService.login(user.getEmail(), user.getPassword());
        if(token == null || token.equals("")){
            throw new DefaultException("登陆失败");
        }else {
            return DTOFactory.okDTO("登陆成功", token);
        }
    }

    @Auth(needToken = true)
    @DeleteMapping("/token")
    @ApiOperation(value = "注销")
    public DTO logout(){
        return DTOFactory.okDTO("注销成功", null);
    }

}
