package dev.example.api.domain.post.business;


import dev.example.api.common.annotation.Business;
import dev.example.api.common.error.PostErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.api.domain.alarm.converter.AlarmConverter;
import dev.example.api.domain.alarm.service.AlarmService;
import dev.example.api.domain.comment.controller.model.response.CommentListResponse;
import dev.example.api.domain.comment.converter.CommentConverter;
import dev.example.api.domain.comment.service.CommentService;
import dev.example.api.domain.like.converter.LikeConverter;
import dev.example.api.domain.like.service.LikeService;
import dev.example.api.domain.post.controller.model.request.PostCommentRequest;
import dev.example.api.domain.post.controller.model.request.PostCreateRequest;
import dev.example.api.domain.post.controller.model.request.PostModifyRequest;
import dev.example.api.domain.post.controller.model.response.PostListResponse;
import dev.example.api.domain.post.controller.model.response.PostModifyResponse;
import dev.example.api.domain.post.controller.model.response.PostMyListResponse;
import dev.example.api.domain.post.converter.PostConverter;
import dev.example.api.domain.post.service.PostService;
import dev.example.api.domain.user.service.UserService;
import dev.example.db.domain.alarm.AlarmArgs;
import dev.example.db.domain.alarm.AlarmEntity;
import dev.example.db.domain.alarm.enums.AlarmType;
import dev.example.db.domain.comment.CommentEntity;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.repository.like.LikeRepository;
import dev.example.kafka.AlarmProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Business
@Transactional(readOnly = true)
public class PostBusiness {

    private final PostService postService;
    private final PostConverter postConverter;
    private final UserService userService;
    private final LikeService likeService;
    private final LikeConverter likeConverter;
    private final CommentService commentService;
    private final CommentConverter commentConverter;
    private final AlarmService alarmService;
    private final AlarmConverter alarmConverter;
    private final AlarmProducer alarmProducer;


    @Transactional
    public void create(PostCreateRequest request, String username) {
        // 유저 조회
        UserEntity findUser = userService.findByUsername(username);
        PostEntity createEntity = postConverter.toCreateEntity(request, findUser);
        // Post save
        postService.save(createEntity);
    }

    @Transactional
    public PostModifyResponse modify(PostModifyRequest request, Long postId, String username) {
        // user 조회
        UserEntity findUser = userService.findByUsername(username);
        // post 조회
        PostEntity findPost = postService.findOneById(postId);
//        PostEntity findPost = postService.findOneById2(postId, findUser);

        // 포스트글과 작성자가 수정 하려고 하는 것인지 비교
        if (findUser != findPost.getUser()) {
            throw new ApiException(PostErrorCode.INVALID_PERMISSION, String.format("username : $s has no permission with postId : %s", username, postId));
        }

        // 변경
        findPost.setModifyTitle(request.getTitle());
        findPost.setModifyBody(request.getBody());
        PostEntity saveEntity = postService.modify(findPost);

        return  postConverter.toModifyResponse(saveEntity);
    }

    @Transactional
    public void postDelete(Long postId, String username) {
        UserEntity findUser = userService.findByUsername(username);
        // post 조회
        PostEntity findPost = postService.findOneById(postId);
        // 포스트글과 작성자가 수정 하려고 하는 것인지 비교
        if (findUser != findPost.getUser()) {
            throw new ApiException(PostErrorCode.INVALID_PERMISSION, String.format("username : $s has no permission with postId : %s", username, postId));
        }


        likeService.deleteAllByPost(findPost);
        commentService.deleteAllByPost(findPost);
        postService.postDelete(findPost);
    }



    public Page<PostListResponse> selectPostList(
            Pageable pageable
    ) {
        // 검증 절차 없음
        // 조회 Lazy User no no
        // where 값 no no
        Page<PostListResponse> findPostList = postService.selectPostList(pageable).map(postConverter::toPostListResponse);
        return findPostList;
    }

    public Page<PostMyListResponse> selectMyPostList(String username, Pageable pageable) {
        // 유저 정보 조회
        UserEntity findUser = userService.findByUsername(username);
        return postService.selectMyPostList(findUser, pageable).map(postConverter::toMyPostListResponse);
    }

    @Transactional
    public void like(Long postId, String username) {
        // user 조회
        UserEntity findUser = userService.findByUsername(username);
        // post 조회
        PostEntity findPost = postService.findOneById(postId);

        // 좋아요는 1번만 가능
        // post where postId and userId
        likeService.likedCheckWithThrow(findPost, findUser);
        LikeEntity likeEntity = likeConverter.otLikeEntity(findUser, findPost);
        likeService.like(likeEntity);

        // 알람 저장
        // 알람 타입,
        AlarmEntity alarmEntity = alarmConverter.toAlarmEntity(findPost.getUser(), AlarmType.NEW_LIKE_NO_POST, new AlarmArgs(findUser.getId(), findPost.getId()));
        AlarmEntity savedAlarm = alarmService.savePostCommentCustom(alarmEntity);

        // Kafka Producer 전송
        alarmProducer.alarm(savedAlarm.toString());
    }


    public Long likeCount(Long postId) {
        // post 조회
        PostEntity findPost = postService.findOneById(postId);
        return likeService.likeCount(findPost);
    }

    @Transactional
    public void comment(Long postId, PostCommentRequest request, String username) {

        // user 조회
        UserEntity validUser = userService.findByUsername(username);
        // post 조회
        PostEntity validPost = postService.findOneById(postId);
        String writeComment = request.getComment();


        // comment 저장
        CommentEntity writeCommentEntity = commentConverter.toWriteCommentEntity(validPost, validUser, writeComment);
        commentService.writeComment(writeCommentEntity);

        // 알람 저장
        // 알람 타입,
        AlarmEntity alarmEntity = alarmConverter.toAlarmEntity(validPost.getUser(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(validUser.getId(), validPost.getId()));
        alarmService.savePostComment(alarmEntity);

    }

    public Page<CommentListResponse> getCommentList(Long postId, Pageable pageable) {

        // 1. post 있는지 확인
        // post 조회
        PostEntity validPost = postService.findOneById(postId);

        // 2. 있으면 post의 달린 댓글 page 해서 가져오기
        return commentService.getCommentList(validPost, pageable).map(commentConverter::toCommentListResponse);

    }
}
