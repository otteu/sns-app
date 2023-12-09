package dev.example.api.domain.user.controller.model.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponse {

    private String token;
}
