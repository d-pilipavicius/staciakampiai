package com.example.demo.BusinessComponent.validator;

import com.example.demo.BusinessComponent.Repositories.IBusinessRepository;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class BusinessValidator {

    private final IBusinessRepository businessRepository;

    public void checkIfAnyBusinessExisted(int totalBusinesses) {
        if (totalBusinesses == 0) {
            throw new NotFoundException(
                    "As of now no businesses exist.");
        }
    }

    public void checkIfBusinessPageExceeded(int askedPage, int totalPages) {
        if (askedPage >= totalPages) {
            throw new UnprocessableException(
                    "Asked for page " + askedPage + " of businesses which is bigger than total business pages "
                            + totalPages + ".");
        }
    }

    public void checkIfBusinessIdExists(UUID businessId){
        if(!businessRepository.existsById(businessId)){
            throw new NotFoundException(
                    "The provided business id: " + businessId + ", does not exist."
            );
        }
    }

    public void checkIfPhoneNumberValid(String phoneNumber){
        if(!Pattern.matches("^[+]{1}(?:[0-9\\-()/.]\\s?){6,15}[0-9]{1}$", phoneNumber)){
            throw new UnprocessableException("Phone number is not valid");
        }
    }
}
