package dev.example.api.domain.alarm.model;

import dev.example.db.domain.alarm.AlarmArgs;
import dev.example.db.domain.alarm.enums.AlarmType;
import dev.example.db.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Alarm {
    private Long id;
    private UserEntity user;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
