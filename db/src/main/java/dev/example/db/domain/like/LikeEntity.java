package dev.example.db.domain.like;


import dev.example.db.domain.BaseEntity;
import dev.example.db.domain.post.PostEntity;
import dev.example.db.domain.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "likes")
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE like_id=?")
@Where(clause = "deleted_at is NULL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeEntity extends BaseEntity {

    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "registerd_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    /***
     *
     */
    public void setRegisterdAt(LocalDateTime time) {
        this.registeredAt = time;
    }


}
