package com.example.demo.UserComponent.Domain.Services;

import java.util.UUID;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.UserComponent.API.DTOs.PutUserCredentialsDTO;
import com.example.demo.UserComponent.validator.UserValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.UserComponent.API.DTOs.CreateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.UserComponent.API.DTOs.UserDTO;
import com.example.demo.UserComponent.Domain.Entities.User;
import com.example.demo.UserComponent.Helpers.Mappers.UserMapper;
import com.example.demo.UserComponent.Repositories.IUserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
// TODO: Add exception handling
public class UserService {
  private final IUserRepository userRepository;

  private final UserMapper userMapper;

  private final UserValidator userValidator;

  private final PasswordEncoder passwordEncoder;

  public UserDTO createUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    userValidator.checkIfUsernameExists(createUserDTO.getUsername());
    User user = userMapper.toUser(createUserDTO);
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public UserDTO getUser(@NotNull UUID userId) {
    User user = userRepository.getReferenceById(userId);
    return userMapper.toUserDTO(user);
  }

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            "The given user id was not found."
    ));
    User updatedUser = userMapper.toUser(updateUserDTO, userId);
    updatedUser.setUsername(user.getUsername());
    updatedUser.setPassword(user.getPassword());
    User savedUser = userRepository.save(updatedUser);
    return userMapper.toUserDTO(savedUser);
  }

  public UserDTO updatePassword(@NotNull UUID userId, @NotNull @Valid PutUserCredentialsDTO putUserCredentialsDTO){
    userValidator.checkIfUsernameExists(putUserCredentialsDTO.getUsername());
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            "The given user id was not found."
    ));
    user.setUsername(putUserCredentialsDTO.getUsername());
    user.setPassword(passwordEncoder.encode(putUserCredentialsDTO.getPassword()));
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public void deleteUser(@NotNull UUID userId) {
    // TODO:Add checking if user id null
    userRepository.deleteById(userId);
  }
}
