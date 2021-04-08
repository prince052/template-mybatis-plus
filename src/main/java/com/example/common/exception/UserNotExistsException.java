package com.example.common.exception;

// 用户不存在
public class UserNotExistsException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super();
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}