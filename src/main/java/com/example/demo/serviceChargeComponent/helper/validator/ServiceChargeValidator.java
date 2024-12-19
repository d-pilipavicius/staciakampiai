package com.example.demo.serviceChargeComponent.helper.validator;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.NotFoundException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.UnprocessableException;
import com.example.demo.CommonHelper.enums.Currency;
import com.example.demo.CommonHelper.enums.PricingStrategy;
import com.example.demo.serviceChargeComponent.repository.ServiceChargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class ServiceChargeValidator {

    private final ServiceChargeRepository serviceChargeRepository;

    public void validateServiceChargeIds(List<UUID> serviceChargeIds) {
        if(serviceChargeIds != null && !serviceChargeIds.isEmpty() && !serviceChargeIds.stream().allMatch(serviceChargeRepository::existsById))
            throw new NotFoundException("ServiceCharge not found");
    }

    public void checkPricingStrategy(Currency currency, PricingStrategy strat) {
        if ((currency == null && strat == PricingStrategy.FIXED_AMOUNT)
                || (currency != null && strat == PricingStrategy.PERCENTAGE)) {
            throw new UnprocessableException(
                    "No currency was provided for a fixedAmount serviceCharge, or currency was provided for a percentage serviceCharge.");
        }
    }

    public void checkIfBusinessHadServiceCharges(int totalServiceChargesOfBusiness) {
        if (totalServiceChargesOfBusiness == 0) {
            throw new NotFoundException(
                    "The provided business id, had no service charges associated with it.");
        }
    }

    public void checkIfServiceChargePageExceeded(int askedPage, int totalPages) {
        if (askedPage >= totalPages) {
            throw new UnprocessableException(
                    "Asked for page " + askedPage + " of service charges which is bigger than total service charges pages "
                            + totalPages + ".");
        }
    }
}
