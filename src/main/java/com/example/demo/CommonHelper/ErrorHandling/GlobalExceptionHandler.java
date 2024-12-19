package com.example.demo.CommonHelper.ErrorHandling;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.ForbiddenException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnauthorizedException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedException(UnauthorizedException ex) {
        ErrorResponse response = ErrorResponse.builder().errorCode("Unauthorized").errorMessage(ex.getErrorMsg())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<ErrorResponse> unprocessableException(UnprocessableException ex) {
        ErrorResponse response = ErrorResponse.builder().errorCode("Invalid data").errorMessage(ex.getErrorMsg())
                .build();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException ex) {
        ErrorResponse response = ErrorResponse.builder().errorCode("Id not found").errorMessage(ex.getErrorMsg())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenException(ForbiddenException ex){
        ErrorResponse response = ErrorResponse.builder().errorCode("Forbidden").errorMessage(ex.getErrorMsg())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse response = ErrorResponse.builder()
                .errorCode("Bad request")
                .errorMessage("Validation failed for one or more fields")
                .details(errorMessages)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorResponse response = ErrorResponse.builder().errorCode("Bad request").errorMessage(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .errorCode("Bad Request")
                .errorMessage("Request body is missing or unreadable")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
