package com.example.demo.UserComponent.ApplicationServices;

import java.util.List;
import java.util.UUID;

import com.example.demo.UserComponent.API.DTOs.*;
import org.springframework.stereotype.Service;

import com.example.demo.UserComponent.Domain.Services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class UserApplicationService {
  private final UserService userService;
  private static final Logger logger = LoggerFactory.getLogger(UserApplicationService.class);

  public UserDTO createUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    return userService.createUser(createUserDTO);
  }

  public UserDTO getUser(@NotNull UUID userID) {
    return userService.getUser(userID);
  }

  public List<UserDTO> getUsersByBusiness(UUID businessId) {
    return userService.getUsersByBusiness(businessId);
  }

  public UserDTO getUserByUsername(@NotNull String username) {return userService.getUserByUsername(username);}

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    return userService.updateUser(userId, updateUserDTO);
  }

  public UserDTO updateSensitiveInformation(@NotNull UUID userId, @NotNull @Valid PutUserCredentialsDTO putUserCredentialsDTO){
    return userService.updateSensitiveInformation(userId, putUserCredentialsDTO);
  }

  public void deleteUser(@NotNull UUID userId) {
    userService.deleteUser(userId);
  }

  public void checkIfUserExists(UUID userId){
    userService.checkIfUserExists(userId);
  }

  public void logUserLogin(String username) { 
    logger.info("User [{}] successfully logged in.", username); 
  }

}
