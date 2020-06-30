package com.edu.neu.healthlung.interpretor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.edu.neu.healthlung.annotation.Auth;
import com.edu.neu.healthlung.exception.DefaultException;
import com.edu.neu.healthlung.exception.NoAuthException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 鉴权拦截器
 * */
public class AuthenticationInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        // 从 http 请求头中取出 token
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //通过反射拿到这个方法的元数据
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        //如果这个方法被Auth修饰，则进入判断，否则直接返回true放行
        if (method.isAnnotationPresent(Auth.class)) {
            Auth auth =method.getAnnotation(Auth.class);

            //如果需要登陆，则继续验证
            if(auth.needToken()){

                //没有token直接报错
                if (token == null) {
                    throw new NoAuthException("无token，请登录");
                }

                //取userId时无法解码直接报错
                String userId;
                try {
                    userId = JWT.decode(token).getClaim("userId").asString();
                } catch (JWTDecodeException j) {
                    throw new DefaultException("无法解析userId");
                }

                if(userId == null){
                    throw new NoAuthException("无法解析userId");
                }

                //将取得的userId存到requestHeader
                request.setAttribute("userId", userId);

                //如果需要是同一个user则执行验证
                //取不到这个参数也不用验证
                if(auth.sameUser()){
                    String paramUserId = request.getParameter("userId");
                    if(paramUserId != null){
                        if(!userId.equals(paramUserId)){
                            throw new NoAuthException("您无权操作其他用户");
                        }
                    }
                }


             //如果不需要登陆，则直接返回true
            }else{
                return true;
            }
        }
        return true;
    }

}
