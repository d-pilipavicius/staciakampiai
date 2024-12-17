package com.example.demo.UserComponent.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDTO {
    @NotNull
    private final String accessToken;

    @NotNull
    private final String tokenType = "Bearer ";
}
