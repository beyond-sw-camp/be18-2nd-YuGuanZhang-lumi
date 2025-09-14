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
    UNAUTHORIZED_CHAT_DELETE("본인이 작성한 메시지만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN),

    FILE_NOT_FOUND("해당 파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FILE_NOT_READABLE("파일을 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),
    FILE_PATH_INVALID("허용되지 않은 파일 경로입니다.", HttpStatus.FORBIDDEN),
    FILE_INFO_ERROR("파일 정보 조회를 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_ALREADY_DELETED("이미 삭제된 파일입니다.", HttpStatus.GONE);

    private final String message;

    private final HttpStatus status;
}
