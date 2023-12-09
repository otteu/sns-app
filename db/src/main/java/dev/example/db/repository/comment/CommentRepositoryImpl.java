package dev.example.db.repository.comment;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.comment.QCommentEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static dev.example.db.domain.comment.QCommentEntity.commentEntity;


public class CommentRepositoryImpl implements CommentRepositoryCustome{

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CommentEntity> getCommentList(PostEntity validPost, Pageable pageable) {

        List<CommentEntity> content = queryFactory
                .selectFrom(commentEntity)
                .where(commentEntity.post.eq(validPost))
                .offset(0)
                .limit(5)
                .fetch();

        JPAQuery<CommentEntity> countQuery = queryFactory
                .selectFrom(commentEntity)
                .where(commentEntity.post.eq(validPost));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
    }


//    @Override
//    public Page<PostEntity> myPostList(UserEntity user, Pageable pageable) {
//        JPAQuery<PostEntity> countQuery = queryFactory
//                .selectFrom(postEntity)
//                .where(postEntity.user.eq(user));
//
//        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
//    }
}
