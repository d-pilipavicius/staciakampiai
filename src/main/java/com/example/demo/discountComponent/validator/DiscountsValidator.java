package com.example.demo.discountComponent.validator;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnauthorizedException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.CommonHelper.enums.DiscountTarget;
import com.example.demo.CommonHelper.enums.PricingStrategy;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.repository.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
@AllArgsConstructor
public final class DiscountsValidator {
    private final DiscountRepository discountRepository;

    public void checkIfAuthorized(UUID id) {
        // PLACEHOLDER FOR AUTHORIZATION
        boolean isAuthorized = true;
        if (!isAuthorized) {
            throw new UnauthorizedException(
                    "You are not authorized to perform this action. Please contact your IT administrator.");
        }
    }

    public void checkDatesOverlap(Timestamp startDate, Timestamp endDate) {
        if (startDate.compareTo(endDate) > 0) {
            throw new UnprocessableException(
                    "The discount starts at " + startDate + " but ends at " + endDate
                            + ", therefore it starts later than ends.");
        }
    }

    public void checkPricingStrategy(Currency currency, PricingStrategy strat) {
        if ((currency == null && strat == PricingStrategy.FIXED_AMOUNT)
                || (currency != null && strat == PricingStrategy.FIXED_AMOUNT)) {
            throw new UnprocessableException(
                    "No currency was provided for a fixedAmount discount, or currency was provided for a percentage discount.");
        }
    }

    // Presuming that if usageCountLimit is set to 1, means the discount
    // automatically becomes a giftcard
    public void checkGiftcard(DiscountTarget target, Integer usageCountLimit, String code) {
        if (usageCountLimit != null && usageCountLimit == 1 && (code == null || target != DiscountTarget.All)) {
            throw new UnprocessableException(
                    "The usage count was given as 1, making it a giftcard, however the code was not given, or the target not set to ALL.");
        }
    }

    public void checkIfDiscountExists(UUID discountId) {
        if (!discountRepository.existsById(discountId)) {
            throw new NotFoundException(
                    "The given discount id " + discountId
                            + " wasn't associated with any discount inside the database.");
        }

    }

    public void checkIfBusinessHadDiscounts(int totalDiscountsOfBusiness, UUID businessId) {
        if (totalDiscountsOfBusiness == 0) {
            throw new NotFoundException(
                    "The provided business id " + businessId + ", had no discounts associated with it.");
        }
    }

    public void checkIfBusinessHadGiftcards(int totalGiftcardsOfBusiness, UUID businessId) {
        if (totalGiftcardsOfBusiness == 0) {
            throw new NotFoundException(
                    "The provided business id " + businessId + ", had no giftcards associated with it.");
        }
    }

    public void checkIfUsageLimitExceeded(Discount discount) {
        if (discount.getUsageCount() > discount.getUsageCountLimit()) {
            throw new UnprocessableException(
                    "The provided discount has already reached its usage count limit, which was: "
                            + discount.getUsageCountLimit());
        }
    }

    public void checkIfDiscountPageExceeded(int askedPage, int totalPages) {
        if (askedPage >= totalPages) {
            throw new UnprocessableException(
                    "Asked for page " + askedPage + " of discounts which is bigger than total discount pages "
                            + totalPages + ".");
        }
    }
}
