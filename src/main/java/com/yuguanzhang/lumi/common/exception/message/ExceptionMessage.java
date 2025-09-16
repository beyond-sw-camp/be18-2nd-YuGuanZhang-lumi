package com.yuguanzhang.lumi.common.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus; // <-- 반드시 import

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    INVALID_CREDENTIALS("아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    DELETED_ACCOUNT("삭제된 계정입니다.", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER_ERROR("서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;
}

