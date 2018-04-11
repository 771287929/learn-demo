package com.zhouwei.exception;

/**
 * 
 * @author 20004388
 *
 */
public class BusinessException extends RuntimeException{
    public BusinessException() {
        super();
    }

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(Throwable t) {
        super(t);
    }

    public BusinessException(String msg, Throwable t) {
        super(msg, t);
    }
}
