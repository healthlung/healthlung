package com.edu.neu.healthlung.interpretor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 使用now()填充更新favoriteDate字段
        this.setFieldValByName("createDate", LocalDate.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}