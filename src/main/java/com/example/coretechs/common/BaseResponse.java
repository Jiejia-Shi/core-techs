package com.example.coretechs.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    private String description;

    public BaseResponse(String msg, T data, int code, String description) {
        this.msg = msg;
        this.data = data;
        this.code = code;
        this.description = description;
    }

    public BaseResponse(ErrorCode errorCode, String description) {
        this(errorCode.getMessage(), null, errorCode.getCode(), description);
    }
}
