package dev.example.db.repository.like;

import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;

public interface LikeRepositoryCustom {

    LikeEntity findByPostAndUser(PostEntity postEntity, UserEntity userEntity);

}
