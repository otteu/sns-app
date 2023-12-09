package dev.example.db.domain.post;

import dev.example.db.domain.BaseEntity;
import dev.example.db.domain.like.LikeEntity;
import dev.example.db.domain.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE post_id=?")
@Where(clause = "deleted_at is NULL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "")
    private List<LikeEntity> like = new ArrayList<>();

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private List<CommentEntity> comments;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")1
//    private List<LikeEntity> likes;

    @Column(name = "registerd_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     * Create */
    public void setCreateTitle(String title) {
        this.title = title;
    }

    public void setCreateBody(String body) {
        this.title = body;
    }

    public void setCreateUser(UserEntity user) {
        this.user = user;
    }

    public void setCreateRegisterdAt(LocalDateTime time) {
        this.registeredAt = time;
    }


    /**
     * Modify */
    public void setModifyTitle(String title) {
        this.title = title;
    }

    public void setModifyBody(String body) {
        this.body = body;
    }
}
