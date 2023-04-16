package shop.mtcoding.securityapp.core.exception;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class MyValidationException extends RuntimeException {

    private Map<String, String> erroMap;

    public MyValidationException(Map<String, String> erroMap) {
        this.erroMap = erroMap;
    }
}
