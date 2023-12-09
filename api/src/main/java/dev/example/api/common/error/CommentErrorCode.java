package dev.example.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCodeIfs {

    COMMENT_WRITE_FAIL(400, 4404, "댓들 작성 저장 실패")
//    USER_NOT_FOUND(400 , 1404 , "사용자를 찾을 수 없음."),
//    INVALID_PASSWORD(400 , 1405 , "비밀번호가 다릅니다."),
//    DUPICATED_USER_NAME(400 , 1406 , "이미 존재하는 회원 입니다."),
//    USER_ENTITY_POINT(400 , 1407 , "회원정보가 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
