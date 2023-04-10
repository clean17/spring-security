package shop.mtcoding.securityapp.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.securityapp.dto.UserRequest;
import shop.mtcoding.securityapp.dto.UserResponse;
import shop.mtcoding.securityapp.model.User;
import shop.mtcoding.securityapp.model.UserRepository;

@RequiredArgsConstructor
@Service
// @Transactional(readOnly = true)
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
}
