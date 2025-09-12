package com.yuguanzhang.lumi.common.exception.handler;

import com.yuguanzhang.lumi.common.exception.message.ExceptionMessage;
import com.yuguanzhang.lumi.common.exception.dto.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponseDto<?>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(ExceptionMessage.INVALID_CREDENTIALS.getStatus())
                .body(BaseResponseDto.fail(ExceptionMessage.INVALID_CREDENTIALS.getMessage(),
                        ExceptionMessage.INVALID_CREDENTIALS.getStatus().value()));
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponseDto<?>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(BaseResponseDto.fail(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto<Void>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(BaseResponseDto.fail(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponseDto.fail("서버 오류가 발생했습니다.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
