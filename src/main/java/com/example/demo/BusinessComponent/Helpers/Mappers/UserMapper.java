package com.example.demo.BusinessComponent.Helpers.Mappers;

import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User toUser(PostUserDTO postUserDTO){
        return User
                .builder()
                .username(postUserDTO.getUsername())
                .password(passwordEncoder.encode(postUserDTO.getPassword()))
                .emailAddress(postUserDTO.getEmailAddress())
                .phoneNumber(postUserDTO.getPhoneNumber())
                .business(Business.builder().id(postUserDTO.getBusinessId()).build())
                .fullName(postUserDTO.getFullName())
                .build();
    }

    public UserDTO toUserDTO(User user){
        return UserDTO
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole())
                .emailAddress(user.getEmailAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
