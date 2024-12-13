package com.example.demo.BusinessComponent.Domain.Services;

import com.example.demo.BusinessComponent.API.DTOs.PostUserDTO;
import com.example.demo.BusinessComponent.API.DTOs.UserDTO;
import com.example.demo.BusinessComponent.Domain.Entities.Business;
import com.example.demo.BusinessComponent.Domain.Entities.User;
import com.example.demo.BusinessComponent.Helpers.Mappers.UserMapper;
import com.example.demo.BusinessComponent.Repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO createUser(PostUserDTO postUserDTO){
        User newUser = userMapper.toUser(postUserDTO);
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDTO(savedUser);
    }
}
