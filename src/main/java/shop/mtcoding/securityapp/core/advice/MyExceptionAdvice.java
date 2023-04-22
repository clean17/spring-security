
package shop.mtcoding.securityapp.core.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.securityapp.core.exception.CustomApiException;
import shop.mtcoding.securityapp.core.exception.CustomException;
import shop.mtcoding.securityapp.core.exception.Exception400;
import shop.mtcoding.securityapp.core.exception.MyValidationException;
import shop.mtcoding.securityapp.dto.ResponseDTO;

@RestControllerAdvice
public class MyExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomApiException e) {
        return new ResponseEntity<>(new ResponseDTO<>(-1, e.getMessage(), null), e.getStatus());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ex(Exception e){
        ResponseDTO<?> responseDTO = new ResponseDTO<>().fail(500, "오류", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> ex400(Exception e){
        String message = e.getMessage();
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MyValidationException.class)
    public ResponseEntity<?> error(MyValidationException e){
        String errMsg = e.getErroMap().toString();
        String devideMsg = errMsg.split("=")[1].split(",")[0].split("}")[0];
        return new ResponseEntity<>((devideMsg), HttpStatus.BAD_REQUEST);
    }
}