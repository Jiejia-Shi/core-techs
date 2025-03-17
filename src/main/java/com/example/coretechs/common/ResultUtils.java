package com.example.coretechs.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("success", data, 0, "");
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode, description);
    }

    public static <T> BaseResponse<T> error(int code, String msg, String description) {
        return new BaseResponse<>(msg, null, code, description);
    }

}
