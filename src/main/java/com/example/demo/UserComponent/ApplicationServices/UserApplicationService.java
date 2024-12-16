package com.example.demo.UserComponent.ApplicationServices;

import java.util.UUID;

import com.example.demo.UserComponent.API.DTOs.*;
import org.springframework.stereotype.Service;

import com.example.demo.UserComponent.Domain.Services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserApplicationService {
  private final UserService userService;

  public UserDTO createUser(CreateUserDTO createUserDTO) {
    return userService.createUser(createUserDTO);
  }

  public UserDTO getUser(@NotNull UUID userID) {
    return userService.getUser(userID);
  }

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    return userService.updateUser(userId, updateUserDTO);
  }

  public void deleteUser(@NotNull UUID userId) {
    userService.deleteUser(userId);
  }

}
