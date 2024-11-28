package com.example.demo.helper.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;

public final class ValidationForDifferentHTTPCodes {
    private ValidationForDifferentHTTPCodes(){}
    public static void checkFor404(Object obj){
        if(obj == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public static ResponseEntity<Object> checkFor403(UUID id){
        /* check somehow authorized, i.e. if the id is of owner/SuperAdmin or just employee*/
        boolean isAuthorized = true;
        if(!isAuthorized){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "errorCode", "Unauthorized",
                    "errorMessage", "You are not authorized to perform this action. Please contact your IT administrator."
            ));
        }
        return null;
    }
}
