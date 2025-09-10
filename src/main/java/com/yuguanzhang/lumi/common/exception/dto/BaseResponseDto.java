package com.yuguanzhang.lumi.common.exception.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BaseResponseDto<T> {
    private final boolean success;  // true / false
    private final int status;       // HTTP status
    private final String message;   // 응답 메시지
    private final T data;

    // 성공 응답
    public static <T> BaseResponseDto<T> success(T data, String message, int status) {
        return new BaseResponseDto<>(true, status, message, data);
    }

    // 실패 응답
    public static <T> BaseResponseDto<T> fail(String message, int status) {
        return new BaseResponseDto<>(false, status, message, null);
    }
}
