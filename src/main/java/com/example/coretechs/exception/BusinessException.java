package com.example.coretechs.exception;

import com.example.coretechs.common.ErrorCode;
import lombok.Data;

public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(final int code, final String msg, String description) {
        super(msg);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
