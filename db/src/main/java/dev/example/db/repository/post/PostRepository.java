package dev.example.db.repository.post;

import dev.example.db.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> , PostRepositoryCustom {
}
