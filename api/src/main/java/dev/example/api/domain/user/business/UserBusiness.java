package dev.example.api.domain.user.business;


import dev.example.api.common.annotation.Business;
import dev.example.api.common.error.UserErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.api.config.redis.UserCacheRepository;
import dev.example.api.domain.alarm.converter.AlarmConverter;
import dev.example.api.domain.alarm.model.Alarm;
import dev.example.api.domain.alarm.service.AlarmService;
import dev.example.api.domain.user.controller.model.User;
import dev.example.api.domain.user.controller.model.request.UserJoinRequest;
import dev.example.api.domain.user.controller.model.request.UserLoginRequest;
import dev.example.api.domain.user.controller.model.response.UserJoinResponse;
import dev.example.api.domain.user.controller.model.response.UserLoginResponse;
import dev.example.api.domain.user.converter.UserConverter;
import dev.example.api.domain.user.service.UserService;
import dev.example.api.utils.jwt.JwtTokenUtils;
import dev.example.db.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Business
@Transactional(readOnly = true)
public class UserBusiness {

    private final UserService userService;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder encoder;
    private final AlarmService alarmService;
    private final AlarmConverter alarmConverter;
    private final UserCacheRepository userCacheRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public UserJoinResponse join(UserJoinRequest request) {

        // 이미 가입한 회원이 있는지 검증
        userService.duplicatedUsernameCheckWithThrow(request.getUsername());

        // Entity 로 변환
        UserEntity entity = userConverter.toJoinEntity(request, encoder.encode(request.getPassword()));
        // 회원 가입
        UserEntity newUserEntity  = userService.join(entity);

        // 반환 객체로 변환
        UserJoinResponse response = userConverter.toJoinResponse(newUserEntity);

        return response;
    }

    public UserLoginResponse login(UserLoginRequest request) {

        // 회원가입 여부 채크
        // Cache 로 변경
//        UserEntity findUserEntity = userService.findByUsername(request.getUsername());
        User user = userService.loadUserByUsername(request.getUsername());
        userCacheRepository.setUser(user);

        // 비밀번호 체크
        passwordNotEqualsWithThrow(request.getPassword(), user.getPassword());

        // token
        String token = JwtTokenUtils.generateToken(request.getUsername(), secretKey, expiredTimeMs);

        // 변환
        UserLoginResponse response = userConverter.toLoginResponse(token);

        return response;
    }

    // 비밀 번호 체크
    private void passwordNotEqualsWithThrow(String userPassword, String findUserPassword) {
        if(!encoder.matches(userPassword, findUserPassword)) {
            throw new ApiException(UserErrorCode.INVALID_PASSWORD);
        }
    }

    public Page<Void> alaramList(String userName, Pageable pageable) {

        return Page.empty();
    }


    public Page<Alarm> getAlarmList(String username, Pageable pageable) {
        // User 검증
        UserEntity validUserEntity = userService.findByUsername(username);

        return alarmService.getLikeList(validUserEntity, pageable).map(alarmConverter::toAlarm);
    }

    public Page<Alarm> getAlarmListCustom(Long userId, Pageable pageable) {
        // User 검증
        return alarmService.getLikeListCustom(userId, pageable).map(alarmConverter::toAlarm);
    }
}
