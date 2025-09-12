package com.yuguanzhang.lumi.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ResponseDto<T> {
    private final boolean success;

    private final int statusCode;

    private String message; // 제거 아니라서 final 안 고침

    private final T data;

    // 단일 데이터
    public ResponseDto(HttpStatus status, T data) {
        this.success = status.is2xxSuccessful();
        this.statusCode = status.value();
        this.message = status.getReasonPhrase();
        this.data = data;
    }

    // null 반환
    public ResponseDto(HttpStatus status) {
        this.success = status.is2xxSuccessful();
        this.statusCode = status.value();
        this.message = status.getReasonPhrase();
        this.data = null;
    }
}
