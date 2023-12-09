package dev.example.api.domain.comment.service;


import dev.example.api.common.error.CommentErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    public void writeComment(CommentEntity commentEntity) {
        Optional.ofNullable(commentEntity)
                        .map(it -> {
                            it.setRegisterdAt(LocalDateTime.now());
                            return commentRepository.save(it);
                        })
                        .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_WRITE_FAIL));
    }

    public Page<CommentEntity> getCommentList(PostEntity validPost, Pageable pageable) {

        return commentRepository.getCommentList(validPost, pageable);
    }

    public void deleteAllByPost(PostEntity findPost) {
        commentRepository.deleteAllByPost(findPost);
    }
}
