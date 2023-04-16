package shop.mtcoding.securityapp.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.securityapp.model.User;

import javax.validation.constraints.NotEmpty;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        @NotEmpty(message = "nickname을 입력해주세요.")
        private String name;
        @NotEmpty(message = "E-mail을 입력해주세요.")
        private String email;
        @NotEmpty(message = "Password를 입력해주세요.")
        private String password;

        public User toEntity() {
            return User.builder()
                    .username(name)
                    .email(email)
                    .password(password)
                    .build();
        }
    }

    // username 필터를 리빌딩하는건 추천하지 않음 dto 에서 AOP사용하는게 더 편함 
    @Getter
    @Setter
    public static class LoginDTO {
        @NotEmpty(message = "E-mail을 입력해주세요.")
        private String email;
        @NotEmpty(message = "Password를 입력해주세요.")
        private String password;
    }
}
