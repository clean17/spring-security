package shop.mtcoding.securityapp.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.securityapp.dto.ResponseDTO;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomApiException e) {
        return new ResponseEntity<>(new ResponseDTO<>(-1, e.getMessage(), null), e.getStatus());
    }

}




