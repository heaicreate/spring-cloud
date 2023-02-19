package com.example.springCloud.model;

import com.example.springCloud.exception.ErrorCode;
import lombok.Data;

@Data
public class Response<T> {
    protected int status;
    /**
     * 成功时：code =  0
     */
    protected int code;

    protected String message;

    protected T data;

    public Response(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Response(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Response(int status, int code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
