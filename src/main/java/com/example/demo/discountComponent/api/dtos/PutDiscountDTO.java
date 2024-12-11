package com.example.demo.discountComponent.api.dtos;


import com.example.demo.helper.enums.Currency;
import com.example.demo.helper.enums.DiscountTarget;
import com.example.demo.helper.enums.PricingStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
//No usageCountLimit and businessId field, by documentation that can't be updated
//If optional fields are missed/explicitly set to null, they will be set to null
public class PutDiscountDTO {

    private final Optional<String> code = Optional.empty();
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final PricingStrategy valueType;
    private final Optional<Currency> currency = Optional.empty();
    @NotNull
    private final Timestamp validFrom;
    @NotNull
    private final Timestamp validUntil;
    @NotNull
    private final DiscountTarget target;
    @NotNull
    private final List<UUID> entitledProductIds;

}
