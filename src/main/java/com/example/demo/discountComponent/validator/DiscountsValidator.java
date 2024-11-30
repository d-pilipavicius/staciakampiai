package com.example.demo.discountComponent.validator;

import com.example.demo.discountComponent.api.dtos.PatchDiscountDTO;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import com.example.demo.helper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.UUID;

public final class DiscountsValidator {
    private DiscountsValidator(){}

    public static void checkIfAuthorized(UUID id){
        //PLACEHOLDER FOR AUTHORIZATION
        boolean isAuthorized = true;
        if(!isAuthorized){
            throw new HTTPExceptionJSON(
                    HttpStatus.FORBIDDEN,
                    "Unauthorized",
                    "You are not authorized to perform this action. Please contact your IT administrator."
            );
        }
    }

    public static void checkDatesOverlap(Timestamp startDate, Timestamp endDate){
        if(startDate.compareTo(endDate) > 0){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "The discount starts later than or at the same time as it ends"
            );
        }
    }

    public static void checkPricingStrategy(Currency currency, PricingStrategy strat){
        if(currency != null && strat == PricingStrategy.Percentage){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "Pricing strategy was set to percentage, however currency was still provided for the discount"
            );
        }
    }

    //Presuming that if usageCountLimit is set to 1, means the discount automatically becomes a giftcard
    public static void checkGiftcard(DiscountTarget target, int usageCountLimit, String code){
        if(usageCountLimit == 1 && (code == null || target != DiscountTarget.All)){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "The usage count was given as 1, making it a giftcard, however the code was set not given, or the target not set to ALL."
            );
        }
    }

    public static void checkIfBusinessHadDiscounts(int totalDiscountsOfBusiness){
        if(totalDiscountsOfBusiness == 0){
            throw new HTTPExceptionJSON(
                    HttpStatus.NOT_FOUND,
                    "No data was found by Id",
                    "The provided business id, had no discounts associated with it."
            );
        }
    }
}
