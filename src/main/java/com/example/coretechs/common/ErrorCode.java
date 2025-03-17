package com.example.coretechs.common;

public enum ErrorCode {

    SUCCESS(0, "success"),
    PARAMS_ERROR(40000, "params error"),
    PARAMS_NULL(40001, "params empty"),
    NOT_LOGIN(40100, "not login"),
    NO_AUTH(40100, "no authority"),
    SYSTEM_ERROR(50000, "system error");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
