package shop.mtcoding.village.core.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.village.core.exception.MyValidationException;
import shop.mtcoding.village.dto.ResponseDTO;
import shop.mtcoding.village.exception.Exception400;


@RestControllerAdvice
public class MyExceptionAdvice {

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
