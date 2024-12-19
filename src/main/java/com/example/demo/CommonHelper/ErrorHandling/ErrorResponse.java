package com.example.demo.CommonHelper.ErrorHandling;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

    private List<String> details;
}
