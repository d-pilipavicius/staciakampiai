package com.example.demo.BusinessComponent.Domain.Services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.demo.BusinessComponent.Repositories.IUserRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.example.demo.BusinessComponent.API.DTOs.CreateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Entities.User;
import com.example.demo.BusinessComponent.Helpers.Mappers.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
  private final IUserRepository userRepository;
  private final UserMapper userMapper;
  
  public UserDTO createUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    User user = userMapper.toUser(createUserDTO);
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public UserDTO getUser(@NotNull @Valid UUID userId) {
    User user = userRepository.getReferenceById(userId);
    return userMapper.toUserDTO(user);
  }

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    User user = userMapper.toUser(updateUserDTO, userId);
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public void deleteUser(@NotNull @Valid UUID userId) {
    userRepository.deleteById(userId);
  }
}
