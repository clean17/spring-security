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

<<<<<<< HEAD
=======
    // username 필터를 리빌딩하는건 추천하지 않음 dto 에서 AOP사용하는게 더 편함 
>>>>>>> 14cec13 (세션 있을때 테스트하는 코드 잠깐 추가 master에서 테스트할것)
    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
<<<<<<< HEAD
        @NotEmpty(message = "E-mail을 입력해주세요.")
        private String email;
        @NotEmpty(message = "Password를 입력해주세요.")
=======
>>>>>>> 14cec13 (세션 있을때 테스트하는 코드 잠깐 추가 master에서 테스트할것)
        private String password;
    }
}
