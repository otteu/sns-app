package dev.example.api.domain.alarm.service;

import dev.example.api.common.error.AlarmErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.db.domain.alarm.AlarmArgs;
import dev.example.db.domain.alarm.AlarmEntity;
import dev.example.db.domain.alarm.enums.AlarmType;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.repository.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public Page<AlarmEntity> getLikeList(
            UserEntity validUserEntity,
            Pageable pageable
    ) {
        return Optional.ofNullable(alarmRepository.findAllByUser(validUserEntity, pageable))
                .orElseThrow(() -> new ApiException(AlarmErrorCode.USER_NOT_FOUND));
    }

    public Page<AlarmEntity> getLikeListCustom(
            Long userId,
            Pageable pageable
    ) {
        return Optional.ofNullable(alarmRepository.findAllByUserId(userId, pageable))
                .orElseThrow(() -> new ApiException(AlarmErrorCode.USER_NOT_FOUND));
    }



    public void savePostComment(
            AlarmEntity alarmEntity
    ) {
        alarmRepository.save(alarmEntity);
    }

    public AlarmEntity savePostCommentCustom(
            AlarmEntity alarmEntity
    ) {
        return alarmRepository.save(alarmEntity);
    }
}
