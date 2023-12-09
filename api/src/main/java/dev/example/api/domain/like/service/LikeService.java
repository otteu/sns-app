package dev.example.api.domain.like.service;

import dev.example.api.common.error.LikeErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.repository.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;


    public void likedCheckWithThrow(PostEntity postEntity, UserEntity userEntity) {

        LikeEntity findLike = likeRepository.findByPostAndUser(postEntity, userEntity);
        Optional.ofNullable(findLike)
                .ifPresent(it -> {
                    throw new ApiException(
                            LikeErrorCode.ALERADY_LIKED,
                            String.format("username : %s already like post : %d", userEntity.getUsername(), postEntity.getId())
                            );
                });

    }

    public void like(LikeEntity likeEntity) {

        Optional.ofNullable(likeEntity)
                .map(it -> {
                    it.setRegisterdAt(LocalDateTime.now());
                    return likeRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(LikeErrorCode.LIKE_FAIL));
    }

    public Long likeCount(PostEntity postEntity) {

        return likeRepository.findByPostCount(postEntity)
                .orElseThrow(() -> new ApiException(LikeErrorCode.LIKE_NOT_FOUND))
        ;
    }

    public void deleteAllByPost(PostEntity findPost) {

        likeRepository.deleteAllByPost(findPost);

    }
}
