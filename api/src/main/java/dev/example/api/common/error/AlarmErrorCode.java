package dev.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlarmErrorCode implements ErrorCodeIfs {

    USER_NOT_FOUND(400 , 5404 , "알람이 없습니다."),
//    INVALID_PASSWORD(400 , 1405 , "비밀번호가 다릅니다."),
//    DUPICATED_USER_NAME(400 , 1406 , "이미 존재하는 회원 입니다."),
//    USER_ENTITY_POINT(400 , 1407 , "회원정보가 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
