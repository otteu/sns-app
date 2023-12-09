package dev.example.db.repository.like;

import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long>, LikeRepositoryCustom {

    @Query("SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =: post")
    Optional<Long> findByPostCount(@Param("post") PostEntity postEntity);


    @Transactional
    @Modifying
    @Query("UPDATE LikeEntity entity SET entity.deletedAt = NOW() WHERE entity.post = :post")
    void deleteAllByPost(@Param("post") PostEntity postEntity);

}
