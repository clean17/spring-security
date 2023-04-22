package shop.mtcoding.securityapp.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.securityapp.model.User;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private String role;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .status(true)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        @NotEmpty(message = "E-mail을 입력해주세요.")
        private String email;
        @NotEmpty(message = "Password를 입력해주세요.")

        private String password;
    }
}
