package shop.mtcoding.securityapp.model;

import java.time.LocalDateTime;
import java.util.Date;

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

    // @PrePersist 주석이 달린 onCreate() 메서드는 엔터티가 지속되기 전에 호출되어 현재 타임스탬프로 createdAt 필드를 설정합니다. 
    // 이 접근 방식은 사용 중인 특정 지속성 공급자에 관계없이 JPA와의 호환성을 보장

    @PreUpdate // update시 동작 
    public void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    public Date getPasswordExpiration() {
        return null;
    }

    public boolean isAccountNonLocked() {
        return true;
    }
}
