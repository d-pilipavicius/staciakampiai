package com.example.demo.BusinessComponent.API.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
