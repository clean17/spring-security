package shop.mtcoding.securityapp.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.securityapp.dto.UserRequest;
import shop.mtcoding.securityapp.dto.UserResponse;
import shop.mtcoding.securityapp.jwt.MyJwtProvider;
import shop.mtcoding.securityapp.dto.UserRequest.LoginDTO;
import shop.mtcoding.securityapp.model.User;
import shop.mtcoding.securityapp.model.UserRepository;

@RequiredArgsConstructor
@Service
// @Transactional(Read = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    /* 
    서비스 역할
     * 트랜잭션 - 영속성 객체 변경 감지 -  requestDTO 받기 - 비즈니스 로직 처리 - responseDTO 응답
     * 
     */
    @Transactional
    public UserResponse.JoinDTO 회원가입(UserRequest.JoinDTO joinDTO){
        String rawPassword = joinDTO.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword); // 암호화된 패스워드는 60바이트 고정
        joinDTO.setPassword(encPassword);
        User userPS = userRepository.save(joinDTO.toEntity());
        // toEntity는 유저 리턴

        return new UserResponse.JoinDTO(userPS);
    }

    @Transactional
    public String 로그인(UserRequest.LoginDTO loginDTO) {
        Optional<User> userOP = userRepository.findbyUsername(loginDTO.getUsername());
        if(userOP.isPresent()){
            User userPS = userOP.get();
            if(passwordEncoder.matches(loginDTO.getPassword(), userPS.getPassword())){
                String jwt = MyJwtProvider.create(userPS);
                return jwt;
            }
            throw new RuntimeException("패스워드가 틀렸습니다.");
        }else{
            throw new RuntimeException("존재하지않는 이메일입니다.");
        }
    }
}
