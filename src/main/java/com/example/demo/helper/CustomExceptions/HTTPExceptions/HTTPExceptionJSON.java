package com.example.demo.helper.CustomExceptions.HTTPExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class HTTPExceptionJSON extends RuntimeException {
    private final HttpStatus response;
    private final String errorCode;
    private final String errorMessage;
}
