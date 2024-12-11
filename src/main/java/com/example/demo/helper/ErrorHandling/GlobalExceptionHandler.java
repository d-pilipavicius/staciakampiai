package com.example.demo.helper.ErrorHandling;

import com.example.demo.helper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.helper.ErrorHandling.CustomExceptions.UnauthorizedException;
import com.example.demo.helper.ErrorHandling.CustomExceptions.UnprocessableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException ex){
        ErrorResponse response = ErrorResponse.builder().errorCode("Unauthorized").errorMessage(ex.getErrorMsg()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<ErrorResponse> unprocessableException(UnprocessableException ex){
        ErrorResponse response = ErrorResponse.builder().errorCode("Invalid data").errorMessage(ex.getErrorMsg()).build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException ex){
        ErrorResponse response = ErrorResponse.builder().errorCode("Id not found").errorMessage(ex.getErrorMsg()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
