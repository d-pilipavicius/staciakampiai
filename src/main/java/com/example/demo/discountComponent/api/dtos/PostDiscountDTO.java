package com.example.demo.discountComponent.api.dtos;


import com.example.demo.helper.enums.Currency;
import com.example.demo.helper.enums.DiscountTarget;
import com.example.demo.helper.enums.PricingStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Builder
public class PostDiscountDTO {
    private final String code;
    @NotNull
    private final BigDecimal amount;
    @NotNull
    private final PricingStrategy valueType;
    private final Currency currency;
    @NotNull
    private final Timestamp validFrom;
    @NotNull
    private final Timestamp validUntil;
    @NotNull
    private final DiscountTarget target;
    private final List<UUID> entitledProductIds;
    @NotNull
    private final UUID businessId;
    private final Integer usageCountLimit;
}
