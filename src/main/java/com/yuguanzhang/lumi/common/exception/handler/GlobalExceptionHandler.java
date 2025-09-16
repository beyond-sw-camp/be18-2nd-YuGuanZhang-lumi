package com.yuguanzhang.lumi.common.exception.handler;

import com.yuguanzhang.lumi.common.dto.BaseResponseDto;
import com.yuguanzhang.lumi.common.exception.message.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 탈퇴한 사용자
    @ExceptionHandler({DisabledException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<BaseResponseDto<String>> handleDeletedAccount(RuntimeException ex) {
        return ResponseEntity.status(ExceptionMessage.DELETED_ACCOUNT.getStatus())
                             .body(BaseResponseDto.of(ExceptionMessage.DELETED_ACCOUNT.getStatus(),
                                                      ExceptionMessage.DELETED_ACCOUNT.getMessage()));
    }

    // 로그인 비밀번호 틀림
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponseDto<String>> handleBadCredentials(
            BadCredentialsException ex) {
        return ResponseEntity.status(ExceptionMessage.INVALID_CREDENTIALS.getStatus())
                             .body(BaseResponseDto.of(
                                     ExceptionMessage.INVALID_CREDENTIALS.getStatus(),
                                     ExceptionMessage.INVALID_CREDENTIALS.getMessage()));
    }

    // 존재하지 않는 사용자
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponseDto<String>> handleUsernameNotFound(
            UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(BaseResponseDto.of(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    // 잘못된 요청
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto<String>> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(BaseResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // 서버 내부 오류
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDto<String>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(BaseResponseDto.of(
                                     ExceptionMessage.INTERNAL_SERVER_ERROR.getStatus(),
                                     ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage()));
    }

}
