package dev.example.db.repository.comment;

import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.post.PostEntity;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>, CommentRepositoryCustome {


    @Transactional
    @Modifying
    @Query("UPDATE CommentEntity entity SET entity.deletedAt = NOW() WHERE entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity findPost);

}
