package com.example.demo.UserComponent.validator;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.UserComponent.Repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {
    private final IUserRepository userRepository;

    public void checkIfUsernameExists(String username) {
        if(userRepository.existsByUsername(username)){
            throw new UnprocessableException(
                    "The given username already exists."
            );
        }
    }
}
