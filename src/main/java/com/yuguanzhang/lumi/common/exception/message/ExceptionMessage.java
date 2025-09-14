package com.yuguanzhang.lumi.common.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {
    INVALID_CREDENTIALS("아이디 또는 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROOM_NOT_FOUND("채팅방 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ROOM_USER_NOT_FOUND("해당 채팅방에서 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CHAT_NOT_FOUND("채팅 메시지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_CHAT_DELETE("본인이 작성한 메시지만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);

    private final String message;

    private final HttpStatus status;
}
