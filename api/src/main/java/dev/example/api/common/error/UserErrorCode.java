package dev.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(400 , 1404 , "사용자를 찾을 수 없음."),
    INVALID_PASSWORD(400 , 1405 , "비밀번호가 다릅니다."),
    DUPICATED_USER_NAME(400 , 1406 , "이미 존재하는 회원 입니다."),
    USER_ENTITY_POINT(400 , 1407 , "회원정보가 없습니다."),
    INTERNAL_SERVER_ERROR(400 , 1408 , "케스팅 User 실패.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
