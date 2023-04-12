package shop.mtcoding.securityapp.core.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import shop.mtcoding.securityapp.model.User;

@Getter
public class MyUserDetails implements UserDetails{

    // 사용자 정보와 관련된 여러 정보들을 리턴하는 메소드들을 가진다.
    private User user;

    
    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(()-> "ROLE_"+user.getRole());
        return authorities;
    }

    @Override
    // 토큰 기반 인증과 같은 사용자 지정 인증 방법을 사용하는 경우 암호가 필요하지 않을 수 있습니다. 이 경우 getPassword() 메서드는 null을 반환
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    // 아래의 옵션들로 Spring Security가 발생시킨다. false의 값들이 온다면 인증을 허용하지 않고 사용자는 권한을 얻지못해 엑세스를 못함
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 / 시큐리티는 사용자가 인증하도록 허용한다.
    // false 일경우
    @Override
    public boolean isEnabled() {
        return user.getStatus();
    }
    
}
