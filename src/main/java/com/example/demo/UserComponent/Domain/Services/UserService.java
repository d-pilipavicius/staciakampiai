package com.example.demo.UserComponent.Domain.Services;

import java.util.UUID;

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

  public UserDTO createUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    User user = userMapper.toUser(createUserDTO);
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public UserDTO getUser(@NotNull UUID userId) {
    User user = userRepository.getReferenceById(userId);
    return userMapper.toUserDTO(user);
  }

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    User user = userMapper.toUser(updateUserDTO, userId);
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public void deleteUser(@NotNull UUID userId) {
    // TODO:Add checking if user id null
    userRepository.deleteById(userId);
  }
}
