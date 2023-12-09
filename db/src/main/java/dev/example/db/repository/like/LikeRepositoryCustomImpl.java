package dev.example.db.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.like.QLikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;

import javax.persistence.EntityManager;

import static dev.example.db.domain.like.QLikeEntity.likeEntity;

public class LikeRepositoryCustomImpl implements LikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public LikeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public LikeEntity findByPostAndUser(PostEntity postEntity, UserEntity userEntity) {
        return queryFactory
                .selectFrom(likeEntity)
                .where(
                        likeEntity.user.eq(userEntity),
                        likeEntity.post.eq(postEntity)
                ).fetchOne();
    }
}
