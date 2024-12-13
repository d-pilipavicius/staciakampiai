package com.example.demo.BusinessComponent.API.DTOs;

import com.example.demo.BusinessComponent.Domain.Entities.Enums.RoleType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PostUserDTO {
    @NotNull
    private final String username;
    @NotNull
    private final String password;
    @NotNull
    private final String fullName;
    @NotNull
    private final String phoneNumber;
    @NotNull
    private final String emailAddress;
    private final UUID businessId;
    @NotNull
    private final RoleType role;
}
