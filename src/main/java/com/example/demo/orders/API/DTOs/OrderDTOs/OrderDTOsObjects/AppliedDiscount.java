package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import com.example.demo.orders.domain.entities.enums.DiscountType;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class AppliedDiscount {
    private UUID id;
    private UUID appliedByEmployeeId;
    private DiscountType type;
    private Optional<String> code;
    private PricingStrategy valueType;
    private BigDecimal value;
    private BigDecimal savings;
    private Currency currency;

}
