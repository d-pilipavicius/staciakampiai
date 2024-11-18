package com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs;

import com.example.demo.orders.domain.entities.enums.DiscountType;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppliedDiscountDTO {
    private UUID id;
    private UUID appliedByEmployeeId;
    private BigDecimal savings;
    private DiscountType type;
    private Optional<String> code;
    private BigDecimal value;
    private PricingStrategy valueType;
    private Optional<Currency> currency;

}
