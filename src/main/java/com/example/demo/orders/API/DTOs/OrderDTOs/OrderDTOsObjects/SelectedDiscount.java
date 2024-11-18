package com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects;

import com.example.demo.orders.domain.entities.enums.DiscountType;
import com.example.demo.orders.domain.entities.enums.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@Getter
@Setter
public class SelectedDiscount {
    private DiscountType type;
    private Optional<String> code;
    private BigDecimal value;
    private PricingStrategy valueType;
    private Optional<Currency> currency;
}
