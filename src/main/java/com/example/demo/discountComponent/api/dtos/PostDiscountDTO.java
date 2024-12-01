package com.example.demo.discountComponent.api.dtos;


import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDiscountDTO {
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
    private List<UUID> entitledProductIds;
    @NotNull
    private UUID businessId;
    private Integer usageCountLimit;
}
