package com.example.demo.helper.ErrorHandling;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
}
