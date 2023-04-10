package shop.mtcoding.securityapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(){
        return "redirect:/";
    }

    @GetMapping("/users/1")
    public ResponseEntity<?> hello1() {

        return ResponseEntity.ok().body("ok1111");
    }

    /*
     * Sentry를 처음 사용하는 경우 이메일 알림을 사용하여 계정에 액세스하고 제품 둘러보기를 완료하십시오.
     * 
     * 기존 사용자이고 알림을 비활성화한 경우 이 이메일을 받지 않습니다.
     * 
     * 성능 측정
     * 들어오는 각 Spring MVC HTTP 요청은 자동으로 트랜잭션으로 전환됩니다. Bean 메서드 실행 주위에 범위를 만들려면 다음과 같이
     * Bean 메서드에 @SentrySpan주석을 추가합니다.
     */
    
    @GetMapping("/sentri")
    public void sentri() {
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
