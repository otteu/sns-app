package dev.example.api.domain.post.controller;


import dev.example.api.common.api.Api;
import dev.example.api.domain.comment.controller.model.response.CommentListResponse;
import dev.example.api.domain.post.business.PostBusiness;
import dev.example.api.domain.post.controller.model.request.PostCommentRequest;
import dev.example.api.domain.post.controller.model.request.PostCreateRequest;
import dev.example.api.domain.post.controller.model.request.PostModifyRequest;
import dev.example.api.domain.post.controller.model.response.PostListResponse;
import dev.example.api.domain.post.controller.model.response.PostModifyResponse;
import dev.example.api.domain.post.controller.model.response.PostMyListResponse;
import dev.example.db.domain.post.PostEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostBusiness postBusiness;

    @PostMapping()
    public Api<String> create(
            @RequestBody PostCreateRequest request,
            Authentication authentication
    ) {
        postBusiness.create(request, authentication.getName());
        // todo : 추가 변경
        String message = "등록되었습니다.";
        return Api.OK(message);
    }

    @PutMapping("/{postId}")
    public Api<PostModifyResponse> modify(
            @PathVariable Long postId,
            @RequestBody PostModifyRequest request,
            Authentication authentication
    ) {
        PostModifyResponse modify = postBusiness.modify(request, postId, authentication.getName());
        return Api.OK(modify);
    }

    @DeleteMapping("/{postId}")
    public Api<String> postDelete(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        postBusiness.postDelete(postId, authentication.getName());
        // todo : 추가 변경
        String message = "삭제되었습니다.";
        return Api.OK(message);
    }

    @GetMapping("/list")
    public Api<Page<PostListResponse>> postList(
            Pageable pageable, Authentication authentication
    ) {
        Page<PostListResponse> responses = postBusiness.selectPostList(pageable);
        return Api.OK(responses);
    }

    @GetMapping("/my")
    public Api<Page<PostMyListResponse>> myPostList(
            Pageable pageable, Authentication authentication
    ) {
        Page<PostMyListResponse> postListResponses = postBusiness.selectMyPostList(authentication.getName(), pageable);
        return Api.OK(postListResponses);
    }

    @PostMapping("/{postId}/likes")
    public Api<String> like(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        postBusiness.like(postId, authentication.getName());
        String message = "좋아요!! 했음";
        return Api.OK(message);
    }

    @GetMapping("/{postId}/likes")
    public Api<Long> likeCount(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        Long likeCount = postBusiness.likeCount(postId);
        return Api.OK(likeCount);
    }

    @PostMapping("/{postId}/comments")
    public Api<String> writeComment(
            @PathVariable Long postId,
            @RequestBody PostCommentRequest request,
            Authentication authentication
    ) {
        postBusiness.comment(postId, request, authentication.getName());
        String message = "comment 작성 완료";
        return Api.OK(message);
    }

    @GetMapping("/{postId}/comments")
    public Api<Page<CommentListResponse>> getCommentList(
            @PathVariable Long postId,
            Pageable pageable,
            Authentication authentication
    ) {
        // 조회 postId 필요, pageable 필요
        Page<CommentListResponse> commentList = postBusiness.getCommentList(postId, pageable);

        return Api.OK(commentList);
    }





}
