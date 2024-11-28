package com.example.demo.orderComponent.api.dtos.OrderHelperDTOs;

import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.DiscountType;
import com.example.demo.serviceChargeComponent.domain.entities.enums.PricingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectedDiscountDTO {
    private DiscountType type;
    private Optional<String> code;
    private BigDecimal value;
    private PricingStrategy valueType;
    private Optional<Currency> currency;
}
