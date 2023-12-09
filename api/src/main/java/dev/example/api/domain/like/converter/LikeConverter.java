package dev.example.api.domain.like.converter;

import dev.example.api.common.annotation.Converter;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;

@Converter
public class LikeConverter {


    public LikeEntity otLikeEntity(UserEntity userEntity, PostEntity postEntity) {
        return LikeEntity.builder()
                .user(userEntity)
                .post(postEntity)
                .build();
    }


}
