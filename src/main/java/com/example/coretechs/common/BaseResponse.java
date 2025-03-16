package com.example.coretechs.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private String msg;

    private T data;

    public BaseResponse(String msg, T data, int code) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }
}
