package dev.example.db.repository.comment;

import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.post.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustome {

    Page<CommentEntity> getCommentList(PostEntity validPost, Pageable pageable);

}
