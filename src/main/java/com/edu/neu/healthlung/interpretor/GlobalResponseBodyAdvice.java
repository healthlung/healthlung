package com.edu.neu.healthlung.interpretor;

import com.edu.neu.healthlung.util.DTO;
import com.edu.neu.healthlung.util.DTOFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.File;

@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static String[] ignores = new String[]{
            //过滤swagger相关的请求的接口，不然swagger会提示base-url被拦截
            "/swagger-resources",
            "/v2/api-docs"
    };

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(aClass) || StringHttpMessageConverter.class.isAssignableFrom(aClass);
    }

    /**
     * 判断url是否需要拦截
     * @param uri
     * @return
     */
    private boolean ignoring(String uri) {
        for (String string : ignores) {
            if (uri.contains(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        //判断url是否需要拦截
        if (this.ignoring(serverHttpRequest.getURI().toString())) {
            return body;
        }
        //如果返回为空
        if (body == null) {
            return DTOFactory.okDTO();
        }

        //如果返回一个DTO，则直接处理
        if (body instanceof DTO) {
            return body;

        //返回文件直接处理
        } else if (body instanceof File) {
            return body;

        //返回String则放到msg上
        } else if(body instanceof String) {
            return DTOFactory.okDTO((String) body, null);

        //返回其他对象就ok
        }else{
            return DTOFactory.okDTO(body);
        }
    }
}
