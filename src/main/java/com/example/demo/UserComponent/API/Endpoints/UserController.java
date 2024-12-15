package com.example.demo.UserComponent.API.Endpoints;

import com.example.demo.UserComponent.API.DTOs.*;
import com.example.demo.security.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.UserComponent.ApplicationServices.UserApplicationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
  private final UserApplicationService userApplicationService;

  private final JWTUtils jwtUtils;

  private final AuthenticationManager authenticationManager;

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    UserDTO createdUser = userApplicationService.createUser(createUserDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
    UserDTO gottenUserDTO = userApplicationService.getUser(userId);
    return ResponseEntity.ok().body(gottenUserDTO);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserDTO updateUserDTO) {
    UserDTO updatedUserDTO = userApplicationService.updateUser(userId, updateUserDTO);
    return ResponseEntity.ok().body(updatedUserDTO);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@NotNull @PathVariable UUID userId) {
    userApplicationService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO){
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtUtils.generateToken(authentication);

    return new ResponseEntity<>(LoginResponseDTO.builder().accessToken(token).build(), HttpStatus.OK);
  }

}
