package com.example.demo.helper.validator;

import com.example.demo.helper.CustomExceptions.HTTPExceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

public final class ValidatorForHTTPStatus {
    private ValidatorForHTTPStatus(){}
    public static void checkFor404(Object obj){
        if(obj == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public static void checkFor403(UUID id){
        /* check somehow authorized, i.e. if the id is of owner/SuperAdmin or just employee*/
        boolean isAuthorized = false;
        if(!isAuthorized){
            throw new UnauthorizedException(
                    "Unauthorized",
                    "You are not authorized to perform this action. Please contact your IT administrator."
            );
        }
    }
}
