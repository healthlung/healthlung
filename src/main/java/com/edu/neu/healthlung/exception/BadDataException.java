package com.edu.neu.healthlung.exception;

//数据有错，可以为空，也可以是外键约束没有之类的。
public class BadDataException extends RuntimeException {
    public BadDataException(String msg){
        super(msg);
    }
}
