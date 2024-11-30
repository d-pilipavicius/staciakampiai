package com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs;

import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    @NotNull
    private UUID id;
    @NotNull
    private int usageCount;
    private String code;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PricingStrategy valueType;
    private Currency currency;
    @NotNull
    private Timestamp validFrom;
    @NotNull
    private Timestamp validUntil;
    @NotNull
    private DiscountTarget target;
    @NotNull
    private List<UUID> entitledProductIds;
    @NotNull
    private UUID businessId;
    @NotNull
    private int usageCountLimit;
}
