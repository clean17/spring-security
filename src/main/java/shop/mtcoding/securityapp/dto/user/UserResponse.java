package shop.mtcoding.securityapp.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.securityapp.core.util.MyDateUtils;
import shop.mtcoding.securityapp.model.User;
public class UserResponse {

    @Getter
    @Setter
    public static class JoinDTO {
        private Long id;
        private String name;
        private String email;
        private String createdAt;


        public JoinDTO(User user) {
            this.id = user.getId();
            this.name = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = MyDateUtils.toStringFormat(user.getCreatedAt());
        }
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private Long id;
        private String email;
        private String createdAt;

        public LoginDTO(User user) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.createdAt = MyDateUtils.toStringFormat(user.getCreatedAt());
        }
    }
}