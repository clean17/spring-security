package shop.mtcoding.securityapp.core.jwt;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import shop.mtcoding.securityapp.core.auth.MyUserDetails;
import shop.mtcoding.securityapp.dto.ResponseDTO;
import shop.mtcoding.securityapp.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String prefixJwt = request.getHeader(MyJwtProvider.HEADER);
        if(prefixJwt != null){
            String jwt = prefixJwt.replace(MyJwtProvider.TOKEN_PREFIX, "");
            try {
                DecodedJWT decodedJWT = MyJwtProvider.verify(jwt);
                Long id = decodedJWT.getClaim("id").asLong();
                String role = decodedJWT.getClaim("role").asString();

                User user = User.builder().id(id).role(role).build();
                MyUserDetails myUserDetails = new MyUserDetails(user);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                myUserDetails,
                                myUserDetails.getPassword(),
                                myUserDetails.getAuthorities()
                        );

                // 토큰이 있으면 홀더에 임시 세션을 넣는다.
                // 컨트롤러로 요청은 들어가지만 세션의 권한을 얻을 수는 없다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (SignatureVerificationException sve){
                log.error("토큰 검증 실패");
                chain.doFilter(request, response);
                return;
            }catch (TokenExpiredException tee){
                log.error("토큰 기한 만료");
                chain.doFilter(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}