package com.example.demo.CommonHelper.ErrorHandling.CustomExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtMalformedException extends RuntimeException{
    String errorMsg;
}
