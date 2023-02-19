package com.example.springCloud.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    protected int status;

    protected int code;

    public BaseException(int status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }

    public BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
