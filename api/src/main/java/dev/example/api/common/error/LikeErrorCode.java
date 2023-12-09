package dev.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeErrorCode implements ErrorCodeIfs {



    LIKE_NOT_FOUND(400 , 3404 , "좋아요가 없음."),
    ALERADY_LIKED(401 , 3405 , "이미 좋아요 했습니다."),
    LIKE_FAIL(401 , 3406 , "좋아요 실패.")

//    INVALID_PERMISSION(401 , 2405 , "유효하지 않습니다."),
//    DUPICATED_USER_NAME(400 , 2406 , "이미 존재하는 회원 입니다."),
//    USER_ENTITY_POINT(400 , 2407 , "회원정보가 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
