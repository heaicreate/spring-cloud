package com.example.springCloud.exception;

public class ServiceException extends BaseException {

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

    public ServiceException(int status, int code, String message) {
        super(status, code, message);
    }

    public ServiceException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ServiceException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, errorCode, cause);
    }
}
