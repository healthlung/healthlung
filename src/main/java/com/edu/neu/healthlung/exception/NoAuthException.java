package com.edu.neu.healthlung.exception;

public class NoAuthException extends RuntimeException {
    public NoAuthException(String msg){
        super(msg);
    }
}
