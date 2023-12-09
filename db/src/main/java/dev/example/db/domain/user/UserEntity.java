package dev.example.db.domain.user;


import dev.example.db.domain.BaseEntity;
import dev.example.db.domain.user.enums.UserRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() where user_id=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Id @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
//    private UserRole role = UserRole.USER;

    @Column(name = "registerd_at")
    private LocalDateTime registeredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    /**
     *
     * * */
    public void setJoinRole(UserRole userRole) {
        this.role = userRole;
    }

    public void setRegisterdAt(LocalDateTime localDateTime) {
        this.registeredAt = localDateTime;
    }

//    public void setEncodePassword(String encodePassword) {
//        this.password = encodePassword;
//    }

}
