package com.example.demo.UserComponent.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    @NotNull
    private final String username;

    @NotNull
    private final String password;
}