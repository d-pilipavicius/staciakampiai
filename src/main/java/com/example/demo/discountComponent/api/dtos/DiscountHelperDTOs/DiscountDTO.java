package com.example.demo.discountComponent.api.dtos.DiscountHelperDTOs;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private UUID id;
    private int usageCount;
    private Optional<String> code;
    private BigDecimal value;
    private PricingStrategy valueType;
    private Optional<Currency> currency;
    private Timestamp validFrom;
    private Timestamp validUntil;
    private DiscountTarget target;
    private Optional<List<UUID>> entitledProductIds;
    private UUID businessId;
    private Optional<Integer> usageCountLimit;
}
