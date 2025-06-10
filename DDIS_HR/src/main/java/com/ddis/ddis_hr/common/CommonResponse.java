package com.ddis.ddis_hr.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(true, message, data);
    }

    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<>(true, message, null);
    }

    public static <T> CommonResponse<T> failure(String message) {
        return new CommonResponse<>(false, message, null);
    }
}
