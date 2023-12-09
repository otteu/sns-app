package dev.example.db.domain.alarm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlarmType {

    NEW_COMMENT_ON_POST("new comment"),
    NEW_LIKE_NO_POST("new like!")
    ;

    private final String alarmText;
}
