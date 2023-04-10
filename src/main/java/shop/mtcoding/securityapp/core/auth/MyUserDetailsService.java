package shop.mtcoding.securityapp.core.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.securityapp.model.User;
import shop.mtcoding.securityapp.model.UserRepository;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    // /login + Post + FormUrlEncoded + username,password 모두 성립하면
    // 즉 스프링 시큐리티 준수하면 UserDetails를 리턴한다. -> Authentication 객체를 만든다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOP = userRepository.findbyUsername(username);
        if(userOP.isPresent()){
            return new MyUserDetails(userOP.get());
        }else{
            return null;
        }
    }
    
}
