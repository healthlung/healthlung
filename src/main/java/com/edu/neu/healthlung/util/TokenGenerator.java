package com.edu.neu.healthlung.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {


    public String generate(Integer userId, String sign){
        JWTCreator.Builder builder = JWT.create();

        //token第一部分：userId
        builder.withClaim("userId", userId.toString());

        //token第二部分：时间戳
        //暂时不加时间戳，因为要多用户同时登陆，保证token不失效
//        builder.withClaim("loginTime", System.currentTimeMillis());

//        //token第二部分：projectId
//        if(projectId != null){
//            builder.withClaim("projectId", projectId.toString());
//        }
//
//        //token第三部分：role
//        if(role != null){
//            builder.withClaim("role", role);
//        }

        return builder.sign(Algorithm.HMAC256(sign));
    }
}
