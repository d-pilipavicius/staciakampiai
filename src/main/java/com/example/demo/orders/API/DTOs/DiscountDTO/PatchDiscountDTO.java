package com.example.demo.orders.API.DTOs.DiscountDTO;

import com.example.demo.orders.domain.entities.enums.DiscountTarget;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchDiscountDTO {
    private Optional<String> code;
    private Optional<BigDecimal> value;
    private Optional<PricingStrategy> valueType;
    private Optional<Currency> currency;
    private Optional<Timestamp> validFrom;
    private Optional<Timestamp> validUntil;
    private Optional<DiscountTarget> target;
    private Optional<List<UUID>> entitledProductIds;
}
