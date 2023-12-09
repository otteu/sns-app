package dev.example.api.domain.user.service;


import dev.example.api.common.error.UserErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.api.config.redis.UserCacheRepository;
import dev.example.api.domain.user.controller.model.User;
import dev.example.api.domain.user.converter.UserConverter;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.domain.user.enums.UserRole;
import dev.example.db.repository.user.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserCacheRepository userCacheRepository;



    public UserEntity join(UserEntity userEntity) {
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setJoinRole(UserRole.USER);
                    userEntity.setRegisterdAt(LocalDateTime.now());
                    return userEntityRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_ENTITY_POINT));
    }

    public void duplicatedUsernameCheckWithThrow(String username) {
        Optional<UserEntity> findUser = userEntityRepository.findByUsername(username);
        findUser.ifPresent(it -> {
            throw new ApiException(
                    UserErrorCode.DUPICATED_USER_NAME,
                    String.format("username : %s is duplicated",it.getUsername())
            );
        });
    }
    public UserEntity findByUsername(String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, String.format("username : %s not found", username))
                );

        return userEntity;
    }

    public User loadUserByUsername(String username) {

        User user = userCacheRepository.getUser(username)
                .orElseGet(() ->
                        userEntityRepository.findByUsername(username)
                                .map(User::fromUser)
                                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, String.format("username : %s not found", username))
                                )
                );
        return user;
    }
}
