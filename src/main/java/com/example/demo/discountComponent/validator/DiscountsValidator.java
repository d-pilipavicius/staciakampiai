package com.example.demo.discountComponent.validator;

import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import com.example.demo.discountComponent.repository.DiscountRepository;
import com.example.demo.helper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public final class DiscountsValidator {
    private final DiscountRepository discountRepository;

    public DiscountsValidator(DiscountRepository discountRepository){
        this.discountRepository = discountRepository;
    }

    public void checkIfAuthorized(UUID id){
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

    public void checkDatesOverlap(Timestamp startDate, Timestamp endDate){
        if(startDate.compareTo(endDate) > 0){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "The discount starts later than or at the same time as it ends"
            );
        }
    }

    public void checkPricingStrategy(Currency currency, PricingStrategy strat){
        if((currency == null && strat == PricingStrategy.FixedAmount) || (currency != null && strat == PricingStrategy.Percentage)){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "No currency was provided for a fixedAmount discount, or currency was provided for a percentage discount."
            );
        }
    }

    //Presuming that if usageCountLimit is set to 1, means the discount automatically becomes a giftcard
    public void checkGiftcard(DiscountTarget target, Integer usageCountLimit, String code){
        if(usageCountLimit != null && usageCountLimit == 1 && (code == null || target != DiscountTarget.All)){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "The usage count was given as 1, making it a giftcard, however the code was not given, or the target not set to ALL."
            );
        }
    }

    public void checkIfDiscountExists(UUID discountId){
        if(!discountRepository.existsById(discountId)){
            throw new HTTPExceptionJSON(
                    HttpStatus.NOT_FOUND,
                    "Invalid discount id",
                    "The given discount id wasn't associated with any discount inside the database."
            );
        }

    }

    public void checkIfBusinessHadDiscounts(int totalDiscountsOfBusiness){
        if(totalDiscountsOfBusiness == 0){
            throw new HTTPExceptionJSON(
                    HttpStatus.NOT_FOUND,
                    "No data was found by discount id",
                    "The provided business id, had no discounts associated with it."
            );
        }
    }

    public void checkIfDiscountPageExceeded(int askedPage, int totalPages){
        if(askedPage >= totalPages){
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "Asked for a page of discounts which is bigger than total discount pages."
            );
        }
    }
}
