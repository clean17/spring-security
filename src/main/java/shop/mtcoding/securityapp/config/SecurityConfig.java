package shop.mtcoding.securityapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 단방향 암호화
    // Configuration + Bean -> IoC 에 생성
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    // 시큐리티 설정을 비활성화 하기 위한 세팅 - 커스텀
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 토큰을 숨겨두어서 보안검사
        http.csrf().disable(); // postman 으로 접근하기 위해 토큰을 비활성화 - csr 이용 , 반면에 ssr이라면 ?
        // ssr은 기본적으로 csrf 토큰을 이용하지 않지만 적용할 수 있다.

        http.formLogin()
                .loginPage("/loginForm")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login") // 로그인 양식 데이터를 제출해야 하는 URL - post
                .defaultSuccessUrl("/") // 인증 성공후 리다이렉션되는 주소
                 // .defaultSuccessUrl("/", true); // 상관없이 강제 리다이렉션
                .successHandler((req, resp, authentication)->{
                    System.out.println("디버그 : 로그인이 완료되었습니다.");
                }) // 로그 기록 
                .failureHandler((req, resp, exception)->{
                    System.out.println("디버그 : 로그인 실패 -> " + exception.getMessage());
                }); // 에러 로그
       
        // 인증, 권한 필터 설정 ( 스프링 문서 참고 )
        http.authorizeRequests((authorize)->{
            authorize.antMatchers("/users/**").authenticated()
            .antMatchers("/manager/**").access("hasRole('ADMIN') or hasRole('MANAGER')")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().permitAll(); // /users 는 인증이 필요 나머지는 허용
        });
        return http.build();
    }
}
