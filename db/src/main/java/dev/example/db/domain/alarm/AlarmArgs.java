package dev.example.db.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private Long fromUserId;
    // 알람의 주체
    private Long targetId;

}
