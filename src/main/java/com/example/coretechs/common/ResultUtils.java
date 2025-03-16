package com.example.coretechs.common;

public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>("success", data, 0);
    }


}
