package com.example.demo.discountComponent.api.dtos;

import com.example.demo.CommonHelper.enums.DiscountTarget;
import com.example.demo.CommonHelper.enums.PricingStrategy;
import com.example.demo.CommonHelper.enums.Currency;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class DiscountDTO {
    @NotNull
    private final UUID id;
    private final int usageCount;
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
    @NotNull
    private final List<UUID> entitledProductIds;
    @NotNull
    private final UUID businessId;
    @NotNull
    private final Integer usageCountLimit;
}
