package com.example.demo.BusinessComponent.API.Endpoints;

import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;
import com.example.demo.BusinessComponent.Repositories.IUserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class AuthController {
    private AuthenticationManager authenticationManager;
    private BusinessApplicationService businessApplicationService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody PostUserDTO postUserDTO){
        //HCECK IF USERNAME ALREADY EXISTS!!!!!
        UserDTO createdUserDTO = businessApplicationService.createUser(postUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }
}
