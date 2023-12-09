package dev.example.api.domain.comment.controller.model.response;


import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommentListResponse {

    private Long id;
    private UserEntity user;
    private PostEntity post;
    private String comment;
    private LocalDateTime registeredAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
