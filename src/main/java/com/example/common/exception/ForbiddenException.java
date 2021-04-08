package com.example.common.exception;

// 权限不足
public class ForbiddenException extends BusinessException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }
}