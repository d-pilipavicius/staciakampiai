package com.example.demo.BusinessComponent.API.Endpoints;

import com.example.demo.BusinessComponent.API.DTOs.LoginDTO;
import com.example.demo.BusinessComponent.API.DTOs.LoginResponseDTO;
import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.ApplicationServices.BusinessApplicationService;
import com.example.demo.BusinessComponent.Repositories.IUserRepository;
import com.example.demo.security.JWTUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private JWTUtils jwtUtils;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody PostUserDTO postUserDTO){
        //HCECK IF USERNAME ALREADY EXISTS!!!!!
        UserDTO createdUserDTO = businessApplicationService.createUser(postUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);

        return new ResponseEntity<>(LoginResponseDTO.builder().accessToken(token).build(), HttpStatus.OK);
    }
}
