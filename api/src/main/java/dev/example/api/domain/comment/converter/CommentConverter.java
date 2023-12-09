package dev.example.api.domain.comment.converter;


import dev.example.api.common.annotation.Converter;
import dev.example.api.domain.comment.controller.model.response.CommentListResponse;
import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import lombok.Builder;

@Converter
@Builder
public class CommentConverter {
    public CommentEntity toWriteCommentEntity(
            PostEntity validPostEntity,
            UserEntity validUserEntity,
            String writeComment
    ) {

        return CommentEntity.builder()
                .post(validPostEntity)
                .user(validUserEntity)
                .comment(writeComment)
                .build();
    }

    public CommentListResponse toCommentListResponse(
            CommentEntity commentEntity
    ) {

       return CommentListResponse.builder()
                .id(commentEntity.getId())
                .user(commentEntity.getUser())
                .post(commentEntity.getPost())
                .comment(commentEntity.getComment())
                .deletedAt(commentEntity.getDeletedAt())
                .updatedAt(commentEntity.getUpdatedAt())
                .registeredAt(commentEntity.getRegisteredAt())
                .build();
    }
}
