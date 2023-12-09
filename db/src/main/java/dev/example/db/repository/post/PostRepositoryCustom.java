package dev.example.db.repository.post;

import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PostRepositoryCustom {
    PostEntity modifyFetchJoinUser(Long postId, UserEntity user);


    Page<PostEntity> myPostList(UserEntity user, Pageable pageable);

}
