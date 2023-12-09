package dev.example.api.domain.post.service;


import dev.example.api.common.error.PostErrorCode;
import dev.example.api.common.exception.ApiException;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import dev.example.db.repository.post.PostRepository;
import dev.example.db.repository.post.PostRepositoryCustom;
import dev.example.db.repository.post.PostRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public PostEntity save(PostEntity postEntity) {

        return Optional.ofNullable(postEntity)
                .map(it -> {
                    it.setCreateRegisterdAt(LocalDateTime.now());
                    return postRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND));

    }

    public PostEntity findOneById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND, String.format("postId : %s not founed", postId)));
    }

    public PostEntity findOneById2(Long postId, UserEntity user) {
        PostEntity postEntity = postRepository.modifyFetchJoinUser(postId, user);
        return Optional.ofNullable(postEntity)
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND, String.format("postId : %s not founed", postId)));
    }

    public PostEntity modify(PostEntity findPost) {
        return postRepository.saveAndFlush(findPost);
    }

    public void postDelete(PostEntity post) {
        postRepository.delete(post);
    }

    public Page<PostEntity> selectPostList(
            Pageable pageable
    ) {
        Page<PostEntity> postList = postRepository.findAll(pageable);

        Page<PostEntity> response = Optional.ofNullable(postList)
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND));
        return response;
    }

    public Page<PostEntity> selectMyPostList(UserEntity user, Pageable pageable) {

        Page<PostEntity> posts = postRepository.myPostList(user, pageable);

        return Optional.ofNullable(posts)
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND));
    }
}
