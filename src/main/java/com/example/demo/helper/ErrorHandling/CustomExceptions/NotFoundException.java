package com.example.demo.helper.ErrorHandling.CustomExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private String errorMsg;
}
