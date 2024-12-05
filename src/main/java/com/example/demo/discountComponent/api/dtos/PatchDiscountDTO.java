package com.example.demo.discountComponent.api.dtos;


import com.example.demo.discountComponent.domain.entities.enums.Currency;
import com.example.demo.discountComponent.domain.entities.enums.DiscountTarget;
import com.example.demo.discountComponent.domain.entities.enums.PricingStrategy;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchDiscountDTO {


    private Optional<String> code;
    private Optional<BigDecimal> amount = Optional.empty();
    private Optional<PricingStrategy> valueType = Optional.empty();
    private Optional<Currency> currency;
    private Optional<Timestamp> validFrom = Optional.empty();
    private Optional<Timestamp> validUntil = Optional.empty();
    private Optional<DiscountTarget> target = Optional.empty();
    private Optional<List<UUID>> entitledProductIds = Optional.empty();

}
