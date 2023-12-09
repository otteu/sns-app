package dev.example.db.repository.alarm;

import dev.example.db.domain.alarm.AlarmEntity;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<AlarmEntity, Long> {
    Page<AlarmEntity> findAllByUser(UserEntity userEntity, Pageable pageable);

    Page<AlarmEntity> findAllByUserId(Long userId, Pageable pageable);

}
