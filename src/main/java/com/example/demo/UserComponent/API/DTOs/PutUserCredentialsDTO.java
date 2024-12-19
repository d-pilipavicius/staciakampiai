package com.example.demo.UserComponent.API.DTOs;

import com.example.demo.UserComponent.Domain.Entities.Enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PutUserCredentialsDTO {
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private final RoleType role;

    @NotNull
    private final UUID businessId;
}
