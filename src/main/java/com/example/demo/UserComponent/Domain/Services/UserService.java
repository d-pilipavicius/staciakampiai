package com.example.demo.UserComponent.Domain.Services;

import java.util.List;
import java.util.UUID;

import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.UserComponent.API.DTOs.PutUserCredentialsDTO;
import com.example.demo.UserComponent.validator.UserValidator;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
// TODO: Add exception handling
public class UserService {
  private final IUserRepository userRepository;

  private final UserMapper userMapper;

  private final UserValidator userValidator;

  private final PasswordEncoder passwordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public UserDTO createUser(@NotNull @Valid CreateUserDTO createUserDTO) {
    userValidator.checkIfUsernameExists(createUserDTO.getUsername());
    User user = userMapper.toUser(createUserDTO);
    User savedUser = userRepository.save(user);
    UserDTO savedUserDTO = userMapper.toUserDTO(savedUser);
    logger.info("Created user with ID: {}, Details: {}", savedUserDTO.getId(), savedUserDTO.toString());

    return savedUserDTO;
  }

  public UserDTO getUser(@NotNull UUID userId) {
    User user = userRepository.getReferenceById(userId);
    return userMapper.toUserDTO(user);
  }

  public UserDTO getUserByUsername(@NotNull String username){
    User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(
            "No user exists"
    ));

    return userMapper.toUserDTO(user);
  }

  public List<UserDTO> getUsersByBusiness(UUID businessId) {
    List<User> users = userRepository.findAllByBusiness(Business.builder().id(businessId).build());
    return users.stream().map(userMapper::toUserDTO).toList();
  }

  public UserDTO updateUser(@NotNull UUID userId, @NotNull @Valid UpdateUserDTO updateUserDTO) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            "The given user id was not found."
    ));
    User updatedUser = userMapper.toUser(updateUserDTO, userId);
    updatedUser.setUsername(user.getUsername());
    updatedUser.setPassword(user.getPassword());
    updatedUser.setRole(user.getRole());
    updatedUser.setBusiness(user.getBusiness());
    User savedUser = userRepository.save(updatedUser);
    UserDTO savedUserDTO = userMapper.toUserDTO(savedUser);

    logger.info("Updated user with ID: {}, New details: {}", savedUserDTO.getId(), savedUserDTO.toString());

    return savedUserDTO;
  }

  public UserDTO updateSensitiveInformation(@NotNull UUID userId, @NotNull @Valid PutUserCredentialsDTO putUserCredentialsDTO){
    userValidator.checkIfUsernameExistsForUpdate(putUserCredentialsDTO.getUsername());
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(
            "The given user id was not found."
    ));
    user.setUsername(putUserCredentialsDTO.getUsername());
    user.setPassword(passwordEncoder.encode(putUserCredentialsDTO.getPassword()));
    user.setRole(putUserCredentialsDTO.getRole());
    user.setBusiness(Business.builder().id(putUserCredentialsDTO.getBusinessId()).build());
    User savedUser = userRepository.save(user);
    return userMapper.toUserDTO(savedUser);
  }

  public void deleteUser(@NotNull UUID userId) {
    // TODO:Add checking if user id null
    userRepository.deleteById(userId);
  }

  public void checkIfUserExists(UUID userId){
      if(!userRepository.existsById(userId)){
        throw new NotFoundException(
                "The provided user id: " + userId + ", does not exist."
        );
      }
  }
}
