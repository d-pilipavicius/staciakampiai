package com.example.demo.UserComponent.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PutUserCredentialsDTO {
    @NotNull
    private final String username;
    @NotNull
    private final String password;
}
