package dev.example.api.domain.user.converter;


import dev.example.api.common.annotation.Converter;
import dev.example.api.domain.user.controller.model.User;
import dev.example.api.domain.user.controller.model.request.UserJoinRequest;
import dev.example.api.domain.user.controller.model.response.UserJoinResponse;
import dev.example.api.domain.user.controller.model.response.UserLoginResponse;
import dev.example.db.domain.user.UserEntity;

@Converter
public class UserConverter {

    public UserEntity toJoinEntity(
            UserJoinRequest userJoinRequest,
            String encodePassword
    ) {
        return UserEntity.builder()
                .username(userJoinRequest.getUsername())
                .password(encodePassword)
                .build();
    }

    public UserJoinResponse toJoinResponse(
            UserEntity userEntity
    ) {
        return UserJoinResponse.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
//                .registeredAt(userEntity.getRegisteredAt())
//                .updatedAt(userEntity.getUpdatedAt())
//                .deletedAt(userEntity.getDeletedAt())
                .build();
    }


    public UserLoginResponse toLoginResponse(String token) {

        return UserLoginResponse.builder()
                .token(token)
                .build();
    }



}
