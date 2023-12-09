package dev.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PostErrorCode implements ErrorCodeIfs {

    POST_NOT_FOUND(400 , 2404 , "포스트를 찾을 수 없음."),
    INVALID_PERMISSION(401 , 2405 , "유효하지 않습니다."),
//    DUPICATED_USER_NAME(400 , 2406 , "이미 존재하는 회원 입니다."),
//    USER_ENTITY_POINT(400 , 2407 , "회원정보가 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
