package com.example.common.exception;

// 用户未认证
public class UnAuthorizeException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public UnAuthorizeException() {
        super();
    }

    public UnAuthorizeException(String message) {
        super(message);
    }
}