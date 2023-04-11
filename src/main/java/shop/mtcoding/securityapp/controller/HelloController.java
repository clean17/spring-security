package shop.mtcoding.securityapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.securityapp.core.auth.MyUserDetails;
import shop.mtcoding.securityapp.core.exception.Exception400;
import shop.mtcoding.securityapp.dto.ResponseDTO;
import shop.mtcoding.securityapp.dto.UserRequest;
import shop.mtcoding.securityapp.dto.UserResponse;
import shop.mtcoding.securityapp.jwt.MyJwtProvider;
import shop.mtcoding.securityapp.service.UserService;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final UserService userService;

    @Value("${meta.name}")
    private String name;

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body(name);
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO loginDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new Exception400(result.getAllErrors().get(0).getDefaultMessage());
        }

        String jwt = userService.로그인(loginDTO);
        return ResponseEntity.ok().header(MyJwtProvider.HEADER, jwt).body("로그인 성공하였습니다.");
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(UserRequest.JoinDTO joinDTO){
        // open in view false 일때 여기서 select 가능
        UserResponse.JoinDTO data = userService.회원가입(joinDTO);
        ResponseDTO<?> responseDTO = new ResponseDTO<>().data(data);
        return ResponseEntity.ok().body(responseDTO);
    }

    // @GetMapping("/users/{id}")
    // public ResponseEntity<?> userCheck(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
    //     String username = myUserDetails.getUsername();
    //     String role = myUserDetails.getUser().getRole();
    //     return ResponseEntity.ok().body(username + " : "+ role);
    // }

    /*
     * Sentry를 처음 사용하는 경우 이메일 알림을 사용하여 계정에 액세스하고 제품 둘러보기를 완료하십시오.
     * 
     * 기존 사용자이고 알림을 비활성화한 경우 이 이메일을 받지 않습니다.
     * 
     * 성능 측정
     * 들어오는 각 Spring MVC HTTP 요청은 자동으로 트랜잭션으로 전환됩니다. Bean 메서드 실행 주위에 범위를 만들려면 다음과 같이
     * Bean 메서드에 @SentrySpan주석을 추가합니다.
     */
    @GetMapping("/ct/users/{id}") //인증 확인
    public ResponseEntity<?> userCheck(@PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails myUserDetails ) {

        Long principalId = myUserDetails.getUser().getId();
        String role = myUserDetails.getUser().getRole();

        if (principalId != id){
            return ResponseEntity.badRequest().body("올바른 접근이 아닙니다. ");
        }

        return ResponseEntity.ok().body(principalId + " : " + role);
    }

    
    @GetMapping("/sentry")
    public void sentri() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
