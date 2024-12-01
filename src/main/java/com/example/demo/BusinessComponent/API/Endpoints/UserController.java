package com.example.demo.BusinessComponent.API.Endpoints;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.BusinessComponent.API.DTOs.CreateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UpdateUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {
  private final BusinessApplicationService businessApplicationService;

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
    UserDTO createdUser = businessApplicationService.createUser(createUserDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }
  
  @GetMapping("/{userId}")
  public ResponseEntity<UserDTO> getMethodName(@RequestParam UUID userId) {
    UserDTO gottenUserDTO = businessApplicationService.getUser(userId);
    return ResponseEntity.ok().body(gottenUserDTO);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserDTO> putMethodName(@PathVariable UUID userId, @RequestBody UpdateUserDTO updateUserDTO) {
    UserDTO updatedUserDTO = businessApplicationService.updateUser(userId, updateUserDTO);
    return ResponseEntity.ok().body(updatedUserDTO);
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@NotNull @PathVariable UUID userId) {
    businessApplicationService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
  
}
