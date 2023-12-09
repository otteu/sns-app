package dev.example.api.domain.alarm.converter;

import dev.example.api.common.annotation.Converter;
import dev.example.api.domain.alarm.model.Alarm;
import dev.example.db.domain.alarm.AlarmArgs;
import dev.example.db.domain.alarm.AlarmEntity;
import dev.example.db.domain.alarm.enums.AlarmType;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.user.UserEntity;

@Converter
public class AlarmConverter {

    public Alarm toAlarm(AlarmEntity alarmEntity) {
        return Alarm.builder()
                .id(alarmEntity.getId())
                .user(alarmEntity.getUser())
                .alarmArgs(alarmEntity.getArgs())
                .alarmType(alarmEntity.getAlarmType())
                .deletedAt(alarmEntity.getDeletedAt())
                .registeredAt(alarmEntity.getRegisteredAt())
                .updatedAt(alarmEntity.getUpdatedAt())
                .build();
    }

    public AlarmEntity toAlarmEntity(
            UserEntity userEntity,
            AlarmType alarmType,
            AlarmArgs alarmArgs
    ) {
        return AlarmEntity.builder()
                .user(userEntity)
                .alarmType(alarmType)
                .args(alarmArgs)
                .build();
    }

}
