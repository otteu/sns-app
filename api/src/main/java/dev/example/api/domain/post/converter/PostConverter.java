package dev.example.api.domain.post.converter;


import dev.example.api.common.annotation.Converter;
import dev.example.api.domain.post.controller.model.request.PostCreateRequest;
import dev.example.api.domain.post.controller.model.request.PostModifyRequest;
import dev.example.api.domain.post.controller.model.response.PostListResponse;
import dev.example.api.domain.post.controller.model.response.PostModifyResponse;
import dev.example.api.domain.post.controller.model.response.PostMyListResponse;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;

@Converter
public class PostConverter {

    public PostEntity toCreateEntity(PostCreateRequest request, UserEntity findUser) {

        return PostEntity.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .user(findUser)
                .build();
    }

    public PostModifyResponse toModifyResponse(PostEntity postEntity) {
        return PostModifyResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .user(postEntity.getUser())
                .deletedAt(postEntity.getDeletedAt())
                .registeredAt(postEntity.getRegisteredAt())
                .updatedAt(postEntity.getUpdatedAt())
                .build();
    }

    public PostListResponse toPostListResponse(PostEntity postEntity) {
        return PostListResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .user(postEntity.getUser())
                .deletedAt(postEntity.getDeletedAt())
                .registeredAt(postEntity.getRegisteredAt())
                .updatedAt(postEntity.getUpdatedAt())
                .build();
    }


    public PostMyListResponse toMyPostListResponse(PostEntity postEntity) {
        return PostMyListResponse.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .body(postEntity.getBody())
                .user(postEntity.getUser())
                .deletedAt(postEntity.getDeletedAt())
                .registeredAt(postEntity.getRegisteredAt())
                .updatedAt(postEntity.getUpdatedAt())
                .build();
    }


}


