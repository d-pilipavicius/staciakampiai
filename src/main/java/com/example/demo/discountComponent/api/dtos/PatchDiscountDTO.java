package com.example.demo.discountComponent.api.dtos;


import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchDiscountDTO {
    private Optional<String> code;
    private Optional<BigDecimal> amount;
    private Optional<PricingStrategy> valueType;
    private Optional<Currency> currency;
    private Optional<Timestamp> validFrom;
    private Optional<Timestamp> validUntil;
    private Optional<DiscountTarget> target;
    private Optional<List<UUID>> entitledProductIds;
}
