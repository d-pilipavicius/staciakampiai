package com.example.demo.helper.CustomExceptions.HTTPExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;
}
