package dev.example.api.domain.user.controller.model.response;

import dev.example.db.domain.user.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserJoinResponse {

    private Long id;
    private String username;
    private UserRole role;
//    private LocalDateTime registeredAt;
//    private LocalDateTime updatedAt;
}
