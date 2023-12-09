package dev.example.db.repository.post;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.post.QPostEntity;
import dev.example.db.domain.user.QUserEntity;
import dev.example.db.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static dev.example.db.domain.post.QPostEntity.postEntity;
import static dev.example.db.domain.user.QUserEntity.userEntity;


public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
//    private final EntityManager ems;
//    public OrderRepositoryCustomImpl(EntityManager em, EntityManager ems) {
    public PostRepositoryCustomImpl(EntityManager em) {
//        this.ems = ems;
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public PostEntity modifyFetchJoinUser(Long postId, UserEntity user) {
        return queryFactory
                .selectFrom(postEntity)
                .join(postEntity.user, userEntity).fetchJoin()
                .where(postEntity.id.eq(postId))
                .fetchOne()
                ;
    }

    @Override
    public Page<PostEntity> myPostList(UserEntity user, Pageable pageable) {
        List<PostEntity> content = queryFactory
                .selectFrom(postEntity)
                .where(postEntity.user.eq(user))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PostEntity> countQuery = queryFactory
                .selectFrom(postEntity)
                .where(postEntity.user.eq(user));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }
}
