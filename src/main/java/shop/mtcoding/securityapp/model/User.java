package shop.mtcoding.securityapp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // Hibernate가 관리 - 영속 비영속 준영속
@Table(name = "user_tb")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role; //USER, MANAGER, ADMIN
    private Boolean status;

    @Builder
    public User(Long id, String username, String password, String email, String role, boolean status,
            LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    private LocalDateTime createdAt; // db에는 timestamp로 변경되어 들어감
    private LocalDateTime updateAt;

    @PrePersist // insert시 동작 / 비영속 -> 영속
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate // update시 동작 
    public void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }
}
