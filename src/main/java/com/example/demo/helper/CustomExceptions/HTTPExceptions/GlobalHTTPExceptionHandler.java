package com.example.demo.helper.CustomExceptions.HTTPExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalHTTPExceptionHandler {
    @ExceptionHandler(HTTPExceptionJSON.class)
    public ResponseEntity<Map<String, Object>> customException(HTTPExceptionJSON ex){
        Map<String, Object> response = Map.of(
                "errorCode", ex.getErrorCode(),
                "errorMessage", ex.getErrorMessage()
        );

        return ResponseEntity.status(ex.getResponse()).body(response);
    }
}
